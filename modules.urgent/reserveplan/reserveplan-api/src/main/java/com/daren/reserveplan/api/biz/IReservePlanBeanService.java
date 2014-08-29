package com.daren.reserveplan.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.reserveplan.entities.ReservePlanBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IReservePlanBeanService extends IBizService {
    List<ReservePlanBean> queryByName(ReservePlanBean reservePlanBean);

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/{enterpriseId}")
    public List<ReservePlanBean> getReservePlanByEnterpriseId(@PathParam("enterpriseId") String enterpriseId);
}
