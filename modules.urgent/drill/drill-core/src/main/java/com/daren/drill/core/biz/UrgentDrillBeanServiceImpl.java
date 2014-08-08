package com.daren.drill.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.api.dao.IUrgentDrillBeanDao;
import com.daren.drill.entities.UrgentDrillBean;

import java.util.List;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UrgentDrillBeanServiceImpl extends GenericBizServiceImpl implements IUrgentDrillBeanService {
    private IUrgentDrillBeanDao urgentDrillBeanDao;

    public void setUrgentDrillBeanDao(IUrgentDrillBeanDao urgentDrillBeanDao) {
        this.urgentDrillBeanDao = urgentDrillBeanDao;
        super.init(urgentDrillBeanDao, UrgentDrillBean.class.getName());
    }

    @Override
    public List<UrgentDrillBean> query(UrgentDrillBean dictBean) {
        return urgentDrillBeanDao.find("select e from UrgentDrillBean e where e.name=?1 ", dictBean.getName());
    }
}


