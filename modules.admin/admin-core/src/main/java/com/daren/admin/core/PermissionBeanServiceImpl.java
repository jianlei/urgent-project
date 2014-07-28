package com.daren.admin.core;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.api.dao.IPermissionBeanDao;
import com.daren.admin.entities.PermissionBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * @类描述：权限服务实现类
 * @创建人：sunlf
 * @创建时间：2014-05-14 上午11:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class PermissionBeanServiceImpl extends GenericBizServiceImpl implements IPermissionBeanService {
    private IPermissionBeanDao permissionBeanDao;

    public void setPermissionBeanDao(IPermissionBeanDao permissionBeanDao) {
        this.permissionBeanDao = permissionBeanDao;
        super.init(permissionBeanDao, PermissionBean.class.getName());

    }

    @Override
    public PermissionBean getRootPermission() {
        return permissionBeanDao.getRootPermission();
    }

    @Override
    public List<PermissionBean> getChildPermission(PermissionBean permissionBean) {
        return permissionBeanDao.getChildPermission(permissionBean);
    }
}