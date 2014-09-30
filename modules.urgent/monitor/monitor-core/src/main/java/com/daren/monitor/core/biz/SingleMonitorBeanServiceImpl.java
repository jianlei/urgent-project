package com.daren.monitor.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.monitor.api.biz.ISingleMonitorBeanService;
import com.daren.monitor.api.dao.ISingleMonitorBeanDao;
import com.daren.monitor.entities.SingleMonitorBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SingleMonitorBeanServiceImpl extends GenericBizServiceImpl implements ISingleMonitorBeanService {
    private ISingleMonitorBeanDao singleMonitorBeanDao;

    public void setSingleMonitorBeanDao(ISingleMonitorBeanDao singleMonitorBeanDao) {
        this.singleMonitorBeanDao = singleMonitorBeanDao;
        super.init(singleMonitorBeanDao, SingleMonitorBean.class.getName());
    }

    @Override
    public List<SingleMonitorBean> querySingleMonitor(SingleMonitorBean singleMonitorBean) {
        return singleMonitorBeanDao.find("select a from SingleMonitorBean a where a.name LIKE ?1", "%" + singleMonitorBean.getName() + "%");
    }

    @Override
    public List<SingleMonitorBean> getSingleMonitorBeanListById(String id) {
        return singleMonitorBeanDao.find("select a from SingleMonitorBean a where a.id = " + id);
    }

    @Override
    public List<SingleMonitorBean> getSingleMonitorList() {
        return singleMonitorBeanDao.find("select a from SingleMonitorBean a");
    }
}


