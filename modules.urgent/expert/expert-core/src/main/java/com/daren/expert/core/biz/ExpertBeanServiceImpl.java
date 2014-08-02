package com.daren.expert.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.expert.api.biz.IExpertBeanService;
import com.daren.expert.api.dao.IExpertBeanDao;
import com.daren.expert.entities.ExpertBeanImpl;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ExpertBeanServiceImpl extends GenericBizServiceImpl implements IExpertBeanService {
    private IExpertBeanDao expertBeanDao;

    public void setExpertBeanDao(IExpertBeanDao expertBeanDao) {
        this.expertBeanDao = expertBeanDao;
        super.init(expertBeanDao, ExpertBeanImpl.class.getName());
    }
}


