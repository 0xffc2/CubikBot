package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.NeTeaseEntity;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;

@AutoBind
public interface NeTeaseLogic {
    Result<NeTeaseEntity> loginByPhone(String username, String password) throws IOException;
    Result<NeTeaseEntity> loginByQQ(QQLoginEntity qqLoginEntity) throws IOException;
    String sign(NeTeaseEntity neTeaseEntity) throws IOException;
    String listeningVolume(NeTeaseEntity neTeaseEntity) throws IOException;
}
