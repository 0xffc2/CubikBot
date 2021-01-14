package top.cubik65536.cubikbot.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.cubikbot.dao.MotionDao;
import top.cubik65536.cubikbot.entity.MotionEntity;
import top.cubik65536.cubikbot.service.MotionService;

import javax.inject.Inject;
import java.util.List;

public class MotionServiceImpl implements MotionService {

    @Inject
    private MotionDao motionDao;

    @Override
    @Transactional
    public MotionEntity findByQQ(long qq) {
        return motionDao.findByQQ(qq);
    }

    @Override
    @Transactional
    public List<MotionEntity> findAll() {
        return motionDao.findAll();
    }

    @Override
    @Transactional
    public void save(MotionEntity motionEntity) {
        motionDao.saveOrUpdate(motionEntity);
    }

    @Override
    @Transactional
    public void delByQQ(Long qq) {
        motionDao.delByQQ(qq);
    }
}
