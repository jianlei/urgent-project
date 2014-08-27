package com.daren.rescue.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.rescue.entities.RescueBean;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IRescueBeanService extends IBizService {
    List<RescueBean> query(RescueBean dictBean);

    HashMap<String ,String > getAllBeansToHashMap();


    @GET
    @Produces("application/json;charset=utf-8")
    List<RescueBean> getRescueList();
}
