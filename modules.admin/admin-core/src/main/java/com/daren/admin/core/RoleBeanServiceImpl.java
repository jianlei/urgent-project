package com.daren.admin.core;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：角色服务类
 * @创建人：sunlf
 * @创建时间：2014-05-14 上午10:43
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RoleBeanServiceImpl extends GenericBizServiceImpl implements IRoleBeanService {
    private IRoleBeanDao roleBeanDao;
    private IUserBeanDao userBeanDao;

    public void setRoleBeanDao(IRoleBeanDao roleBeanDao) {
        this.roleBeanDao = roleBeanDao;
        super.init(roleBeanDao, RoleBean.class.getName());
    }

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
    }

    @Override
    public List<String> getRoleNameList() {
        return roleBeanDao.getRoleNameList();
    }

    @Override
    public List<String> getRoleNameList(UserBean userBean) {
        return roleBeanDao.getRoleNameList(userBean);
    }

    @Override
    public void saveRoleUser(RoleBean roleBean, List<UserBean> userSelect) {
        List<UserBean> userBeanList = new ArrayList<UserBean>();
        //为新对象
        if (roleBean.getId() == 0L) {
            roleBean = roleBeanDao.save(roleBean);
        } else {
            userBeanList = roleBeanDao.get(RoleBean.class.getName(), roleBean.getId()).getUserList();
        }

        //删除全部该角色下的用户
        if (userSelect.size() == 0) {
            removeRole(roleBean, userBeanList);
        } else {
            removeRole(roleBean, userBeanList);
            //添加角色到用户
            for (UserBean userBean : userSelect) {
                UserBean user = userBeanDao.getUser(userBean.getId());
                if (!user.getRoleList().contains(roleBean)) {
                    user.getRoleList().add(roleBean);
                    userBeanDao.save(user);
                }
            }
        }
    }

    @Override
    public List<RoleBean> query(RoleBean roleBean) {
        return roleBeanDao.find("select a from RoleBean a where a.name LIKE ?1", "%" + roleBean.getName() + "%");
    }

    @Override
    public List<UserBean> getUserList(RoleBean roleBean) {
        return null;
    }

    private void removeRole(RoleBean roleBean, List<UserBean> userBeanList) {
        for (UserBean userBean : userBeanList) {
            userBean.getRoleList().remove(roleBean);
            userBeanDao.save(userBean);
        }
    }
}