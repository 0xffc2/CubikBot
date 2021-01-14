package top.cubik65536.cubikbot.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.QQBindEntity;

@AutoBind
public interface QQBindService {
	QQBindEntity findByQQ(long qq);
	void save(QQBindEntity qqBindEntity);
}
