package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.annotation.Config;
import com.IceCreamQAQ.Yu.annotation.Synonym;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.PathVar;
import com.icecreamqaq.yuq.annotation.QMsg;
import com.icecreamqaq.yuq.controller.ContextSession;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.job.RainInfo;
import com.icecreamqaq.yuq.message.Image;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.logic.QQAILogic;
import top.cubik65536.cubikbot.logic.ToolLogic;
import top.cubik65536.cubikbot.service.ConfigService;
import top.cubik65536.cubikbot.service.GroupService;
import top.cubik65536.cubikbot.service.MessageService;
import top.cubik65536.cubikbot.utils.BotUtils;
import top.cubik65536.cubikbot.utils.OkHttpUtils;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@GroupController
public class ToolController {
    @Inject
    private ToolLogic toolLogic;
    @Inject
    private GroupService groupService;
    @Inject
    private QQAILogic qqAiLogic;
    @Inject
    private ConfigService configService;
    @Inject
    private MessageService messageService;
    @Inject
    private RainInfo rainInfo;
    @Inject
    private MessageItemFactory mif;

    @Before
    public GroupEntity before(long group, Member qq) {
        GroupEntity groupEntity = groupService.findByGroup(group);
        return groupEntity;
    }

    @Config("YuQ.Mirai.protocol")
    private String protocol;

    private final LocalDateTime startTime;

    public ToolController() {
        startTime = LocalDateTime.now();
    }

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    @Action("统计")
    @Synonym({"运行状态"})
    public String status() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        Properties props = System.getProperties();
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmMaxMoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        LocalDateTime nowTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, nowTime);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        String ss = days + "天" + hours + "小时" + minutes + "分钟";
        return  "程序运行时长：" + ss + "\n" +
                "cpu核数：" + processor.getLogicalProcessorCount() + "\n" +
                "cpu当前使用率：" + new DecimalFormat("#.##%").format(1.0-(idle * 1.0 / totalCpu)) + "\n" +
                "总内存：" + formatByte(totalByte) + "\n" +
                "已使用内存：" + formatByte(totalByte-acaliableByte) + "\n" +
                "操作系统：" + osName + "\n" +
                "系统架构：" + osArch + "\n" +
                "jvm内存总量：" + formatByte(jvmTotalMemoryByte) + "\n" +
                "jvm已使用内存：" + formatByte(jvmTotalMemoryByte-freeMemoryByte) + "\n" +
                "java版本：" + jdkVersion;
    }

    @Action("消息统计")
    public String message(){
        return "当前收发消息状态：\n" +
                "收：" + rainInfo.getCountRm() + " / 分钟\n" +
                "发：" + rainInfo.getCountSm() + " / 分钟\n" +
                "总计：\n" +
                "收：" + rainInfo.getCountRa() + " 条，\n" +
                "发：" + rainInfo.getCountSa() + " 条。";
    }

    private String formatByte(long byteNumber){
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber/FORMAT;
        if(kbNumber<FORMAT){
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber/FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    @Action("几点了")
    public Image time() throws IOException {
        return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(toolLogic.queryTime()));
    }

    @Action("窥屏检测")
    public void checkPeeping(GroupEntity groupEntity, Group group, long qq) {
        if (groupEntity == null || groupEntity.getPeeking() == null || !groupEntity.getPeeking())
            throw FunKt.getMif().at(qq).plus("该功能已关闭！！").toThrowable();
        String random = BotUtils.randomNum(4);
        group.sendMessage(FunKt.getMif().jsonEx("{\"app\":\"com.tencent.miniapp\",\"desc\":\"\",\"view\":\"notification\",\"ver\":\"1.0.0.11\",\"prompt\":\"QQ程序\",\"appID\":\"\",\"sourceName\":\"\",\"actionData\":\"\",\"actionData_A\":\"\",\"sourceUrl\":\"\",\"meta\":{\"notification\":{\"appInfo\":{\"appName\":\"窥屏检测中...\",\"appType\":4,\"appid\":1109659848,\"iconUrl\":\"https:\\/\\/api.kuku.me\\/tool\\/peeping\\/check\\/" + random + "\"},\"button\":[],\"data\":[],\"emphasis_keyword\":\"\",\"title\":\"请等待15s\"}},\"text\":\"\",\"extraApps\":[],\"sourceAd\":\"\",\"extra\":\"\"}").toMessage());
        executorService.schedule(() -> {
            String msg;
            try {
                JSONObject jsonObject = OkHttpUtils.getJson("https://api.kuku.me/tool/peeping/result/" + random);
                if (jsonObject.getInteger("code") == 200) {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
                    sb.append("检测到共有").append(jsonArray.size()).append("位小伙伴在窥屏").append("\n");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject singleJsonObject = jsonArray.getJSONObject(i);
                        sb.append(singleJsonObject.getString("ip"))
                                .append("-").append(singleJsonObject.getString("address"))
                                /*.append("-").append(singleJsonObject.getString("simpleUserAgent"))*/.append("\n");
                    }
                    msg = BotUtils.removeLastLine(sb);
                } else msg = jsonObject.getString("message");
            } catch (IOException e) {
                e.printStackTrace();
                msg = "查询失败，请重试！！";
            }
            group.sendMessage(FunKt.getMif().text(msg).toMessage());
        }, 15, TimeUnit.SECONDS);
    }

    @Action("学习 {learn} {say}")
    @QMsg(at = true)
    public String learn(ContextSession session, long qq, GroupEntity groupEntity, Group group, @PathVar(2) String type, String learn, String say) {
        if (learn.equals("草") || learn.contains("早") || learn.contains("晚")) {
            return "无法学习该词汇！";
        } else if (toolLogic.alreadyLearned(learn, groupEntity)) {
            return "无法学习该词汇！该词汇已被学习！";
        } else {
            MessageItemFactory mif = FunKt.getMif();
            JSONObject jsonObject = new JSONObject();
            JSONArray aJsonArray = BotUtils.messageToJsonArray(mif.text(say).toMessage());
            jsonObject.put("q", learn);
            jsonObject.put("a", aJsonArray);
            if (type == null) type = "PARTIAL";
            if (!"ALL".equalsIgnoreCase(type)) type = "PARTIAL";
            else type = "ALL";
            jsonObject.put("type", type);
            JSONArray jsonArray = groupEntity.getLearnJsonArray();
            jsonArray.add(jsonObject);
            groupEntity.setLearnJsonArray(jsonArray);
            groupService.save(groupEntity);
            return "学习成功！快对我说 " + learn + " 试试吧！";
        }
    }

    @Action("复读 {word}")
    @QMsg(at = true)
    public String learn(ContextSession session, long qq, GroupEntity groupEntity, Group group, @PathVar(2) String type, String word) {
        String message = learn(session, qq, groupEntity, group, type, word, word);
        return message;
    }

    @Action("忘记 {word}")
    @QMsg(at = true)
    public String delLearn(GroupEntity groupEntity, String word) {
        JSONArray learnJsonArray = groupEntity.getLearnJsonArray();
        List<JSONObject> delList = new ArrayList<>();
        for (int i = 0; i < learnJsonArray.size(); i++) {
            JSONObject jsonObject = learnJsonArray.getJSONObject(i);
            if (word.equals(jsonObject.getString("q"))) {
                delList.add(jsonObject);
            }
        }
        delList.forEach(learnJsonArray::remove);
        groupEntity.setLearnJsonArray(learnJsonArray);
        groupService.save(groupEntity);
        return "我已经忘记啦！";
    }

}
