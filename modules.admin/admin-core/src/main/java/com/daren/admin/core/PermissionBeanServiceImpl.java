package com.daren.admin.core;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.api.dao.IPermissionBeanDao;
import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.entities.PermissionBean;
import com.daren.admin.entities.RoleBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;
import java.util.Set;

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
    private IRoleBeanDao roleBeanDao;

    public void setPermissionBeanDao(IPermissionBeanDao permissionBeanDao) {
        this.permissionBeanDao = permissionBeanDao;
        super.init(permissionBeanDao, PermissionBean.class.getName());

    }

    public void setRoleBeanDao(IRoleBeanDao roleBeanDao) {
        this.roleBeanDao = roleBeanDao;
    }

    @Override
    public PermissionBean getRootPermission() {
        return permissionBeanDao.getRootPermission();
    }

    @Override
    public List<PermissionBean> getChildPermission(PermissionBean permissionBean) {
        return permissionBeanDao.getChildPermission(permissionBean);
    }

    @Override
    public List<PermissionBean> query(PermissionBean permissionBean) {
        return null;
    }

    @Override
    public List<PermissionBean> getRootBeanList() {
        return permissionBeanDao.find("select u from PermissionBean u where u.parent is null");
    }

    @Override
    public List<PermissionBean> getChildBeanList(PermissionBean permissionBean) {
        return permissionBeanDao.find("select u from PermissionBean u where u.parent= ?1", permissionBean);
    }

    /**
     * 根据角色分配权限
     * @param roleBean 角色bean
     * @param checkedNodes 权限列表
     */
    @Override
    public void assignPermission(RoleBean roleBean, Set<PermissionBean> checkedNodes) {
        roleBean.getPermissionList().clear();
        for(PermissionBean bean:checkedNodes){
            roleBean.getPermissionList().add(bean);
        }
        roleBeanDao.save(roleBean);
    }
}