package top.cubik65536.cubikbot.event;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.cache.EhcacheHelp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.event.GroupMemberEvent;
import com.icecreamqaq.yuq.event.GroupMemberRequestEvent;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.event.GroupRecallEvent;
import com.icecreamqaq.yuq.message.*;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.entity.QQEntity;
import top.cubik65536.cubikbot.logic.QQAILogic;
import top.cubik65536.cubikbot.logic.TimeLogic;
import top.cubik65536.cubikbot.service.GroupService;
import top.cubik65536.cubikbot.service.MessageService;
import top.cubik65536.cubikbot.service.QQService;
import top.cubik65536.cubikbot.utils.BotUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EventListener
@SuppressWarnings("unused")
public class GroupManageEvent {
    @Inject
    private GroupService groupService;
    @Inject
    private QQService qqService;
    @Inject
    private QQAILogic qqaiLogic;
    @Inject
    private TimeLogic timeLogic;
    @Inject
    private MessageService messageService;
    @Inject
    @Named("CommandCountOnTime")
    public EhcacheHelp<Integer> eh;

    private final Map<Long, JSONArray> lastMessage = new ConcurrentHashMap<>();
    private final Map<Long, Long> lastQQ = new ConcurrentHashMap<>();
    private final Map<Long, JSONArray> lastRepeatMessage = new ConcurrentHashMap<>();

    @Event(weight = Event.Weight.high)
    public void status(com.IceCreamQAQ.Yu.event.events.Event e){
        Long group = null;
        Message message = null;
        if (e instanceof GroupMemberEvent){
            group = ((GroupMemberEvent) e).getGroup().getId();
        }else if (e instanceof GroupMemberRequestEvent){
            group = ((GroupMemberRequestEvent) e).getGroup().getId();
        }else if (e instanceof GroupRecallEvent){
            group = ((GroupRecallEvent) e).getGroup().getId();
        }else if (e instanceof GroupMessageEvent){
            GroupMessageEvent groupMessageEvent = (GroupMessageEvent) e;
            group = groupMessageEvent.getGroup().getId();
            message = groupMessageEvent.getMessage();
        }
        if (group == null) return;
        GroupEntity groupEntity = groupService.findByGroup(group);
        boolean status = true;
        if (message != null) {
            List<String> list = message.toPath();
            if (list.size() == 2) {
                String pa = list.get(1);
                if ("CubikBot".equals(list.get(0)) && pa.equals("开") || pa.equals("关")) {
                    status = false;
                }
            }
        }
        if (groupEntity != null && groupEntity.getStatus()){
            status = false;
        }
        if (status){
            e.setCancel(true);
        }
    }

    @Event
    public void inter(GroupMessageEvent e) throws IOException {
        GroupEntity groupEntity = groupService.findByGroup(e.getGroup().getId());
        if (groupEntity == null) return;
        if (groupEntity.getWhiteJsonArray().contains(String.valueOf(e.getSender().getId()))) return;
        Message message = e.getMessage();
        String str;
        try {
            str = message.toPath().get(0);
        }catch (IllegalStateException ex){
            str = null;
        }
        if (str != null){
            JSONArray interceptJsonArray = groupEntity.getInterceptJsonArray();
            for (int i = 0; i < interceptJsonArray.size(); i++){
                String intercept = interceptJsonArray.getString(i);
                if (str.contains(intercept)){
                    e.cancel = true;
                    break;
                }
            }
        }
        if (!e.getGroup().getBot().isAdmin()) return;
        QQEntity qqEntity = qqService.findByQQAndGroup(e.getSender().getId(), e.getGroup().getId());
        if (qqEntity == null) qqEntity = new QQEntity(e.getSender().getId(), groupEntity);
        JSONArray violationJsonArray = groupEntity.getViolationJsonArray();
        int code = 0;
        String vio = null;
        out:for (int i = 0; i < violationJsonArray.size(); i++){
            String violation = violationJsonArray.getString(i);
            String nameCard = e.getSender().getNameCard();
            if (nameCard.contains(violation)) {
                code = 3;
                vio = violation;
                break;
            }
            for (MessageItem item: message.getBody()){
                if (item instanceof Text){
                    Text text = (Text) item;
                    if (text.getText().contains(violation)) code = 1;
                }else if (item instanceof Image){
                    Image image = (Image) item;
                    String result = qqaiLogic.generalOCR(image.getUrl());
                    if (result.contains(violation)) code = 1;
                    boolean b = qqaiLogic.pornIdentification(image.getUrl());
                    if (b) code = 2;
                }else if (item instanceof XmlEx){
                    XmlEx xmlEx = (XmlEx) item;
                    if (xmlEx.getValue().contains(violation)) code = 1;
                }else if (item instanceof JsonEx){
                    JsonEx jsonEx = (JsonEx) item;
                    if (jsonEx.getValue().contains(violation)) code = 1;
                }
                if (code != 0){
                 vio = violation;
                 break out;
                }
            }
        }
        if (code != 0){
            Integer violationCount = qqEntity.getViolationCount();
            if (violationCount == null) violationCount = 0;
            qqEntity.setViolationCount(++violationCount);
            Integer maxViolationCount = groupEntity.getMaxViolationCount();
            if (maxViolationCount == null) maxViolationCount = 5;
            if (violationCount < maxViolationCount){
                qqService.save(qqEntity);
                StringBuilder sb = new StringBuilder();
                if (code == 2) sb.append("检测到色情图片。").append("\n");
                else if (code == 1) sb.append("检测到违规词\"").append(vio).append("\"。").append("\n");
                else sb.append("检测到违规去群名片\"").append(vio).append("\"。").append("\n");
                sb.append("您当前的违规次数为").append(violationCount)
                        .append("次，累计违规").append(maxViolationCount)
                        .append("次会被移除本群哦！！");
                e.getSender().ban(60 * 30);
                e.getGroup().sendMessage(FunKt.getMif().at(qqEntity.getQq()).plus(sb.toString()));
            }else {
                e.getSender().kick("违规次数已上限！！");
                e.getGroup().sendMessage(Message.Companion.toMessage(
                        qqEntity.getQq() + "违规次数已达上限，送飞机票一张！！"
                ));
            }
        }
    }

    @Event
    public void qa(GroupMessageEvent e){
        GroupEntity groupEntity = groupService.findByGroup(e.getGroup().getId());
        if (groupEntity == null) return;
        Message message = e.getMessage();
        if (message.toPath().size() == 0) return;
        if ("学习".equals(message.toPath().get(0))) return;
        if ("复读".equals(message.toPath().get(0))) return;
        if ("忘记".equals(message.toPath().get(0))) return;
        if ("加问答".equals(message.toPath().get(0))) return;
        if ("删问答".equals(message.toPath().get(0))) return;
        String str;
        try {
            str = Message.Companion.firstString(message);
        } catch (IllegalStateException ex) {
            return;
        }
        JSONArray qaJsonArray = groupEntity.getQaJsonArray();
        for (int i = 0; i < qaJsonArray.size(); i++) {
            JSONObject jsonObject = qaJsonArray.getJSONObject(i);
            String type = jsonObject.getString("type");
            String q = jsonObject.getString("q");
            boolean status = false;
            if ("ALL".equals(type)){
                if (str.equals(q)) status = true;
            }else if (str.contains(jsonObject.getString("q"))) status = true;
            if (status){
                Integer maxCount = groupEntity.getMaxCommandCountOnTime();
                if (maxCount == null) maxCount = -1;
                if (maxCount > 0){
                    String key = "qq" + e.getSender().getId() + q;
                    Integer num = eh.get(key);
                    if (num == null) num = 0;
                    if (num >= maxCount) return;
                    eh.set(key, ++num);
                }
                JSONArray jsonArray = jsonObject.getJSONArray("a");
                e.getGroup().sendMessage(BotUtils.jsonArrayToMessage(jsonArray));
            }
        }
    }

    @Event
    public void learn(GroupMessageEvent e) {
        GroupEntity groupEntity = groupService.findByGroup(e.getGroup().getId());
        if (groupEntity == null) return;
        Message message = e.getMessage();
        if (message.toPath().size() == 0) return;
        if ("学习".equals(message.toPath().get(0))) return;
        if ("复读".equals(message.toPath().get(0))) return;
        if ("忘记".equals(message.toPath().get(0))) return;
        if ("加问答".equals(message.toPath().get(0))) return;
        if ("删问答".equals(message.toPath().get(0))) return;
        String str;
        try {
            str = Message.Companion.firstString(message);
        } catch (IllegalStateException ex) {
            return;
        }
        MessageItemFactory mif = FunKt.getMif();
        JSONArray learnJsonArray = groupEntity.getLearnJsonArray();
        if ((message.toPath().get(0).startsWith("草") || message.toPath().get(0).startsWith("cao") || message.toPath().get(0).startsWith("艹")) && (groupEntity.getCao())) {
            e.getGroup().sendMessage(mif.text("稻 草 人").toMessage());
            return;
        }
        if (message.toPath().get(0).startsWith("早")) {
            if (timeLogic.isInTime("05:00", "8:30")) {
                e.getGroup().sendMessage(mif.text("早").toMessage());
            } else if (timeLogic.isInTime("08:30", "11:45")) {
                e.getGroup().sendMessage(mif.text("太阳都晒屁股了不早了").toMessage());
            } else ;
            return;
        } else if (message.toPath().get(0).startsWith("晚安") && (timeLogic.isInTimeSec("20:00:00", "23:59:59") || timeLogic.isInTimeSec("00:00:01", "03:00:00"))) {
            e.getGroup().sendMessage(mif.text("晚安").toMessage());
            return;
        } else ;
        for (int i = 0; i < learnJsonArray.size(); i++) {
            JSONObject jsonObject = learnJsonArray.getJSONObject(i);
            String type = jsonObject.getString("type");
            String q = jsonObject.getString("q");
            boolean status = false;
            if ("ALL".equals(type)) {
                if (str.equals(q)) status = true;
            } else if (str.contains(jsonObject.getString("q"))) status = true;
            if (status) {
                Integer maxCount = groupEntity.getMaxCommandCountOnTime();
                if (maxCount == null) maxCount = -1;
                if (maxCount > 0) {
                    String key = "qq" + e.getSender().getId() + q;
                    Integer num = eh.get(key);
                    if (num == null) num = 0;
                    if (num >= maxCount) return;
                    eh.set(key, ++num);
                }
                JSONArray jsonArrayForQ = BotUtils.messageToJsonArray(mif.text(q).toMessage());
                JSONArray jsonArray = jsonObject.getJSONArray("a");
                if (!(groupEntity.getRepeat()) && jsonArray.equals(jsonArrayForQ)) {
                    return;
                } else {
                    e.getGroup().sendMessage(BotUtils.jsonArrayToMessage(jsonArray));
                }
            }
        }
    }

}
