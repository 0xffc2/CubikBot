package top.cubik65536.cubikbot.service.impl;

import top.cubik65536.cubikbot.dao.QQBindDao;
import top.cubik65536.cubikbot.entity.QQBindEntity;
import top.cubik65536.cubikbot.service.QQBindService;

import javax.inject.Inject;

public class QQBindServiceImpl implements QQBindService {
	@Inject
	private QQBindDao qqBindDao;
	@Override
	public QQBindEntity findByQQ(long qq) {
		return qqBindDao.findByQQ(qq);
	}

	@Override
	public void save(QQBindEntity qqBindEntity) {
		qqBindDao.saveOrUpdate(qqBindEntity);
	}
}
