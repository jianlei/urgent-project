package com.daren.monitor.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.monitor.entities.MonitorBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * Created by dell on 14-1-17.
 */
public interface IMonitorBeanService extends IBizService {


    List<MonitorBean> queryMonitor(MonitorBean monitorBean);

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/{id}")
    public List<MonitorBean> getMonitorBeanListByEnterpriseId(@PathParam("id") String enterpriseId);

}
