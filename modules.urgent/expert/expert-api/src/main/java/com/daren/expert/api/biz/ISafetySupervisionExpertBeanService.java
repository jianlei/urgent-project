package com.daren.expert.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.expert.entities.SafetySupervisionExpertBean;

import java.util.List;

/**
 * 行管专家
 * Created by 张清欣 on 14-7-28.
 */
public interface ISafetySupervisionExpertBeanService extends IBizService {
    List<SafetySupervisionExpertBean> query(SafetySupervisionExpertBean dictBean);
}
