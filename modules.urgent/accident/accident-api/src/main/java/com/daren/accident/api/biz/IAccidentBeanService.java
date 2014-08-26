package com.daren.accident.api.biz;


import com.daren.accident.entities.AccidentBean;
import com.daren.core.api.biz.IBizService;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IAccidentBeanService extends IBizService {
    List<AccidentBean> queryAccidentByAccidentLevel();
    public Response addAccident(AccidentBean bean);
}
