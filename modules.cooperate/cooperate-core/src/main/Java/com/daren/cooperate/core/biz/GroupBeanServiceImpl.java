package com.daren.cooperate.core.biz;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.cooperate.api.biz.IGroupBeanService;
import com.daren.cooperate.api.dao.IGroupBasicBeanDao;
import com.daren.cooperate.api.dao.IGroupUserRelBeanDao;
import com.daren.cooperate.api.dao.IGroupUserReqBeanDao;
import com.daren.cooperate.core.model.*;
import com.daren.cooperate.core.util.CookieUtil;
import com.daren.cooperate.core.util.SendMsgByXingeThread;
import com.daren.cooperate.entities.GroupBasicBean;
import com.daren.cooperate.entities.GroupUserRelBean;
import com.daren.cooperate.entities.GroupUserReqBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;
import com.daren.enterprise.core.model.OrgnizationListModel;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.*;

/**
 * @类描述：群组基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 11:08
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class GroupBeanServiceImpl extends GenericBizServiceImpl implements IGroupBeanService {

    private IGroupBasicBeanDao groupBasicBeanDao;
    private IGroupUserRelBeanDao groupUserRelBeanDao;
    private IGroupUserReqBeanDao groupUserReqBeanDao;
    private IUserBeanService userBeanService;

    public void setGroupBasicBeanDao(IGroupBasicBeanDao groupBasicBeanDao) {
        this.groupBasicBeanDao = groupBasicBeanDao;
    }

    public void setGroupUserRelBeanDao(IGroupUserRelBeanDao groupUserRelBeanDao) {
        this.groupUserRelBeanDao = groupUserRelBeanDao;
    }

    public void setUserBeanService(IUserBeanService userBeanService) {
        this.userBeanService = userBeanService;
    }

    public void setGroupUserReqBeanDao(IGroupUserReqBeanDao groupUserReqBeanDao) {
        this.groupUserReqBeanDao = groupUserReqBeanDao;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/createGroup")
    public Map createGroup(@FormParam("group_name")String group_name, @FormParam("group_logo")String group_logo,
                           @FormParam("ids")String ids,@FormParam("req_msg")String req_msg,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{

            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                GroupBasicBean groupBasicBean = new GroupBasicBean();
                groupBasicBean.setGroup_name(group_name);
                groupBasicBean.setCreate_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                groupBasicBean.setGroup_num(1);
                groupBasicBean.setManager_user(user_id);
                //groupBasicBean.setJgdm();
                //groupBasicBean.setGroup_logo();
                groupBasicBean = groupBasicBeanDao.save(groupBasicBean);
                GroupUserRelBean groupUserRelBean = new GroupUserRelBean();
                groupUserRelBean.setGroup_id(groupBasicBean.getId());
                groupUserRelBean.setUser_id(user_id);
                groupUserRelBeanDao.save(groupUserRelBean);
                List tokenAllList = new ArrayList();
                if(ids!=null && !"".equals(ids)){
                    Gson gson = new Gson();
                    OrgContainerModel orgJson = gson.fromJson(ids,OrgContainerModel.class);
                    List<OrgnizationListModel> orgList = orgJson.getModels();
                    for(int i=0;i<orgList.size();i++){
                        OrgnizationListModel om = orgList.get(i);
                        if(om.getFlag()==2){        //用户
                            GroupUserReqBean groupUserReqBean = new GroupUserReqBean();
                            groupUserReqBean.setGroup_id(groupBasicBean.getId());
                            groupUserReqBean.setReq_id(user_id);
                            groupUserReqBean.setRes_id(Long.parseLong(om.getJgdm()));
                            //groupUserReqBean.setReq_msg(req_msg);
                            groupUserReqBean.setReq_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                            groupUserReqBean.setRes_type(0);
                            groupUserReqBeanDao.save(groupUserReqBean);
                            List tokenList = userBeanService.getUserTokenListByIds(Long.parseLong(om.getJgdm()));
                            tokenAllList.addAll(tokenList);
                        }else{                      //组织机构
                            List<Long> useridList = userBeanService.getUseridListByGgdm(om.getJgdm(),user_id);
                            if(useridList!=null && !useridList.isEmpty()){
                                for(int j=0;j<useridList.size();j++){
                                    GroupUserReqBean groupUserReqBean = new GroupUserReqBean();
                                    groupUserReqBean.setGroup_id(groupBasicBean.getId());
                                    groupUserReqBean.setReq_id(user_id);
                                    groupUserReqBean.setRes_id(useridList.get(j));
                                    //groupUserReqBean.setReq_msg(req_msg);
                                    groupUserReqBean.setReq_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                                    groupUserReqBean.setRes_type(0);
                                    groupUserReqBeanDao.save(groupUserReqBean);
                                }
                            }
                            List tokenJgdmList = userBeanService.getUserTokenListJgdm(om.getJgdm(),user_id);
                            tokenAllList.addAll(tokenJgdmList);
                        }
                    }
                }
                //发送推送
                if(tokenAllList!=null&&!tokenAllList.isEmpty()){
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("function", 1007);
                    pushjsoncontent.put("message", "群组邀请提醒:邀请您加入群组-"+groupBasicBean.getGroup_name()+"!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenAllList,1,"","",pushjsoncontent);
                    Thread thread = new Thread(smxt);
                    thread.start();
                }
                map.put("response",groupBasicBean);
                result =  1;
            }else {
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/updateGroupName")
    @Override
    public Map updateGroupName(@FormParam("group_id")Long group_id,@FormParam("group_name") String group_name,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                //GroupBasicBean groupBasicBean = groupBasicBeanDao.get(GroupBasicBean.class.getName(),group_id);
                groupBasicBeanDao.update("update GroupBasicBean g set g.group_name = '"+group_name+"' where g.id="+group_id );
                result =  1;
            }else {
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/addUserToGroup")
    @Override
    public Map addUserToGroup(@FormParam("group_id")Long group_id, @FormParam("ids")String ids,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                List tokenAllList = new ArrayList();
                if(ids!=null && !"".equals(ids)){
                    Gson gson = new Gson();
                    OrgContainerModel orgJson = gson.fromJson(ids,OrgContainerModel.class);
                    List<OrgnizationListModel> orgList = orgJson.getModels();
                    for(int i=0;i<orgList.size();i++){
                        OrgnizationListModel om = orgList.get(i);
                        if(om.getFlag()==2){        //用户
                            GroupUserReqBean groupUserReqBean = new GroupUserReqBean();
                            groupUserReqBean.setGroup_id(group_id);
                            groupUserReqBean.setReq_id(user_id);
                            //groupUserReqBean.setReq_msg(req_msg);
                            groupUserReqBean.setRes_id(Long.parseLong(om.getJgdm()));
                            groupUserReqBean.setReq_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                            groupUserReqBean.setRes_type(0);
                            groupUserReqBeanDao.save(groupUserReqBean);
                            List tokenList = userBeanService.getUserTokenListByIds(Long.parseLong(om.getJgdm()));
                            tokenAllList.addAll(tokenList);
                        }else{                      //组织机构
                            List<Long> useridList = userBeanService.getUseridListByGgdm(om.getJgdm(),user_id);
                            if(useridList!=null && !useridList.isEmpty()){
                                for(int j=0;j<useridList.size();j++){
                                    GroupUserReqBean groupUserReqBean = new GroupUserReqBean();
                                    groupUserReqBean.setGroup_id(group_id);
                                    groupUserReqBean.setReq_id(user_id);
                                    //groupUserReqBean.setReq_msg(req_msg);
                                    groupUserReqBean.setRes_id(useridList.get(j));
                                    groupUserReqBean.setReq_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                                    groupUserReqBean.setRes_type(0);
                                    groupUserReqBeanDao.save(groupUserReqBean);
                                }
                            }
                            List tokenJgdmList = userBeanService.getUserTokenListJgdm(om.getJgdm(),user_id);
                            tokenAllList.addAll(tokenJgdmList);
                        }
                    }
                }
                //发送推送
                if(tokenAllList!=null&&!tokenAllList.isEmpty()){
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("function", 1007);
                    pushjsoncontent.put("message", "群组邀请提醒:邀请您加入群组-"+1+"!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenAllList,1,"","",pushjsoncontent);
                    Thread thread = new Thread(smxt);
                    thread.start();
                }
                result =  1;
            }else {
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @Override
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/getGroupList/{page}/{page_size}")
    public Map getGroupList(@PathParam("page")Integer page, @PathParam("page_size")Integer page_size,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                List<GroupListModel> groupList;
                int start = 0;
                if(page==null){
                    start=0;
                }else{
                    start = (page-1)*page_size;
                }
                if(page_size==null){
                    page_size = 15;
                }
                groupList = groupBasicBeanDao.findByNativeSql("select * from(\n" +
                        "(select cgb.id,cgb.group_name,cgb.group_logo,cgb.group_num,cgb.manager_user as create_userid " +
                        "from coop_group_basic cgb where cgb.is_generate=1 and cgb.id in \n" +
                        "(select group_id from coop_group_user_rel where user_id="+user_id+") order by cgb.create_time desc)\n" +
                        "union\n" +
                        "(select cgb.id,cgb.group_name,cgb.group_logo,cgb.group_num,cgb.manager_user as create_userid " +
                        "from coop_group_basic cgb where cgb.is_generate=0 and cgb.id in \n" +
                        "(select group_id from coop_group_user_rel where user_id="+user_id+") order by cgb.create_time desc)\n" +
                        ") t", GroupListModel.class ,start, page_size);
                if(groupList!=null && groupList.size()>0){
                    map.put("response",groupList);
                }
                List list = groupUserRelBeanDao.find("select count(t.user_id) from GroupUserRelBean t " +
                        "where t.group_id in (SELECT t1.group_id FROM GroupUserReqBean t1 WHERE t1.res_type=0 and t1.res_id="+user_id+")");
                map.put("group_number",list.get(0));
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/replyReq")
    public Map replyReq(@FormParam("group_id")Long group_id, @FormParam("res_type")Integer res_type, @FormParam("name") String name,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && res_type!=null && user_id>0){
                //修改群组请求表状态
                groupUserReqBeanDao.update("update GroupUserReqBean t set t.res_type="+res_type+
                        " where t.group_id="+group_id+" and t.res_id="+user_id);
                //如果同意，群组人数+1
                if(res_type==1){
                    //创建新的群组-人员关联
                    GroupUserRelBean groupUserRelBean = new GroupUserRelBean();
                    groupUserRelBean.setGroup_id(group_id);
                    groupUserRelBean.setUser_id(user_id);
                    groupUserRelBeanDao.save(groupUserRelBean);
                    groupBasicBeanDao.update("update GroupBasicBean t set t.group_num=t.group_num+1 where t.id="+group_id);   //groupUserReqBean.setRes_id(Long.parseLong(om.getJgdm()));
                }
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @Override
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/getNewReqList/{page}/{page_size}")
    public Map getNewReqList(@PathParam("page")Integer page, @PathParam("page_size")Integer page_size,
                             @Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                List<GroupReqListModel> groupReqList;
                int start = 0;
                if(page==null){
                    start=0;
                }else{
                    start = (page-1)*page_size;
                }
                if(page_size==null){
                    page_size = 15;
                }
                groupReqList = groupBasicBeanDao.findByNativeSql("select c.id,c.req_msg,c.req_time,s.name," +
                        "cgb.group_name,cgb.id as group_id \n" +
                        "from coop_group_user_req c left join sys_user s on s.id=c.req_id \n" +
                        "left join coop_group_basic cgb on cgb.id=c.group_id \n" +
                        "where c.res_id="+user_id+" and c.res_type=0 order by c.req_time desc limit "+start+","+page_size, GroupReqListModel.class);
                if(groupReqList!=null && groupReqList.size()>0){
                    map.put("response",groupReqList);
                }
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/disolveGroup")
    @Override
    public Map disolveGroup(@FormParam("group_id")Long group_id,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && user_id>0){
                groupUserReqBeanDao.update("delete from GroupUserReqBean g where g.group_id="+group_id);    //删除请求表
                groupUserRelBeanDao.update("delete from GroupUserRelBean g where g.group_id="+group_id);  //删除关联表
                groupBasicBeanDao.remove("GroupBasicBean",group_id);    //删除主表
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/deleteUserFromGroup")
    @Override
    public Map deleteUserFromGroup(@FormParam("group_id")Long group_id,@FormParam("delete_user_id") Long delete_user_id,
                                   @Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && user_id>0){
                groupUserRelBeanDao.update("delete from GroupUserRelBean g where g.user_id="+delete_user_id+" and g.group_id="+group_id);  //删除关联
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/quitGroup")
    @Override
    public Map quitGroup(@FormParam("group_id")Long group_id, @Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && user_id>0){
                groupUserRelBeanDao.update("delete from GroupUserRelBean g where g.user_id="+user_id+" and g.group_id="+group_id);  //删除关联
                groupBasicBeanDao.update("update GroupBasicBean g set g.group_num = g.group_num-1");
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @Override
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/getGroupDetail")
    public Map getGroupDetail(@QueryParam("group_id")Long group_id,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && user_id>0){
                List<GroupUserListModel> groupUserListModels = groupUserRelBeanDao.findByNativeSql("select c.user_id,su.name,s.head_spicurl " +
                        "from coop_group_user_rel c left join sys_user su on su.id=c.user_id " +
                        "left join sys_user_rel s on s.user_id = c.user_id where c.user_id!="+user_id+" and c.group_id="+group_id,GroupUserListModel.class);
                Map resMap = new HashMap();
                GroupBasicBean groupBasicBean = groupBasicBeanDao.get(GroupBasicBean.class.getName(),group_id);
                if(groupBasicBean!=null){
                    resMap.put("manager_user",groupBasicBean.getManager_user());
                }

                if(groupUserListModels!=null) {
                    resMap.put("user_list", groupUserListModels);
                }
                map.put("response",resMap);
                result = 1;
            }else{
                map.put("errorCode", ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode", ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

}
