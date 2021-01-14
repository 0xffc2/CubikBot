package top.cubik65536.cubikbot.dao;

import com.icecreamqaq.yudb.YuDao;
import com.icecreamqaq.yudb.jpa.annotation.Dao;
import top.cubik65536.cubikbot.entity.RecallEntity;

import java.util.List;

@Dao
public interface RecallDao extends YuDao<RecallEntity, Integer> {
    List<RecallEntity> findByGroupAndQQ(Long group, Long qq);
}