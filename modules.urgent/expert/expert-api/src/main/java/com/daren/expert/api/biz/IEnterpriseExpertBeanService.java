package com.daren.expert.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.expert.entities.EnterpriseExpertBean;

import java.util.List;

/**
 * 企业企业专家
 * Created by 张清欣 on 14-7-28.
 */
public interface IEnterpriseExpertBeanService extends IBizService {

    List<EnterpriseExpertBean> query(EnterpriseExpertBean dictBean);
}
