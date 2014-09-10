package com.daren.admin.api.biz;


import com.daren.admin.entities.UserBean;
import com.daren.core.api.biz.IBizService;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUserBeanService extends IBizService {
    public UserBean addUser(UserBean user);

    public void delUser(UserBean user);

    public void saveUser(UserBean user);


    public List<UserBean> getAllUser();

    List<UserBean> queryUser(UserBean userBean);

    /**
     * 分页查询用户
     *
     * @param userName   用户名
     * @param pageNumber 页数
     * @param pageSize   每页大小
     * @return 用户结果集
     */
    public List<UserBean> queryUser(String userName, int pageNumber, int pageSize);

    List<UserBean> query(UserBean userBean);

    /**
     * 生成roleList列表，以逗号分隔
     *
     * @param userBean 用户
     * @return
     */
    public String getRoleList(UserBean userBean);

    /**
     * 保存用户以及相关的角色
     *
     * @param userBean
     * @param roleSelect
     */
    void saveUserRole(UserBean userBean, List<String> roleSelect);

    /**
     * 获得当前登陆用户名称
     * @return
     */
    String getCurrentUserName();
}
