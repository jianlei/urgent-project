package com.daren.expert.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.api.dao.IEnterpriseExpertBeanDao;
import com.daren.expert.entities.EnterpriseExpertBean;

/**
 * @类描述：企业专家服务实现类
 * @创建人：张清欣
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseExpertBeanServiceImpl extends GenericBizServiceImpl implements IEnterpriseExpertBeanService {
    private IEnterpriseExpertBeanDao enterpriseExpertBeanDao;

    public void setEnterpriseExpertBeanDao(IEnterpriseExpertBeanDao enterpriseExpertBeanDao) {
        this.enterpriseExpertBeanDao = enterpriseExpertBeanDao;
        super.init(enterpriseExpertBeanDao, EnterpriseExpertBean.class.getName());
    }
}


