package com.daren.danger.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.danger.entities.DangerBean;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IDangerBeanService extends IBizService {

    @GET
    @Produces("application/json;charset=utf-8")
    List<DangerBean> getDangerList();

    List<DangerBean> queryDangerSource(DangerBean dangerBean);
}
