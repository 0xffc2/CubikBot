package top.cubik65536.cubikbot.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.cubikbot.dao.BiliBiliDao;
import top.cubik65536.cubikbot.dao.QQJobDao;
import top.cubik65536.cubikbot.dao.QQLoginDao;
import top.cubik65536.cubikbot.service.DaoService;

import javax.inject.Inject;

public class DaoServiceImpl implements DaoService {
    @Inject
    private QQJobDao qqJobDao;
    @Inject
    private QQLoginDao qqLoginDao;
    @Inject
    private BiliBiliDao biliBiliDao;

    @Override
    @Transactional
    public void delByQQ(Long qq) {
        qqJobDao.delByQQ(qq);
        qqLoginDao.delByQQ(qq);
        biliBiliDao.delByQQ(qq);
    }
}
