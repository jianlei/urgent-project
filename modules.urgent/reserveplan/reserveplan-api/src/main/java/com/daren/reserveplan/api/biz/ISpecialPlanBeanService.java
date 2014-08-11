package com.daren.reserveplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.reserveplan.entities.SpecialPlanBean;
import com.daren.reserveplan.entities.SpotPlanBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface ISpecialPlanBeanService extends IBizService {

    List<SpecialPlanBean> queryByName(SpecialPlanBean specialPlanBean);

    List<SpecialPlanBean> queryByReservePlanId(SpecialPlanBean specialPlanBean);
}
