package top.cubik65536.cubikbot.controller.manage;

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
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.service.GroupService;
import top.cubik65536.cubikbot.utils.BotUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GroupController
@SuppressWarnings("unused")
public class ManageAdminController {
    @Config("YuQ.Mirai.bot.master")
    private String master;
    @Inject
    private GroupService groupService;

    @Before
    public GroupEntity before(Member qq, long group){
        GroupEntity groupEntity = groupService.findByGroup(group);
        if (groupEntity == null) groupEntity = new GroupEntity(group);
        if (groupEntity.isAdmin(qq.getId()) || qq.getId() == Long.parseLong(master) || qq.isAdmin()){
            return groupEntity;
        }else throw FunKt.getMif().at(qq).plus("您的权限不足，无法执行！！").toThrowable();
    }

    @Action("清屏")
    public String clear(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) sb.append("\n");
        return sb.toString();
    }

    @Action("禁言 {qqNo}")
    @QMsg(at = true)
    public String shutUp(long group, long qqNo, @PathVar(2) String timeStr){
        int time;
        if (timeStr == null) time = 0;
        else {
            if (timeStr.length() == 1) return "未发现时间单位！！单位可为（s,m,h,d）";
            int num = Integer.parseInt(timeStr.substring(0, timeStr.length() - 1));
            switch (timeStr.charAt(timeStr.length() - 1)){
                case 's': time = num; break;
                case 'm': time = num * 60; break;
                case 'h': time = num * 60 * 60; break;
                case 'd': time = num * 60 * 60 * 24; break;
                default: return "禁言时间格式不正确";
            }
        }
        FunKt.getYuq().getGroups().get(group).get(qqNo).ban(time);
        return "禁言成功！！";
    }

    @Action("CubikBot {status}")
    @Synonym({"整点报时 {status}", "自动审核 {status}",
            "欢迎语 {status}", "退群拉黑 {status}", "鉴黄 {status}", "色图 {status}",
            "撤回通知 {status}", "闪照通知 {status}", "复读机 {status}"})
    @QMsg(at = true)
    public String onOrOff(GroupEntity groupEntity, boolean status, @PathVar(0) String op){
        String systemStatus = " ";
        switch (op){
            case "CubikBot":
                groupEntity.setStatus(status);
                if (status) {
                    systemStatus = "\nWelcome to CubikBot | Based on YuQ-Mirai | Powered by Cubik Network\n" +
                            "Version: 1.0-ALPHA | THIS IS AN INTERNAL TEST VERSION!!!\n" +
                            "Plgins : id 'java'\n" +
                            "    id \"com.github.johnrengelman.shadow\" version \"6.1.0\"\n" +
                            "    id 'org.jetbrains.kotlin.jvm' version '1.4.21'" +
                            "Repositories : maven { url \"https://plugins.gradle.org/m2/\" }\n" +
                            "    maven { url \"https://maven.icecreamqaq.com/repository/maven-public/\" }\n" +
                            "    maven { url \"https://nexus.kuku.me/repository/maven-public/\" }\n" +
                            "    mavenCentral()\n" +
                            "    jcenter()\n" +
                            "Dependencies : testCompile group: 'junit', name: 'junit', version: '4.12'\n" +
                            "    implementation 'org.projectlombok:lombok:1.18.16'\n" +
                            "    annotationProcessor 'org.projectlombok:lombok:1.18.16'\n" +
                            "    implementation 'com.IceCreamQAQ.YuQ:YuQ-Mirai:0.1.0.0-DEV12'\n" +
                            "    implementation 'com.IceCreamQAQ.Yu:Yu-DB:0.0.2.0-DEV8'\n" +
                            "    implementation 'com.IceCreamQAQ.Yu:WebCore:0.0.2.0-DEV5'\n" +
                            "    implementation 'com.IceCreamQAQ:Yu-Core:0.2.0.0-DEV7'\n" +
                            "    implementation 'org.jsoup:jsoup:1.13.1'\n" +
                            "    implementation 'com.h2database:h2:1.4.200'\n" +
                            "    implementation 'com.github.oshi:oshi-core:5.3.5'\n" +
                            "Java Environement : java 1.8.0_271, Java Compile Encoding : UTF-8\n" +
                            "Main Class : top.cubik65536.cubikbot.Start\n";
                } else {

                }
                break;
            case "整点报时":
                groupEntity.setOnTimeAlarm(status);
                break;
            case "自动审核":
                groupEntity.setAutoReview(status);
                break;
            case "欢迎语":
                groupEntity.setWelcomeMsg(status);
                break;
            case "退群拉黑":
                groupEntity.setLeaveGroupBlack(status);
                break;
            case "鉴黄":
                groupEntity.setPic(status);
                break;
            case "色图":
                groupEntity.setColorPic(status);
                break;
            case "撤回通知":
                groupEntity.setRecall(status);
                break;
            case "闪照通知":
                groupEntity.setFlashNotify(status);
                break;
            case "复读机":
                groupEntity.setRepeat(status);
                break;
            default:
                return null;
        }
        groupService.save(groupEntity);
        if (status) return systemStatus + op + "开启成功";
        else return systemStatus + op + "关闭成功";
    }

    @Action("加问答 {q}")
    @QMsg(at = true)
    public String qa(ContextSession session, long qq, GroupEntity groupEntity, String q, Group group, @PathVar(2) String type) {
        MessageItemFactory mif = FunKt.getMif();
        group.sendMessage(mif.at(qq).plus("请输入回答语句！！"));
        Message a = session.waitNextMessage();
        JSONObject jsonObject = new JSONObject();
        JSONArray aJsonArray = BotUtils.messageToJsonArray(a);
        jsonObject.put("q", q);
        jsonObject.put("a", aJsonArray);
        System.out.println("aJasonArray = " + aJsonArray);
        if (type == null) type = "PARTIAL";
        if (!"ALL".equalsIgnoreCase(type)) type = "PARTIAL";
        else type = "ALL";
        jsonObject.put("type", type);
        JSONArray jsonArray = groupEntity.getQaJsonArray();
        jsonArray.add(jsonObject);
        groupEntity.setQaJsonArray(jsonArray);
        groupService.save(groupEntity);
        return "添加问答成功！！";
    }

    @Action("删问答 {q}")
    @QMsg(at = true)
    public String delQa(GroupEntity groupEntity, String q) {
        JSONArray qaJsonArray = groupEntity.getQaJsonArray();
        List<JSONObject> delList = new ArrayList<>();
        for (int i = 0; i < qaJsonArray.size(); i++) {
            JSONObject jsonObject = qaJsonArray.getJSONObject(i);
            if (q.equals(jsonObject.getString("q"))) {
                delList.add(jsonObject);
            }
        }
        delList.forEach(qaJsonArray::remove);
        groupEntity.setQaJsonArray(qaJsonArray);
        groupService.save(groupEntity);
        return "删除问答成功！！";
    }

}
