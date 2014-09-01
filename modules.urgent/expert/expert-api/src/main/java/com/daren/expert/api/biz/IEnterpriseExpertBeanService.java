package com.daren.expert.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.expert.entities.EnterpriseExpertBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * 企业企业专家
 * Created by 张清欣 on 14-7-28.
 */
public interface IEnterpriseExpertBeanService extends IBizService {

    List<EnterpriseExpertBean> query(EnterpriseExpertBean dictBean);

    /**
     * 物资like查询
     * @创建人：dlw
     * @修改时间：9-1
     */
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/search/{name}")
    List<EnterpriseExpertBean> getLikeEnterpriseExperByName(@PathParam("name")String name);

    /**
     * @修改人：dlw
     * @修改时间：8-26
     * @修改备注：创建getExpertList()方法
     */
    @GET
    @Produces("application/json;charset=utf-8")
    List<EnterpriseExpertBean> getExpertList();

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/scope/{lng}/{lat}/{distance}")
    List<EnterpriseExpertBean> getEnterpriseExpertByScope(@PathParam("lng")String lng,@PathParam("lat")String lat,@PathParam("distance")String distance);
}
