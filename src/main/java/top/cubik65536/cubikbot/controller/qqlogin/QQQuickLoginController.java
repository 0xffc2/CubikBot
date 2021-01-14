package top.cubik65536.cubikbot.controller.qqlogin;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.annotation.Path;
import com.IceCreamQAQ.Yu.annotation.Synonym;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.controller.BotActionContext;
import top.cubik65536.cubikbot.entity.BiliBiliEntity;
import top.cubik65536.cubikbot.entity.NeTeaseEntity;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.logic.BiliBiliLogic;
import top.cubik65536.cubikbot.logic.NeTeaseLogic;
import top.cubik65536.cubikbot.pojo.Result;
import top.cubik65536.cubikbot.service.BiliBiliService;
import top.cubik65536.cubikbot.service.NeTeaseService;
import top.cubik65536.cubikbot.service.QQLoginService;

import javax.inject.Inject;
import java.io.IOException;

@GroupController
@Path("qqquicklogin")
@SuppressWarnings("unused")
public class QQQuickLoginController {

	@Inject
	private QQLoginService qqLoginService;
	@Inject
	private BiliBiliService biliBiliService;
	@Inject
	private BiliBiliLogic biliBiliLogic;
	@Inject
	private NeTeaseLogic neTeaseLogic;
	@Inject
	private NeTeaseService neTeaseService;

	@Before
	public void before(long qq, BotActionContext actionContext){
		QQLoginEntity qqLoginEntity = qqLoginService.findByQQ(qq);
		if (qqLoginEntity == null) throw FunKt.getMif().at(qq).plus("您还没有绑定QQ号，请绑定后再试！！<群聊发送<qqlogin qr>或者私聊发送<qqlogin pwd>>").toThrowable();
		else if (!qqLoginEntity.getStatus()) throw FunKt.getMif().at(qq).plus("您的QQ已失效，请更新后再试！！").toThrowable();
		else actionContext.set("qqLoginEntity", qqLoginEntity);
	}

	@Action("bilibili")
	@Synonym("bl")
	public String biliBiliLogin(QQLoginEntity qqLoginEntity, long qq, long group) throws IOException {
		BiliBiliEntity biliBiliEntity = biliBiliService.findByQQ(qq);
		if (biliBiliEntity == null) biliBiliEntity = new BiliBiliEntity(qq, group);
		Result<BiliBiliEntity> result = biliBiliLogic.loginByQQ(qqLoginEntity);
		if (result.getCode() == 200){
			BiliBiliEntity newBiliBiliEntity = result.getData();
			biliBiliEntity.setCookie(newBiliBiliEntity.getCookie());
			biliBiliEntity.setUserId(newBiliBiliEntity.getUserId());
			biliBiliEntity.setToken(newBiliBiliEntity.getToken());
			biliBiliService.save(biliBiliEntity);
			return "绑定或者更新哔哩哔哩成功！！";
		}else return result.getMessage();
	}


	@Action("wy")
	public String wyLogin(QQLoginEntity qqLoginEntity, long qq, long group) throws IOException {
		NeTeaseEntity neTeaseEntity = neTeaseService.findByQQ(qq);
		if (neTeaseEntity == null) neTeaseEntity = new NeTeaseEntity(qq);
		Result<NeTeaseEntity> result = neTeaseLogic.loginByQQ(qqLoginEntity);
		if (result.getCode() == 200){
			NeTeaseEntity newNeTeaseEntity = result.getData();
			neTeaseEntity.set__csrf(newNeTeaseEntity.get__csrf());
			neTeaseEntity.setMUSIC_U(newNeTeaseEntity.getMUSIC_U());
			neTeaseService.save(neTeaseEntity);
			return "绑定或者更新网易成功！！";
		}else return result.getMessage();
	}


}
