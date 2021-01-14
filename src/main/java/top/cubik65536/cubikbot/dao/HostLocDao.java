package top.cubik65536.cubikbot.dao;

import com.icecreamqaq.yudb.jpa.JPADao;
import com.icecreamqaq.yudb.jpa.annotation.Select;
import org.jetbrains.annotations.NotNull;
import top.cubik65536.cubikbot.entity.HostLocEntity;

import java.util.List;

public interface HostLocDao extends JPADao<HostLocEntity, Integer> {
    @NotNull
    @Select("from HostLocEntity")
    List<HostLocEntity> findAll();
    HostLocEntity findByQQ(long qq);
}
