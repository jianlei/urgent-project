package com.daren.admin.core;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.admin.entities.UserBeanImpl;
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
        super.init(roleBeanDao, com.daren.admin.entities.RoleBeanImpl.class.getName());
    }

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
    }

    @Override
    public List<String> getRoleNameList() {
        return roleBeanDao.getRoleNameList();
    }

    @Override
    public void saveRoleUser(RoleBeanImpl roleBean, List<UserBeanImpl> userSelect) {
        List<UserBeanImpl> userBeanList=new ArrayList<UserBeanImpl>();
        //为新对象
        if(roleBean.getId()==0l){
            roleBean=roleBeanDao.save(roleBean);
        }
        else  {
            userBeanList=roleBeanDao.get(RoleBeanImpl.class.getName(),roleBean.getId()).getUserList();
        }

        //删除全部该角色下的用户
        if(userSelect.size()==0){
            removRole(roleBean, userBeanList);
        }
        else
        {
            removRole(roleBean, userBeanList);
            //添加角色到用户
            for(UserBeanImpl userBean:userSelect){
                UserBeanImpl user=userBeanDao.getUser(userBean.getId());
                if(!user.getRoleList().contains(roleBean))
                {
                    user.getRoleList().add(roleBean);
                    userBeanDao.save(user);
                }
            }
        }
    }

    private void removRole(RoleBeanImpl roleBean, List<UserBeanImpl> userBeanList) {
        for (UserBeanImpl userBean:userBeanList){
            userBean.getRoleList().remove(roleBean);
            userBeanDao.save(userBean);
        }
    }
}