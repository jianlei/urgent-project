package com.daren.reserveplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.reserveplan.entities.SpotPlanBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface ISpotPlanBeanService extends IBizService {

    List<SpotPlanBean> queryByName(SpotPlanBean spotPlanBean);

    List<SpotPlanBean> queryByReservePlanId(SpotPlanBean spotPlanBean);
}
