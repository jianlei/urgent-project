package com.daren.reserveplan.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.reserveplan.api.biz.IComprehensivePlanBeanService;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.api.dao.IComprehensivePlanBeanDao;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ComprehensivePlanBeanServiceImpl extends GenericBizServiceImpl implements IComprehensivePlanBeanService {
    private IComprehensivePlanBeanDao comprehensivePlanBeanDao;

    public void setComprehensivePlanBeanDao(IComprehensivePlanBeanDao comprehensivePlanBeanDao) {
        this.comprehensivePlanBeanDao = comprehensivePlanBeanDao;
        super.init(comprehensivePlanBeanDao, ReservePlanBean.class.getName());
    }
}


