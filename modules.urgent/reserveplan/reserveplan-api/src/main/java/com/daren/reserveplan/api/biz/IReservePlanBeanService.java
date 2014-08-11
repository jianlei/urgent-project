package com.daren.reserveplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.reserveplan.entities.ReservePlanBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IReservePlanBeanService extends IBizService {
    List<ReservePlanBean> queryByName(ReservePlanBean reservePlanBean);
}
