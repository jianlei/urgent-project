package com.daren.enterprise.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.api.dao.IEnterpriseBeanDao;
import com.daren.enterprise.entities.EnterpriseBeanImpl;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseBeanServiceImpl extends GenericBizServiceImpl implements IEnterpriseBeanService {
    private IEnterpriseBeanDao enterpriseBeanDao;

    public void setEnterpriseBeanDao(IEnterpriseBeanDao enterpriseBeanDao) {
        this.enterpriseBeanDao = enterpriseBeanDao;
        super.init(enterpriseBeanDao, EnterpriseBeanImpl.class.getName());
    }
}


