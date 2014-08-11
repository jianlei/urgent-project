package com.daren.reserveplan.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.api.biz.ISpecialPlanBeanService;
import com.daren.reserveplan.api.biz.ISpotPlanBeanService;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.api.dao.ISpotPlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;
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

public class SpotPlanBeanServiceImpl extends GenericBizServiceImpl implements ISpotPlanBeanService {
    private ISpotPlanBeanDao spotPlanBeanDao;

    public void setSpotPlanBeanDao(ISpotPlanBeanDao spotPlanBeanDao) {
        this.spotPlanBeanDao = spotPlanBeanDao;
        super.init(spotPlanBeanDao, SpotPlanBean.class.getName());
    }
    @Override
    public List<SpotPlanBean> queryByName(SpotPlanBean spotPlanBean) {
        return spotPlanBeanDao.find("select a from SpotPlanBean a where a.name LIKE ?1", "%" + spotPlanBean.getName() + "%");
    }

    @Override
    public List<SpotPlanBean> queryByReservePlanId(SpotPlanBean spotPlanBean) {
        return spotPlanBeanDao.find("select a from SpotPlanBean a where a.reservePlanId LIKE ?1", "%" + spotPlanBean.getReservePlanId() + "%");
    }
}


