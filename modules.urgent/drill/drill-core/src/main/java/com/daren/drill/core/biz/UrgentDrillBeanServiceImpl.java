package com.daren.drill.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.api.dao.IUrgentDrillBeanDao;
import com.daren.drill.entities.UrgentDrillBean;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
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
}


