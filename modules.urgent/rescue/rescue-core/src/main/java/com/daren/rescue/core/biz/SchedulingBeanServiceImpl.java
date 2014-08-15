package com.daren.rescue.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.rescue.api.biz.ISchedulingBeanService;
import com.daren.rescue.api.dao.ISchedulingBeanDao;
import com.daren.rescue.entities.SchedulingBean;

import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
public class SchedulingBeanServiceImpl extends GenericBizServiceImpl implements ISchedulingBeanService {
    private ISchedulingBeanDao schedulingBeanDao;

    public void setSchedulingBeanDao(ISchedulingBeanDao schedulingBeanDao) {
        this.schedulingBeanDao = schedulingBeanDao;
        super.init(schedulingBeanDao, SchedulingBean.class.getName());
    }

    @Override
    public void deleteSchedulingBeanById(long id) {
        schedulingBeanDao.remove(SchedulingBean.class.getName(), id);
    }

    @Override
    public List<SchedulingBean> getOnSchedulingBeanByAttach(long id) {
        return schedulingBeanDao.find("select s from SchedulingBean s where s.attach=?1 ", id);
    }
}
