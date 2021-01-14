package top.cubik65536.cubikbot.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.cubikbot.dao.RecallDao;
import top.cubik65536.cubikbot.entity.RecallEntity;
import top.cubik65536.cubikbot.service.RecallService;

import javax.inject.Inject;
import java.util.List;

public class RecallServiceImpl implements RecallService {
    @Inject
    private RecallDao recallDao;
    @Override
    @Transactional
    public List<RecallEntity> findByGroupAndQQ(Long group, Long qq) {
        return recallDao.findByGroupAndQQ(group, qq);
    }

    @Override
    @Transactional
    public void save(RecallEntity recallEntity) {
        recallDao.saveOrUpdate(recallEntity);
    }
}
