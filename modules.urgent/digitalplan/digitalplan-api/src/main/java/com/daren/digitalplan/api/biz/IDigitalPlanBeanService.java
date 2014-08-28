package com.daren.digitalplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.digitalplan.entities.DigitalPlanBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IDigitalPlanBeanService extends IBizService {

    List<DigitalPlanBean> queryDigitalPlanSource(DigitalPlanBean digitalplanBean);
}
