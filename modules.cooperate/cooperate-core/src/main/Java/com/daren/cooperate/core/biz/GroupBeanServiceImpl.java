package com.daren.cooperate.core.biz;

import com.daren.cooperate.api.biz.IGroupBeanService;
import com.daren.cooperate.api.dao.IGroupBasicBeanDao;
import com.daren.cooperate.api.dao.IGroupUserRelBeanDao;
import com.daren.cooperate.api.dao.IGroupUserReqBeanDao;
import com.daren.cooperate.core.model.ErrorCodeValue;
import com.daren.cooperate.core.model.GroupListModel;
import com.daren.cooperate.core.model.GroupReqListModel;
import com.daren.cooperate.core.util.CookieUtil;
import com.daren.cooperate.core.util.SendMsgByXingeThread;
import com.daren.cooperate.entities.GroupBasicBean;
import com.daren.cooperate.entities.GroupUserRelBean;
import com.daren.cooperate.entities.GroupUserReqBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;
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

    public void setGroupBasicBeanDao(IGroupBasicBeanDao groupBasicBeanDao) {
        this.groupBasicBeanDao = groupBasicBeanDao;
    }

    public void setGroupUserRelBeanDao(IGroupUserRelBeanDao groupUserRelBeanDao) {
        this.groupUserRelBeanDao = groupUserRelBeanDao;
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
                String[] idArr;
                if(ids!=null && !"".equals(ids)){
                    idArr = ids.split(",");
                    for(int i=0;i<idArr.length;i++){
                        GroupUserReqBean groupUserReqBean = new GroupUserReqBean();
                        groupUserReqBean.setGroup_id(groupBasicBean.getId());
                        groupUserReqBean.setReq_id(user_id);
                        //groupUserReqBean.setReq_msg(req_msg);
                        groupUserReqBean.setReq_time(DateUtil.convertDateToString(new Date(), DateUtil.longSdf));
                        groupUserReqBean.setRes_type(0);
                        groupUserReqBeanDao.save(groupUserReqBean);           //保存日程和人员关系
                    }
                }
                //推送
                List tokenList = new ArrayList();
                if(tokenList!=null&&!tokenList.isEmpty()){
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("fuction", 2004);
                    pushjsoncontent.put("message", "活动取消提醒:很抱歉，您圈子内的活动"+group_name+"已经取消了!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenList,1,"","",pushjsoncontent);
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
                        "(select cgb.id,cgb.group_name,cgb.group_logo,cgb.group_num from coop_group_basic cgb where cgb.is_generate=1 and cgb.id in \n" +
                        "(select group_id from coop_group_user_rel where user_id=1) order by cgb.create_time desc)\n" +
                        "union\n" +
                        "(select cgb.id,cgb.group_name,cgb.group_logo,cgb.group_num from coop_group_basic cgb where cgb.is_generate=0 and cgb.id in \n" +
                        "(select group_id from coop_group_user_rel where user_id=1) order by cgb.create_time desc)\n" +
                        ") t", GroupListModel.class ,start, page_size);
                if(groupList!=null && groupList.size()>0){
                    map.put("response",groupList);
                }
                List list = groupUserRelBeanDao.find("select count(t.user_id) from GroupUserRelBean t " +
                        "where t.group_id in (select g.group_id from GroupUserRelBean g where g.user_id="+user_id+")");
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
    public Map replyReq(@FormParam("group_id")Integer group_id, @FormParam("res_type")Integer res_type,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(group_id!=null && res_type!=null && user_id>0){
                groupUserReqBeanDao.update("update NoticeUserRelBean t set t.reply_type="+res_type+
                        " where t.notice_id="+group_id+" and t.user_id="+user_id);
                GroupUserRelBean groupUserRelBean = new GroupUserRelBean();
                groupUserRelBean.setGroup_id(group_id);
                groupUserRelBean.setUser_id(user_id);
                groupUserRelBeanDao.save(groupUserRelBean);
                groupBasicBeanDao.update("update GroupBasicBean t set t.group_num=t.group_num+1 where id="+group_id);
                result = 1;
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
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
                groupReqList = groupBasicBeanDao.findByNativeSql("select c.id,c.req_msg,c.req_time,s.name,cgb.group_name  \n" +
                        "from coop_group_user_req c left join sys_user s on s.id=c.req_id \n" +
                        "left join coop_group_basic cgb on cgb.id=c.group_id \n" +
                        "where c.res_id="+user_id, GroupReqListModel.class ,start, page_size);
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

}
