package top.cubik65536.cubikbot.service.impl;

import top.cubik65536.cubikbot.dao.HostLocDao;
import top.cubik65536.cubikbot.entity.HostLocEntity;
import top.cubik65536.cubikbot.service.HostLocService;

import javax.inject.Inject;
import java.util.List;

public class HostLocServiceImpl implements HostLocService {

    @Inject
    private HostLocDao hostLocDao;

    @Override
    public List<HostLocEntity> findAll() {
        return hostLocDao.findAll();
    }

    @Override
    public HostLocEntity findByQQ(long qq) {
        return hostLocDao.findByQQ(qq);
    }

    @Override
    public void save(HostLocEntity hostLocEntity) {
        hostLocDao.saveOrUpdate(hostLocEntity);
    }
}
