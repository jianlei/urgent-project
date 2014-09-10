package com.daren.safetycompetency.core;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.safetycompetency.api.biz.ISafetyCompetencyService;
import com.daren.safetycompetency.api.dao.ISafetyCompetencyBeanDao;

/**
 * Created by Administrator on 2014/9/10.
 */
public class SafetyCompetencyServiceImpl extends GenericBizServiceImpl implements ISafetyCompetencyService {
    ISafetyCompetencyBeanDao safetyCompetencyBeanDao;
}
