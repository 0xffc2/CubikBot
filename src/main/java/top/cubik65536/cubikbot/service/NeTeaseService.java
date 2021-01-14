package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.NeTeaseEntity;

import java.util.List;

@AutoBind
public interface NeTeaseService {
    NeTeaseEntity findByQQ(Long qq);
    void save(NeTeaseEntity neTeaseEntity);
    List<NeTeaseEntity> findAll();
    int deByQQ(Long qq);
}
