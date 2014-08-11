package com.daren.reserveplan.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanBeanServiceImpl extends GenericBizServiceImpl implements IReservePlanBeanService {
    private IReservePlanBeanDao reservePlanBeanDao;

    public void setReservePlanBeanDao(IReservePlanBeanDao reservePlanBeanDao) {
        this.reservePlanBeanDao = reservePlanBeanDao;
        super.init(reservePlanBeanDao, ReservePlanBean.class.getName());
    }

    @Override
    public List<ReservePlanBean> queryByName(ReservePlanBean reservePlanBean) {
        return reservePlanBeanDao.find("select a from ReservePlanBean a where a.name LIKE ?1", "%" + reservePlanBean.getName() + "%");
    }
}


