package com.daren.admin.api.dao;

import com.daren.admin.entities.PermissionBeanImpl;
import com.daren.core.api.persistence.IGenericDao;

import java.util.List;

/**
 * @类描述：权限Dao接口类
 * @创建人：sunlf
 * @创建时间：2014-05-14 上午11:21
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IPermissionBeanDao extends IGenericDao<PermissionBeanImpl, Long> {
    PermissionBeanImpl getRootPermission();

    List<PermissionBeanImpl> getChildPermission(PermissionBeanImpl permissionBean);
}
