package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.entity.Friend;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.Message;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliController;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliLoginController;
import top.cubik65536.cubikbot.controller.manage.ManageAdminController;
import top.cubik65536.cubikbot.controller.manage.ManageNotController;
import top.cubik65536.cubikbot.controller.manage.ManageSuperAdminController;
import top.cubik65536.cubikbot.controller.netease.BindNeTeaseController;
import top.cubik65536.cubikbot.controller.netease.NeTeaseController;
import top.cubik65536.cubikbot.controller.qqlogin.BindQQController;
import top.cubik65536.cubikbot.controller.qqlogin.QQJobController;
import top.cubik65536.cubikbot.controller.qqlogin.QQLoginController;
import top.cubik65536.cubikbot.controller.qqlogin.QQQuickLoginController;
import top.cubik65536.cubikbot.controller.warframe.WarframeController;
import top.cubik65536.cubikbot.controller.weibo.WeiboController;
import top.cubik65536.cubikbot.controller.weibo.WeiboNotController;
import top.cubik65536.cubikbot.utils.BotUtils;

@com.IceCreamQAQ.YuWeb.annotation.WebController
@SuppressWarnings("unused")
public class WebController {
    @Action("groupMessage")
    public String groupMessage(String group, String msg){
        Group g = FunKt.getYuq().getGroups().get(Long.parseLong(group));
        if (g == null) return "Error: Group Not Found!";
        g.sendMessage(Message.Companion.toMessage(msg));
        return "OK!";
    }

    @Action("privateMessage")
    public String privateMessage(String qq, String msg){
        Friend f = FunKt.getYuq().getFriends().get(Long.parseLong(qq));
        if (f == null) return "Error: Friend Not Found!";
        f.sendMessage(Message.Companion.toMessage(msg));
        return "OK!";
    }

    @Action("tempMessage")
    public String tempMessage(String qq, String group, String msg){
        Group g = FunKt.getYuq().getGroups().get(Long.parseLong(group));
        if (g == null) return "Error: Group Not Found!";
        Member m = g.getMembers().get(Long.parseLong(qq));
        if (m == null) return "Error: Member Not Found!";
        return "OK!";
    }

    @Action("menu")
    public String menu(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("help", BotUtils.menu(MenuController.class));
        jsonObject.put("tool", BotUtils.menu(ToolController.class));
        jsonObject.put("bilibili", BotUtils.menu(BiliBiliLoginController.class, BiliBiliController.class));
        jsonObject.put("bot", BotUtils.menu(BotController.class));
        jsonObject.put("manage", BotUtils.menu(ManageNotController.class, ManageSuperAdminController.class, ManageAdminController.class));
        jsonObject.put("wy", BotUtils.menu(NeTeaseController.class, BindNeTeaseController.class));
        jsonObject.put("qq", BotUtils.menu(QQLoginController.class, BindQQController.class, QQJobController.class, QQQuickLoginController.class));
        jsonObject.put("setting", BotUtils.menu(SettingController.class));
        jsonObject.put("wb", BotUtils.menu(WeiboNotController.class, WeiboController.class));
        jsonObject.put("wf", BotUtils.menu(WarframeController.class));
        return jsonObject.toString();
    }
}