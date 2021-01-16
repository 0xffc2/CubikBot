package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.annotation.Config;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.QMsg;
import com.icecreamqaq.yuq.entity.Member;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliController;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliLoginController;
import top.cubik65536.cubikbot.controller.manage.ManageAdminController;
import top.cubik65536.cubikbot.controller.manage.ManageNotController;
import top.cubik65536.cubikbot.controller.manage.ManageSuperAdminController;
import top.cubik65536.cubikbot.controller.qqlogin.BindQQController;
import top.cubik65536.cubikbot.controller.qqlogin.QQJobController;
import top.cubik65536.cubikbot.controller.qqlogin.QQLoginController;
import top.cubik65536.cubikbot.controller.qqlogin.QQQuickLoginController;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.service.GroupService;
import top.cubik65536.cubikbot.utils.BotUtils;

import javax.inject.Inject;
import java.util.List;

@GroupController
@SuppressWarnings("unused")
public class MenuController {

    private String permissionsGroup = "user";

    @Config("YuQ.Mirai.bot.master")
    private String master;
    @Inject
    private GroupService groupService;

    @Before
    public void before(long group, Member qq) {
        GroupEntity groupEntity = groupService.findByGroup(group);
        if (groupEntity.isSuperAdmin(qq.getId())) permissionsGroup = "admin";
        else if (String.valueOf(qq).equals(master) || groupEntity.isSuperAdmin(qq.getId()))
            permissionsGroup = "superAdmin";
        else permissionsGroup = "user";
    }

    private String firstAvailableCommands = "欢迎使用CubikBot，查询权限组{";
    private String sencondAvailableCommands = "}，该权限组可使用的指令有：\n注意，每个参数前后加空格（最后一个参数不需要），不要括号！";
    private String userAvailableCommands = "【help】获取可用指令！\n" +
            "【help {权限组}】获得相关权限组可用指令！\n" +
            "【学习 {关键词} {要我说的话}】让机器人学习一个关键词！当你说的话里有关键词的时候机器人会回复{要我说的话}里的内容！\n" +
            "【复读 {关键词}】让机器人复读一个关键词！当你说的话里有关键词的时候机器人会复读，需要管理开启！\n" +
            "【草】稻草人模式，需要管理打开\n" +
            "【色图】获取色图，需要管理打开\n";
    private String adminAvailableCommands = "管理可以使用所有user权限组的指令！输入【help】查看\n" +
            "【加问答 {问题}】添加一个自动回答的问题！\n" +
            "【删问答 {问题}】删除这个自动回答的问题！\n" +
            "以下所有功能均为相关功能开关，某些功能可能需要将机器人设为管理才能生效，status为\"开\"或\"关\"：\n" +
            "【CubikBot {status}】机器人开关！\n" +
            "【整点报时 {status}】整点报时功能开关！\n" +
            "【自动审核 {status}】自动审核入群请求功能开关！\n" +
            "【欢迎语 {status}】入群欢迎消息开关！\n" +
            "【退群拉黑 {status}】退群者自动拉黑功能开关！\n" +
            "【鉴黄 {status}】鉴黄功能开关！\n" +
            "【色图 {status}】色图功能开关！\n" +
            "【撤回通知 {status}】通知是否有消息撤回！\n" +
            "【闪照通知 {status}】通知是否有闪照发送！\n" +
            "【复读机 {status}】复读机功能开关！\n";

    @Action("help")
    @QMsg(at = true)
    public String help() {
        return help("user");
    }

    @Action("help {permission}")
    @QMsg(at = true)
    public String help(String permission) {
        switch (permission) {
            case "user":
                return firstAvailableCommands + "user" + sencondAvailableCommands + "\n" + userAvailableCommands;
            case "admin":
                if (permissionsGroup.equals("user")) return "您的权限不足，无法执行此命令！！";
                return firstAvailableCommands + "admin" + sencondAvailableCommands + "\n" + adminAvailableCommands;
            default:
                return null;
        }
    }

    // kukubot自带菜单，只开放给超级管理
    @Action("helpOp") //原@Action("help")
    public String helpOp(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("help", MenuController.class);
    }

    @Action("tool")
    public String tool(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("tool", ToolController.class);
    }

    @Action("bilibili")
    public String bl(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("bilibili", BiliBiliLoginController.class, BiliBiliController.class);
    }

    @Action("bot")
    public String bot(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("bot", BotController.class);
    }

    @Action("manage")
    public String manage(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("manage", ManageNotController.class, ManageSuperAdminController.class, ManageAdminController.class);
    }

    @Action("qq")
    public String qq(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("qq", QQLoginController.class, BindQQController.class, QQJobController.class, QQQuickLoginController.class);
    }

    @Action("setting")
    public String setting(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return parse("设置", SettingController.class);
    }

    @Action("菜单")
    public String menu(GroupEntity entity) {
        if (!(permissionsGroup.equals("superAdmin"))) return "您的权限不足，无法执行此命令！！";
        return "https://api.kuku.me/menu";
    }

    private String parse(String name, Class<?>... classes) {
        List<String> list = BotUtils.menu(classes);
        StringBuilder sb = new StringBuilder().append("╭┅═☆━━━━━━━").append(name).append("━━━┅╮").append("\n");
        for (int i = 0; i < list.size(); i += 2) {
            sb.append("||").append(list.get(i));
            if (i + 1 < list.size()) {
                sb.append("   ").append(list.get(i + 1)).append("||");
            } else sb.append("||");
            if (i + 1 < list.size()) {
                sb.append("\n");
            }
        }
        return sb.append("╰┅━━━━━━━━━━━━━★═┅╯").toString();
    }

}
