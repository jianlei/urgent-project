package com.daren.digitalplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.digitalplan.entities.DigitalPlanBean;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IDigitalPlanBeanService extends IBizService {
    List<DigitalPlanBean> queryDigitalPlanSource(DigitalPlanBean digitalplanBean);

    @GET
    @Produces("application/json;charset=utf-8")
    List<DigitalPlanBean> getAllDigitalPlanList();
}
