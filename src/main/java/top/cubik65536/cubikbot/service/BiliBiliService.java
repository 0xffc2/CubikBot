package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.BiliBiliEntity;

import java.util.List;

@AutoBind
public interface BiliBiliService {
    BiliBiliEntity findByQQ(Long qq);
    void save(BiliBiliEntity biliBiliEntity);
    int delByQQ(Long qq);
    List<BiliBiliEntity> findByMonitor(Boolean monitor);
    List<BiliBiliEntity> findAll();
    List<BiliBiliEntity> findByTask(Boolean task);
}
