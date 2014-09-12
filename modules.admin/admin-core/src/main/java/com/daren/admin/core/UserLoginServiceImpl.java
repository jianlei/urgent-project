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
            UserBean user = userBeanDao.getUser(username);
            if (user!=null && encrypt(password).equals(user.getPassword())) {
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

    /*public HashMap updateToken(HttpServletRequest request) {
        int result =-1;
        String message="失败!";
        HashMap returnMap = new HashMap();
        try {
            User user = (User)request.getAttribute("userinfo");
            if(user!=null && !user.equals("")){
                String token = String.valueOf(request.getParameter("token"));
                HashMap hm = new HashMap();
                hm.put("user_id", user.getUser_id());
                hm.put("token", token);
                //单点登录判断
                hm.put("username", user.getUser_name());
                User checkUserOnline = userService.checkUserOnline(hm);
                int resu = clientConfigMapperService.updateEqualUserToken(hm);
                int updateClientClientConfig = clientConfigMapperService.updateClientClientConfigToken(hm);
                if(updateClientClientConfig>0){
                    userService.updateUserOnline(hm);//更新在线用token
                    result =1;
                    message="成功!";
                    if(checkUserOnline!=null && checkUserOnline.getUser_id()!=0){
                        //判断在线token与用户的token是否相同
                        if(!token.equals(checkUserOnline.getToken())){
                            //push
                            if(checkUserOnline.getToken()!=null&&!checkUserOnline.getToken().equals("")){
                                JSONObject pushjsoncontent = new JSONObject();
                                pushjsoncontent.put("fuction", 1001);
                                pushjsoncontent.put("message", "您已被迫下线!");
                                pushjsoncontent.put("chat_id", 0);
                                SendMsgByXingeThread smxt = new SendMsgByXingeThread(checkUserOnline.getToken(),3,"","",pushjsoncontent);//3单个token  push
                                Thread thread = new Thread(smxt);
                                thread.start();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result =0;
            message="异常!";
        }finally{
            returnMap.put("result", result);
            returnMap.put("message", message);
            return returnMap;
        }
    }*/
}
