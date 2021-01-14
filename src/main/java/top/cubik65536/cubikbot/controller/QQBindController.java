package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.icecreamqaq.yuq.annotation.PrivateController;
import com.icecreamqaq.yuq.controller.BotActionContext;
import top.cubik65536.cubikbot.entity.QQBindEntity;
import top.cubik65536.cubikbot.service.QQBindService;

import javax.inject.Inject;

@SuppressWarnings("unused")
@PrivateController
public class QQBindController {

	@Inject
	private QQBindService qqBindService;

	@Before
	public void before(long qq, BotActionContext actionContext){
		QQBindEntity qqBindEntity = qqBindService.findByQQ(qq);
		if (qqBindEntity == null) qqBindEntity = new QQBindEntity(qq);
		actionContext.set("qqBindEntity", qqBindEntity);
	}

	@Action("ark {cookie}")
	public String bindArt(QQBindEntity qqBindEntity, String cookie){
		qqBindEntity.setArkNightsCookie("HG_ACCOUNT=" + cookie + "; ");
		qqBindService.save(qqBindEntity);
		return "绑定或者更新art成功！！";
	}

}
