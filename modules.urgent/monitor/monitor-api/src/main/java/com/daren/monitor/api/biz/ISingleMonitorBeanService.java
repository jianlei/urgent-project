package com.daren.monitor.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.monitor.entities.SingleMonitorBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * Created by dell on 14-1-17.
 */
public interface ISingleMonitorBeanService extends IBizService {


    List<SingleMonitorBean> querySingleMonitor(SingleMonitorBean singleMonitorBean);

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/{id}")
    public List<SingleMonitorBean> getSingleMonitorBeanListById(@PathParam("id") String enterpriseId);

    @GET
    @Produces("application/json;charset=utf-8")
    List<SingleMonitorBean> getSingleMonitorList();
}
