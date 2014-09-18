package com.daren.admin.api.biz;


import com.daren.admin.entities.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-2-17
 * Time: 下午5:07
 * 用户登录服务
 */

public interface IUserLoginService {

    /**
     * 用户名不存在
     */
    public final static int USERNAME_NOT_EXIST = -1;

    /**
     * 密码错误
     */
    public final static int PASSWORD_ERROR = -2;

    /**
     * 位置错误
     */
    public final static int UNKOWN_ERROR = -99;

    /**
     * 成功
     */
    public final static int SUCCESS = 1;


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    public UserBean login(String username, String password);

    /**
     * 记录用户登录后的时间和登录ip
     *
     * @param loginIp 登录ip
     * @paramy 用户id
     */
    void updateUserLoginInfo(long id, String loginIp);

    /**
     * 获得用户的权限
     *
     * @param username 用户登录名称
     * @return 权限列表
     */
    public List<String> getUserPermission(String username);

    /**
     * 手机端登录
     * @param username
     * @param password
     * @param client
     * @param request
     * @param response
     * @return
     */
    public Map loginByPhone(String username, String password, String client, HttpServletRequest request,
                            HttpServletResponse response);

    /**
     * 修改用户的token
     * @param token
     * @return
     */
    public Map updateToken(HttpServletRequest request,String token,Long user_id);

}
