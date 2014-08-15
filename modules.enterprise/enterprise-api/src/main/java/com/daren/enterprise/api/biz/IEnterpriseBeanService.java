package com.daren.enterprise.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.enterprise.entities.EnterpriseBean;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 14-1-17.
 */
public interface IEnterpriseBeanService extends IBizService {

    List<EnterpriseBean> queryEnterprise(EnterpriseBean enterpriseBean);

    Map<String, String> getAllBeansToHashMap();

    List<EnterpriseBean> findByName(String term, int page, int page_size);
}
