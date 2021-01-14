package top.cubik65536.cubikbot.dao;

import com.icecreamqaq.yudb.YuDao;
import com.icecreamqaq.yudb.jpa.annotation.Dao;
import com.icecreamqaq.yudb.jpa.annotation.Execute;
import com.icecreamqaq.yudb.jpa.annotation.Select;
import top.cubik65536.cubikbot.entity.NeTeaseEntity;

import java.util.List;

@Dao
public interface NeTeaseDao extends YuDao<NeTeaseEntity, Integer> {
    NeTeaseEntity findByQQ(Long qq);
    @Select("from NeTeaseEntity")
    List<NeTeaseEntity> findAll();
    @Execute("delete from NeTeaseEntity where qq = ?0")
    int delByQQ(Long qq);
}