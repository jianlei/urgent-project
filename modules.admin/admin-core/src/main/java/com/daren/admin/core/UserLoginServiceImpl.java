package com.daren.admin.core;

import com.daren.admin.api.biz.IUserLoginService;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.core.util.UtilTools;
import com.daren.admin.entities.PermissionBean;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.cooperate.core.model.ErrorCodeValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dell on 14-3-25.
 */

public class UserLoginServiceImpl implements IUserLoginService {
    //    @Named
    /*@Inject
    @Reference(id="userBeanDao",serviceInterface = IUserBeanDao.class)*/
    private IUserBeanDao userBeanDao;

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    private static void setCookie(HttpServletResponse response, String name,
                                  String value, int maxAge){
        if (value == null) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
    }

    @Override
    public UserBean login(String username, String password) {
        UserBean user = userBeanDao.getUser(username);
        if (user == null) {
            return null;
        }
        if (encrypt(password).equals(user.getPassword())) {

            return user;
        } else {
            return null;
        }
    }

    @Override
    public void updateUserLoginInfo(long id, String loginIp) {
        userBeanDao.updateUserLoginInfo(id, loginIp);
    }

    @Override
    public List<String> getUserPermission(String username) {
        List<String> stringList = new ArrayList<String>();
        UserBean userBean = userBeanDao.getUser(username);
        if (userBean != null) {
            List<RoleBean> roleBeanList = userBean.getRoleList();
            for (RoleBean rolebean : roleBeanList) {
                for (PermissionBean permissionBean : rolebean.getPermissionList()) {
                    String permission = permissionBean.getPermission();
                    if (null != permission && !permission.equals(""))
                        stringList.add(permission);

                }
            }
        }
        return stringList;
    }

    private String encrypt(String text) {

        return text;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/loginByPhone")
    public Map loginByPhone(@FormParam("username")String username, @FormParam("password")String password, @FormParam("client")String client,
                            @Context HttpServletRequest request, @Context HttpServletResponse response){
        Map map = new HashMap();
        int result = -1;
        try{
            UserBean user = userBeanDao.findUnique("select u from UserBean u where u.loginName=?1",username);
            if (user!=null && password.equals(user.getPassword())) {
                logon(user, client, request, response);
                result = 1;
                Map resMap = new HashMap();
                resMap.put("user_id",user.getId());
                resMap.put("name",user.getName());
                resMap.put("user_name",user.getLoginName());
                resMap.put("password",user.getPassword());
                map.put("response",resMap);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            map.put("result", result);
        }
        return map;
    }

    private HashMap logon(UserBean user,String client,HttpServletRequest request,HttpServletResponse response) throws Exception {
        int rs = 1;
        HashMap rsMap = new HashMap();
        try{
            String map_provide = "1";//先设成1 用时再改
            String value = user.getId()+"::"+user.getLoginName()+"::"+user.getName()+"::"+map_provide+"::"+client;
            int maxAge = 0;
            if(client.equals("0")) {
                maxAge= 24*60*60;
            } else{
                maxAge= 60*24*60*60;
            }
            String key = UtilTools.generateKey().toString();
            value = UtilTools.encryptString(key, value);
            value = URLEncoder.encode(value + "||" + key, "UTF-8");
            UtilTools.setCookie(response, "jldaren.cooperate", value, maxAge);
            rsMap.put("result", rs);
            rsMap.put("user", user);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rsMap;
    }
    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/updateToken")
    public Map updateToken(@Context HttpServletRequest request, @FormParam("token")String token,@FormParam("user_id") Long user_id) {
        int result =-1;
        Map map = new HashMap();
        try {
            if(user_id!=null){
                userBeanDao.update("update UserRelBean u set u.token=null where u.token='"+token+"'");
                userBeanDao.update("update UserRelBean u set u.token='"+token+"' where u.user_id="+user_id);
                result = 1;
            }
        } catch (Exception e) {
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally{
            map.put("result", result);
        }
        return map;
    }
}
