package com.daren.resources.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.resources.api.biz.IResourcesBeanService;
import com.daren.resources.api.dao.IResourcesBeanDao;
import com.daren.resources.entities.ResourcesBeanImpl;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ResourcesBeanServiceImpl extends GenericBizServiceImpl implements IResourcesBeanService {
    private IResourcesBeanDao resourcesBeanDao;

    public void setResourcesBeanDao(IResourcesBeanDao resourcesBeanDao) {
        this.resourcesBeanDao = resourcesBeanDao;
        super.init(resourcesBeanDao, ResourcesBeanImpl.class.getName());
    }
}


