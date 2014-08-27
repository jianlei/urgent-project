package com.daren.hazard.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.hazard.entities.HazardBean;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IHazardBeanService extends IBizService {

    @GET
    @Produces("application/json;charset=utf-8")
    List<HazardBean> getHazardList();

    List<HazardBean> queryHazardSource(HazardBean hazardBean);
}
