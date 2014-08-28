package com.daren.enterprise.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.enterprise.entities.EnterpriseBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 14-1-17.
 */
public interface IEnterpriseBeanService extends IBizService {

    List<EnterpriseBean> queryEnterprise(EnterpriseBean enterpriseBean);

    Map<String, String> getAllBeansToHashMap();

    List<EnterpriseBean> findByName(String term, int page, int page_size);

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/{id}")
    public EnterpriseBean getEnterpriseBeanByUUId(@PathParam("id") String uuId);

}
