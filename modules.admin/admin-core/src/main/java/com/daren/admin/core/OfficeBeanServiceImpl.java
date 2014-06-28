package com.daren.admin.core;

import com.daren.admin.api.biz.IOfficeBeanService;
import com.daren.admin.api.dao.IOfficeBeanDao;
import com.daren.core.impl.biz.GenericBizServiceImpl;

/**
 * @类描述：机构服务实现类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class OfficeBeanServiceImpl extends GenericBizServiceImpl implements IOfficeBeanService {
    private IOfficeBeanDao officeBeanDao;

    public void setOfficeBeanDao(IOfficeBeanDao officeBeanDao) {
        this.officeBeanDao = officeBeanDao;
        super.init(officeBeanDao, com.daren.admin.entities.OfficeBeanImpl.class.getName());
    }
}
