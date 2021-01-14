package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.GroupEntity;

import java.util.List;

@AutoBind
public interface GroupService {
    void save(GroupEntity groupEntity);
    GroupEntity findByGroup(Long group);
    List<GroupEntity> findByOnTimeAlarm(Boolean onTimeAlarm);
    List<GroupEntity> findAll();
    List<GroupEntity> findByLocMonitor(Boolean locMonitor);
}
