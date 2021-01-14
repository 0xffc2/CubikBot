package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.pojo.InstagramPojo;
import top.cubik65536.cubikbot.pojo.TwitterPojo;

import java.io.IOException;
import java.util.List;

@AutoBind
public interface MyApiLogic {
    List<TwitterPojo> findTwitterIdByName(String name) throws IOException;

    List<TwitterPojo> findTweetsById(Long id) throws IOException;

    List<InstagramPojo> findInsIdByName(String name) throws IOException;

    List<InstagramPojo> findInsPicById(String name, Long id, Integer page) throws IOException;
}
