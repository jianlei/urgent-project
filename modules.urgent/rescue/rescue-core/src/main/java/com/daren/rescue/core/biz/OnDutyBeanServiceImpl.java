package com.daren.rescue.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.rescue.api.biz.IOnDutyBeanService;
import com.daren.rescue.api.dao.IOnDutyBeanDao;
import com.daren.rescue.entities.OnDutyBean;

import java.util.List;

/**
 * Created by Administrator on 2014/8/8.
 */
public class OnDutyBeanServiceImpl extends GenericBizServiceImpl implements IOnDutyBeanService {
    private IOnDutyBeanDao onDutyBeanDao;

    public void setOnDutyBeanDao(IOnDutyBeanDao onDutyBeanDao) {
        this.onDutyBeanDao = onDutyBeanDao;
        super.init(onDutyBeanDao, OnDutyBean.class.getName());
    }

    @Override
    public void deleteOnDutyBeanById(long id) {
        onDutyBeanDao.remove(OnDutyBean.class.getName(), id);
    }

    @Override
    public List<OnDutyBean> getOnDutyBeanByAttach(long id) {
        return onDutyBeanDao.find("select o from OnDutyBean o where o.attach=?1 ", id);
    }
}
