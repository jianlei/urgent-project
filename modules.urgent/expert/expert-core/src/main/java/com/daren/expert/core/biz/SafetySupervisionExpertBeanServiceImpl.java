package com.daren.expert.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.expert.api.biz.ISafetySupervisionExpertBeanService;
import com.daren.expert.api.dao.ISafetySupervisionExpertBeanDao;
import com.daren.expert.entities.SafetySupervisionExpertBean;

import java.util.List;

/**
 * @类描述：行管专家服务实现类
 * @创建人：张清欣
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SafetySupervisionExpertBeanServiceImpl extends GenericBizServiceImpl implements ISafetySupervisionExpertBeanService {
    private ISafetySupervisionExpertBeanDao safetySupervisionExpertBeanDao;

    public void setSafetySupervisionExpertBeanDao(ISafetySupervisionExpertBeanDao safetySupervisionExpertBeanDao) {
        this.safetySupervisionExpertBeanDao = safetySupervisionExpertBeanDao;
        super.init(safetySupervisionExpertBeanDao, SafetySupervisionExpertBean.class.getName());
    }

    @Override
    public List<SafetySupervisionExpertBean> query(SafetySupervisionExpertBean dictBean) {
        return safetySupervisionExpertBeanDao.find("select e from SafetySupervisionExpertBean e where e.name=?1 ", dictBean.getName());
    }
}


