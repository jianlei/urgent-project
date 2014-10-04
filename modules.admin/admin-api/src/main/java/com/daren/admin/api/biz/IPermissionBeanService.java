package com.daren.admin.api.biz;

import com.daren.admin.entities.PermissionBean;
import com.daren.admin.entities.RoleBean;
import com.daren.core.api.biz.IBizService;

import java.util.List;
import java.util.Set;

/**
 * @类描述：权限服务接口类
 * @创建人：sunlf
 * @创建时间：2014-05-14 上午11:19
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IPermissionBeanService extends IBizService {
    /**
     * 获得root权限信息
     *
     * @return
     */
    PermissionBean getRootPermission();

    List<PermissionBean> getChildPermission(PermissionBean permissionBean);

    List<PermissionBean> query(PermissionBean permissionBean);

    List<PermissionBean> getRootBeanList();

    List<PermissionBean> getChildBeanList(PermissionBean permissionBean);

    /**
     * 根据角色分配权限
     * @param roleBean 角色bean
     * @param checkedNodes 权限列表
     */
    void assignPermission(RoleBean roleBean, Set<PermissionBean> checkedNodes);
}
