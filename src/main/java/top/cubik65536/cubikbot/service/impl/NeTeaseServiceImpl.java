package top.cubik65536.cubikbot.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.cubikbot.dao.NeTeaseDao;
import top.cubik65536.cubikbot.entity.NeTeaseEntity;
import top.cubik65536.cubikbot.service.NeTeaseService;

import javax.inject.Inject;
import java.util.List;

public class NeTeaseServiceImpl implements NeTeaseService {

    @Inject
    private NeTeaseDao neTeaseDao;

    @Override
    @Transactional
    public NeTeaseEntity findByQQ(Long qq) {
        return neTeaseDao.findByQQ(qq);
    }

    @Override
    @Transactional
    public void save(NeTeaseEntity neTeaseEntity) {
        neTeaseDao.saveOrUpdate(neTeaseEntity);
    }

    @Override
    @Transactional
    public List<NeTeaseEntity> findAll() {
        return neTeaseDao.findAll();
    }

    @Override
    @Transactional
    public int deByQQ(Long qq) {
        return neTeaseDao.delByQQ(qq);
    }
}
