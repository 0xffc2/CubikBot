package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AutoBind
public interface QQMailLogic {
    Result<List<Map<String, String>>> getFile(QQLoginEntity qqLoginEntity) throws IOException;

    String fileRenew(QQLoginEntity qqLoginEntity) throws IOException;
}
