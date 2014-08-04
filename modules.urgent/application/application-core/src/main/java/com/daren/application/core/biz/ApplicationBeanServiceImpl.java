package com.daren.application.core.biz;

import com.daren.application.entities.ApplicationBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.application.api.biz.IApplicationBeanService;
import com.daren.application.api.dao.IApplicationBeanDao;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ApplicationBeanServiceImpl extends GenericBizServiceImpl implements IApplicationBeanService {
    private IApplicationBeanDao applicationBeanDao;

    public void setApplicationBeanDao(IApplicationBeanDao applicationBeanDao) {
        this.applicationBeanDao = applicationBeanDao;
        super.init(applicationBeanDao, ApplicationBean.class.getName());
    }
}


