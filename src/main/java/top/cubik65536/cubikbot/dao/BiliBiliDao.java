package top.cubik65536.cubikbot.dao;

import com.icecreamqaq.yudb.YuDao;
import com.icecreamqaq.yudb.jpa.annotation.Dao;
import com.icecreamqaq.yudb.jpa.annotation.Execute;
import com.icecreamqaq.yudb.jpa.annotation.Select;
import top.cubik65536.cubikbot.entity.BiliBiliEntity;

import java.util.List;

@Dao
public interface BiliBiliDao extends YuDao<BiliBiliEntity, Integer> {
    BiliBiliEntity findByQQ(Long qq);
    @Execute("delete from BiliBiliEntity where qq = ?0")
    int delByQQ(Long qq);
    List<BiliBiliEntity> findByMonitor(Boolean monitor);
    @Select("from BiliBiliEntity")
    List<BiliBiliEntity> findAll();
    List<BiliBiliEntity> findByTask(Boolean task);
}