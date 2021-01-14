package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.icecreamqaq.yuq.annotation.GroupController;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliController;
import top.cubik65536.cubikbot.controller.bilibili.BiliBiliLoginController;
import top.cubik65536.cubikbot.controller.manage.ManageAdminController;
import top.cubik65536.cubikbot.controller.manage.ManageNotController;
import top.cubik65536.cubikbot.controller.manage.ManageSuperAdminController;
import top.cubik65536.cubikbot.controller.qqlogin.BindQQController;
import top.cubik65536.cubikbot.controller.qqlogin.QQJobController;
import top.cubik65536.cubikbot.controller.qqlogin.QQLoginController;
import top.cubik65536.cubikbot.controller.qqlogin.QQQuickLoginController;
import top.cubik65536.cubikbot.utils.BotUtils;

import java.util.List;

@GroupController
@SuppressWarnings("unused")
public class MenuController {

    @Action("help")
    public String help(){
        return parse("help", MenuController.class);
    }

    @Action("tool")
    public String tool(){
        return parse("tool", ToolController.class);
    }

    @Action("bilibili")
    public String bl(){
        return parse("bilibili", BiliBiliLoginController.class, BiliBiliController.class);
    }

    @Action("bot")
    public String bot(){
        return parse("bot", BotController.class);
    }

    @Action("manage")
    public String manage(){
        return parse("manage", ManageNotController.class, ManageSuperAdminController.class, ManageAdminController.class);
    }

    @Action("qq")
    public String qq(){
        return parse("qq", QQLoginController.class, BindQQController.class, QQJobController.class, QQQuickLoginController.class);
    }

    @Action("setting")
    public String setting(){
        return parse("设置", SettingController.class);
    }

    @Action("菜单")
    public String menu(){
        return "https://api.kuku.me/menu";
    }


    private String parse(String name, Class<?>...classes){
        List<String> list = BotUtils.menu(classes);
        StringBuilder sb = new StringBuilder().append("╭┅═☆━━━━━━━").append(name).append("━━━┅╮").append("\n");
        for (int i = 0; i < list.size(); i+=2) {
            sb.append("||").append(list.get(i));
            if (i + 1 < list.size()){
                sb.append("   ").append(list.get(i + 1)).append("||");
            }else sb.append("||");
            if (i + 1 < list.size()){
                sb.append("\n");
            }
        }
        return sb.append("╰┅━━━━━━━━━━━━━★═┅╯").toString();
    }


}
