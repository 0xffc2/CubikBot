package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;

@AutoBind
public interface QQAILogic {
    boolean pornIdentification(String imageUrl) throws IOException;

    String generalOCR(String imageUrl) throws IOException;

    String textChat(String question, String session) throws IOException;

    Result<byte[]> voiceSynthesis(String text) throws IOException;
}
