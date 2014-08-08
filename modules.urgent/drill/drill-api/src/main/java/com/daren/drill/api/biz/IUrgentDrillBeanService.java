package com.daren.drill.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.drill.entities.UrgentDrillBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUrgentDrillBeanService extends IBizService {
    List<UrgentDrillBean> query(UrgentDrillBean dictBean);
}
