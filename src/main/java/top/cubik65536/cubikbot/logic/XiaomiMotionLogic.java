package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;

@AutoBind
public interface XiaomiMotionLogic {
    Result<String> login(String phone, String password) throws IOException;
    String changeStep(String token, int step) throws IOException;
}
