package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.MotionEntity;

import java.util.List;

@AutoBind
public interface MotionService {
    MotionEntity findByQQ(long qq);
    List<MotionEntity> findAll();
    void save(MotionEntity motionEntity);
    void delByQQ(Long qq);
}