package com.daren.admin.api.biz;

import com.daren.admin.entities.RoleBeanImpl;
import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.api.biz.IBizService;

import java.util.List;

/**
 * @类描述：角色管理业务接口类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午6:29
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IRoleBeanService extends IBizService {
    /**
     * 获得角色名称列表
     * @return
     */
    List<String> getRoleNameList();

    /**
     * 根据角色保存该角色关联的用户列表
     * @param roleBean
     * @param userSelect
     */
    void saveRoleUser(RoleBeanImpl roleBean, List<UserBeanImpl> userSelect);
}
