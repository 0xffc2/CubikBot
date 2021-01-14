package top.cubik65536.cubikbot.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.cubikbot.dao.WeiboDao;
import top.cubik65536.cubikbot.entity.WeiboEntity;
import top.cubik65536.cubikbot.service.WeiboService;

import javax.inject.Inject;
import java.util.List;

public class WeiboServiceImpl implements WeiboService {
    @Inject
    private WeiboDao weiboDao;
    @Override
    @Transactional
    public WeiboEntity findByQQ(Long qq) {
        return weiboDao.findByQQ(qq);
    }

    @Override
    @Transactional
    public void save(WeiboEntity weiboEntity) {
        weiboDao.saveOrUpdate(weiboEntity);
    }

    @Override
    @Transactional
    public int delByQQ(Long qq) {
        return weiboDao.delByQQ(qq);
    }

    @Override
    @Transactional
    public List<WeiboEntity> findByMonitor(Boolean monitor) {
        return weiboDao.findByMonitor(monitor);
    }

    @Override
    @Transactional
    public List<WeiboEntity> findAll() {
        return weiboDao.findAll();
    }
}
