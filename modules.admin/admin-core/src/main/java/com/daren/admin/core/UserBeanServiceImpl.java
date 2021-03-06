package com.daren.admin.core;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public class UserBeanServiceImpl extends GenericBizServiceImpl implements IUserBeanService {
    private IUserBeanDao userBeanDao;
    private IRoleBeanDao roleBeanDao;

//    public void setUserBeanDao(IUserBeanDao userBeanDao) {
//        this.userBeanDao = userBeanDao;
////        super.init(userBeanDao, UserBean.class.getName());
//    }
//
//    public void setRoleBeanDao(IRoleBeanDao roleBeanDao) {
//        this.roleBeanDao = roleBeanDao;
//    }


    public IRoleBeanDao getRoleBeanDao() {
        return roleBeanDao;
    }

    public void setRoleBeanDao(IRoleBeanDao roleBeanDao) {
        this.roleBeanDao = roleBeanDao;
    }

    public IUserBeanDao getUserBeanDao() {
        return userBeanDao;
    }

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
        super.init(userBeanDao, UserBean.class.getName());
    }

    public void init() {
        /*UserBeanImpl user = new UserBeanImpl();
        user.setName("test");
        user.setPassword("hello");
        user = addUser(user);
        List<UserBeanImpl> list = this.getAllUser();

        System.out.print("system is called " + list.size());*/
    }

    @Override

    public UserBean addUser(UserBean user) {
        return userBeanDao.saveUser(user);
    }

    @Override
    public void delUser(UserBean user) {
        userBeanDao.removeUser(user.getId());
    }

    @Override
    public void saveUser(UserBean user) {
        userBeanDao.saveUser(user);
    }

    public List<UserBean> getAllUser() {
        List<UserBean> beanList = userBeanDao.getAll(UserBean.class.getName());
        return beanList;
    }

    @Override
    public List<UserBean> queryUser(UserBean userBean) {

        return userBeanDao.find("select a from UserBean a where a.name LIKE ?1", "%" + userBean.getName() + "%");
    }

    @Override
    public List<UserBean> queryUser(String userName, int pageNumber, int pageSize) {

        return userBeanDao.findbyPage("select a from UserBean a where a.name LIKE ?1", pageNumber, pageSize, "%" + userName + "%");
    }

    @Override
    public List<UserBean> query(UserBean userBean) {
        return queryUser(userBean);
    }

    /**
     * 生成roleList列表，以逗号分隔
     *
     * @param userBean 用户
     * @return
     */
    @Override
    public String getRoleList(UserBean userBean) {
        List<RoleBean> roleBeanList =roleBeanDao.find("select r from RoleBean r join r.userList u where u=?1",userBean);
        String value = "";
        if(roleBeanList!=null && roleBeanList.size()>0){
            for (RoleBean roleBean : roleBeanList) {
                value = value + roleBean.getName() + ",";
            }
            //截掉最后一个“，”
            if (value.length() > 1)
                value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    @Override
    public void saveUserRole(UserBean userBean, List<String> roleSelect) {
        List<RoleBean> roleBeanList = userBean.getRoleList();
        //清空全部角色
        roleBeanList.clear();
        //重新添加角色
        if (roleSelect != null && roleSelect.size() > 0) {
            for (String role : roleSelect) {
                roleBeanList.add(roleBeanDao.getRole(role));
            }
        }
        saveUser(userBean);
    }

    @Override
    public String getCurrentUserName() {
        Session session = SecurityUtils.getSubject().getSession();
        UserBean userBean = (UserBean) session.getAttribute("currentUser");
        return userBean.getName();
    }

    @Override
    public String getCurrentUserLoginName() {
        Session session = SecurityUtils.getSubject().getSession();
        UserBean userBean = (UserBean) session.getAttribute("currentUser");
        return userBean.getLoginName();
    }

    @Override
    public UserBean getCurrentUser() {
        Session session = SecurityUtils.getSubject().getSession();
        UserBean userBean = (UserBean) session.getAttribute("currentUser");
        return userBean;
    }

    @Override
    public List getUserTokenListByIds(Long id) {
        return userBeanDao.findByNativeSql("select s.token from sys_user_rel s where s.token is not null and s.user_id in ("+id+")",String.class);
    }

    @Override
    public List getUserTokenListJgdm(String jgdm,long user_id) {
        String jgdmStr = jgdm.replaceAll("(0+)$", "");
        return userBeanDao.findByNativeSql("select sur.token from sys_user s " +
                " left join sys_user_rel sur on sur.user_id=s.id where s.id!="+user_id+
                " and sur.token is not null and s.jgdm like '"+jgdmStr+"%'",String.class);
    }

    @Override
    public List getUserTokenListByNoticeId(Long notice_id,int reply_type,long user_id) {
        return userBeanDao.findByNativeSql("select s.token from sys_user_rel s where s.token is not null and s.user_id in " +
                "(select c.user_id from coop_notice_user_rel c " +
                "where c.user_id != "+user_id+" and c.notice_id="+notice_id+" and c.reply_type="+reply_type+")",String.class);
    }

    @Override
    public List getUseridListByGgdm(String jgdm, long user_id) {
        String jgdmStr = jgdm.replaceAll("(0+)$", "");
        return userBeanDao.findByNativeSql("select s.id from sys_user s " +
                " where s.id!="+user_id+" and s.jgdm like '"+jgdmStr+"%'",Long.class);
    }

    @Override
    public List<UserBean> getUserListByCond(int is_ent_user) {
        return userBeanDao.findByNativeSql("select * from sys_user u where u.is_ent_user="+is_ent_user,UserBean.class);
    }

}
