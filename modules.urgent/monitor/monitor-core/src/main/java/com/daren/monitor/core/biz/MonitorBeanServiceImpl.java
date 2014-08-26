package com.daren.monitor.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.monitor.api.biz.IMonitorBeanService;
import com.daren.monitor.api.dao.IMonitorBeanDao;
import com.daren.monitor.entities.MonitorBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MonitorBeanServiceImpl extends GenericBizServiceImpl implements IMonitorBeanService {
    private IMonitorBeanDao monitorBeanDao;

    public void setMonitorBeanDao(IMonitorBeanDao monitorBeanDao) {
        this.monitorBeanDao = monitorBeanDao;
        super.init(monitorBeanDao, MonitorBean.class.getName());
    }

    @Override
    public List<MonitorBean> queryMonitor(MonitorBean monitorBean) {
        return monitorBeanDao.find("select a from MonitorBean a where a.name LIKE ?1", "%" + monitorBean.getName() + "%");
    }
}


