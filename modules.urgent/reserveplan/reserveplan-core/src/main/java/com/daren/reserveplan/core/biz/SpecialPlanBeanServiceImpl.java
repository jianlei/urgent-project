package com.daren.reserveplan.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.api.biz.ISpecialPlanBeanService;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.api.dao.ISpecialPlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.daren.reserveplan.entities.SpecialPlanBean;
import com.daren.reserveplan.entities.SpotPlanBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SpecialPlanBeanServiceImpl extends GenericBizServiceImpl implements ISpecialPlanBeanService {
    private ISpecialPlanBeanDao specialPlanBeanDao;

    public void setSpecialPlanBeanDao(ISpecialPlanBeanDao specialPlanBeanDao) {
        this.specialPlanBeanDao = specialPlanBeanDao;
        super.init(specialPlanBeanDao, SpecialPlanBean.class.getName());
    }

    @Override
    public List<SpecialPlanBean> queryByName(SpecialPlanBean specialPlanBean) {
        return specialPlanBeanDao.find("select a from SpecialPlanBean a where a.name LIKE ?1", "%" + specialPlanBean.getName() + "%");
    }

    @Override
    public List<SpecialPlanBean> queryByReservePlanId(SpecialPlanBean specialPlanBean) {
        return specialPlanBeanDao.find("select a from SpecialPlanBean a where a.reservePlanId LIKE ?1", "%" + specialPlanBean.getReservePlanId() + "%");
    }
}


