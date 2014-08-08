package com.daren.admin.api.dao;

import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.core.api.persistence.IGenericDao;

import java.util.List;

/**
 * @类描述：角色管理Dao接口
 * @创建人：sunlf
 * @创建时间：下午6:27
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IRoleBeanDao extends IGenericDao<RoleBean, Long> {
    /**
     * 获得角色名列表
     *
     * @return
     */
    List<String> getRoleNameList();

    /**
     * 通过角色名获得角色对象
     *
     * @param roleName 角色名
     * @return
     */
    public RoleBean getRole(String roleName);

    /**
     * 获得角色名列表
     *
     * @return
     */
    List<String> getRoleNameList(UserBean userBean);
}
