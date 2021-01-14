package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AutoBind
public interface HostLocLogic {
    Result<String> login(String username, String password) throws IOException;
    boolean isLogin(String cookie) throws IOException;
    void sign(String cookie) throws IOException;
    List<Map<String, String>> post();
}
