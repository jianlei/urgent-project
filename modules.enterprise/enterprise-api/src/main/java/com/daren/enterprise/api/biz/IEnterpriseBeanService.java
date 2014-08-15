package com.daren.enterprise.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.enterprise.entities.EnterpriseBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IEnterpriseBeanService extends IBizService {

    List<EnterpriseBean> queryEnterprise(EnterpriseBean enterpriseBean);
    HashMap<String ,String > getAllBeansToHashMap();
}
