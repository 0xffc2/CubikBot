package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.HostLocEntity;

import java.util.List;

@AutoBind
public interface HostLocService {
    List<HostLocEntity> findAll();
    HostLocEntity findByQQ(long qq);
    void save(HostLocEntity hostLocEntity);
}
