package top.cubik65536.cubikbot.dao;

import com.icecreamqaq.yudb.jpa.JPADao;
import top.cubik65536.cubikbot.entity.QQBindEntity;

public interface QQBindDao extends JPADao<QQBindEntity, Integer> {
	QQBindEntity findByQQ(long qq);
}
