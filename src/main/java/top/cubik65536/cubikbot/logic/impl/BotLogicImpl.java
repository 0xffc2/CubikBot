package top.cubik65536.cubikbot.logic.impl;

import com.IceCreamQAQ.Yu.util.OkHttpWebImpl;
import com.icecreamqaq.yuq.mirai.MiraiBot;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.logic.BotLogic;
import top.cubik65536.cubikbot.utils.BotUtils;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class BotLogicImpl implements BotLogic {
    @Inject
    private OkHttpWebImpl web;
    @Inject
    private MiraiBot miraiBot;

    @Override
    public QQLoginEntity getQQLoginEntity(){
        return BotUtils.toQQLoginEntity(web, miraiBot);
    }

}
