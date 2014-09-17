package com.daren.cooperate.core.biz;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.cooperate.api.biz.INoticeBeanService;
import com.daren.cooperate.api.dao.INoticeBasicBeanDao;
import com.daren.cooperate.api.dao.INoticeUserRelBeanDao;
import com.daren.cooperate.core.model.*;
import com.daren.cooperate.core.util.CookieUtil;
import com.daren.cooperate.core.util.SendMsgByXingeThread;
import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.cooperate.entities.NoticeUserRelBean;
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
 * @类描述：日程基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class NoticeBeanServiceImpl extends GenericBizServiceImpl implements INoticeBeanService {

    private INoticeBasicBeanDao noticeBasicBeanDao;
    private INoticeUserRelBeanDao noticeUserRelBeanDao;
    private IUserBeanService userBeanService;

    public void setNoticeBasicBeanDao(INoticeBasicBeanDao noticeBasicBeanDao) {
        this.noticeBasicBeanDao = noticeBasicBeanDao;
    }

    public void setNoticeUserRelBeanDao(INoticeUserRelBeanDao noticeUserRelBeanDao) {
        this.noticeUserRelBeanDao = noticeUserRelBeanDao;
    }

    public void setUserBeanService(IUserBeanService userBeanService) {
        this.userBeanService = userBeanService;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/createNotice")
    public Map createNotice(@FormParam("title")String title, @FormParam("content")String content,
                                   @FormParam("notice_time")String notice_time, @FormParam("ids")String ids,
                                   @FormParam("user_id")Long user_id1,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                NoticeBasicBean noticeBasicBean = new NoticeBasicBean();
                noticeBasicBean.setTitle(title);
                noticeBasicBean.setContent(content);
                noticeBasicBean.setNotice_time(notice_time);
                noticeBasicBean.setCreate_time(DateUtil.convertDateToString(new Date(),DateUtil.longSdf));
                noticeBasicBean.setUser_id(user_id);
                //noticeBasicBean.setJgdm();                //取监管机构
                noticeBasicBean = noticeBasicBeanDao.save(noticeBasicBean);
                /*NoticeUserRelBean noticeUserRelBean1 = new NoticeUserRelBean();
                noticeUserRelBean1.setNotice_id(noticeBasicBean.getId());
                noticeUserRelBean1.setUser_id(user_id);
                noticeUserRelBean1.setReply_type(1);
                noticeUserRelBeanDao.save(noticeUserRelBean1);           //保存日程和人员关系*/
                //保存直接传过来的id串
                List tokenAllList = new ArrayList();
                if(ids!=null && !"".equals(ids)){
                    Gson gson = new Gson();
                    OrgContainerModel orgJson = gson.fromJson(ids,OrgContainerModel.class);
                    List<OrgnizationListModel> orgList = orgJson.getModels();
                    for(int i=0;i<orgList.size();i++){
                        OrgnizationListModel om = orgList.get(i);
                        if(om.getFlag()==2){        //用户
                            NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                            noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                            noticeUserRelBean.setUser_id(Long.parseLong(om.getJgdm()));
                            noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                            List tokenList = userBeanService.getUserTokenListByIds(Long.parseLong(om.getJgdm()));
                            tokenAllList.addAll(tokenList);
                        }else{                      //组织机构
                            List<Long> useridList = userBeanService.getUseridListByGgdm(om.getJgdm());
                            if(useridList!=null && !useridList.isEmpty()){
                                for(int j=0;j<useridList.size();j++){
                                    NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                                    noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                                    noticeUserRelBean.setUser_id(useridList.get(j));
                                    noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                                }
                            }
                            List tokenJgdmList = userBeanService.getUserTokenListJgdm(om.getJgdm());
                            tokenAllList.addAll(tokenJgdmList);
                        }
                    }
                }
                //发送推送
                if(tokenAllList!=null&&!tokenAllList.isEmpty()){
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("function", 10000);
                    pushjsoncontent.put("message", "新日程提醒:您有新的日程-"+title+"!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenAllList,1,"","",pushjsoncontent);
                    Thread thread = new Thread(smxt);
                    thread.start();
                }
                result =  1;
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
    @Path("/cancelNotice")
    public Map cancelNotice(@FormParam("notice_id")Long notice_id,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(notice_id!=null && user_id>0){
                NoticeBasicBean noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                noticeBasicBean.setIs_cancle(1);
                noticeBasicBeanDao.save(noticeBasicBean);
                //发送推送
                List tokenAllList = userBeanService.getUserTokenListByNoticeId(notice_id, 1, user_id);
                if(tokenAllList!=null&&!tokenAllList.isEmpty()){
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("function", 10001);
                    pushjsoncontent.put("message", "日程取消提醒:您的日程-"+noticeBasicBean.getTitle()+"-已取消!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenAllList,1,"","",pushjsoncontent);
                    Thread thread = new Thread(smxt);
                    thread.start();
                }
                result = 1;
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result", result);
        }
        return map;
    }

    @Override
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("getNoticeList/{page}/{pageSize}")
    //@Consumes("application/json;charset=utf-8")
    public Map getNoticeList(@PathParam("page")Integer page, @PathParam("pageSize")Integer page_size,
                             @Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        List<NoticeListModel> list = new ArrayList<NoticeListModel>();
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(user_id>0){
                int start = 0;
                if(page==null){
                    start=0;
                }else{
                    start = (page-1)*page_size;
                }
                if(page_size==null){
                    page_size = 15;
                }
                list = noticeBasicBeanDao.findByNativeSql("select * from \n" +
                        "(select t1.* from (select cnb.id,cnb.title,cnb.content,cnb.notice_time,cnb.user_id,cnb.create_time,su.name,-1 as reply_type\n" +
                        "from coop_notice_basic cnb left join sys_user su on su.id=cnb.user_id\n" +
                        "where cnb.user_id="+user_id+" and cnb.notice_time >= substring(now(),1,16) and cnb.is_cancle=0\n" +
                        "union\n" +
                        "select cnb1.id,cnb1.title,cnb1.content,cnb1.notice_time,cnb1.user_id,cnb1.create_time,su1.name,cnur1.reply_type\n" +
                        "from coop_notice_basic cnb1 left join sys_user su1 on su1.id=cnb1.user_id\n" +
                        "left join coop_notice_user_rel cnur1 on cnur1.notice_id=cnb1.id\n" +
                        "where cnur1.user_id="+user_id+" and cnb1.notice_time>= substring(now(),1,16) and cnb1.is_cancle=0\n" +
                        "order by notice_time) t1\n" +
                        "union\n" +
                        "select t2.* from (select cnb2.id,cnb2.title,cnb2.content,cnb2.notice_time,cnb2.user_id,cnb2.create_time,su2.name,-1 as reply_type\n" +
                        "from coop_notice_basic cnb2 left join sys_user su2 on su2.id=cnb2.user_id\n" +
                        "where cnb2.user_id="+user_id+" and cnb2.notice_time <substring(now(),1,16) and cnb2.is_cancle=0\n" +
                        "union\n" +
                        "select cnb3.id,cnb3.title,cnb3.content,cnb3.notice_time,cnb3.user_id,cnb3.create_time,su3.name,cnur3.reply_type\n" +
                        "from coop_notice_basic cnb3 left join sys_user su3 on su3.id=cnb3.user_id\n" +
                        "left join coop_notice_user_rel cnur3 on cnur3.notice_id=cnb3.id\n" +
                        "where cnur3.user_id="+user_id+" and cnb3.notice_time<substring(now(),1,16) and cnb3.is_cancle=0 order by notice_time desc) t2" +
                        ") t limit "+start+","+page_size, NoticeListModel.class );
                result = 1;
                if(list!=null && list.size()>0){
                    map.put("response",list);
                }
            }else {
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch (Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result", result);
        }
        return map;
    }

    @GET
    @Path("/getNoticeDetail/{notice_id}")
    @Produces("application/json;charset=utf-8")
    @Override
    public Map getNoticeDetail(@PathParam("notice_id")Long notice_id,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            List<NoticeDetailModel> noticeDetail = new ArrayList<NoticeDetailModel>();
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(notice_id!=null && user_id>0){
                //noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                noticeDetail = noticeBasicBeanDao.findByNativeSql("select cnb.id,cnb.title,cnb.content,cnb.notice_time," +
                        "cnb.user_id,cnb.create_time,su.name,-1 as reply_type,\n" +
                        "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id) as total_num,\n" +
                        "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id and cnur.reply_type=1) as join_num\n" +
                        "from coop_notice_basic cnb left join sys_user su on su.id=cnb.user_id " +
                        "where cnb.user_id="+user_id+" and cnb.id="+notice_id,NoticeDetailModel.class);
                if(noticeDetail==null || noticeDetail.size()==0){
                    noticeDetail = noticeBasicBeanDao.findByNativeSql("select cnb.id,cnb.title,cnb.content,cnb.notice_time," +
                            "cnb.user_id,cnb.create_time,su.name,cnur.reply_type as reply_type,\n" +
                            "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id) as total_num,\n" +
                            "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id and cnur.reply_type=1) as join_num\n" +
                            "from coop_notice_user_rel cnur left join coop_notice_basic cnb on cnb.id=cnur.notice_id\n" +
                            " left join sys_user su on su.id=cnb.user_id where cnur.user_id="+user_id+" and cnb.id="+notice_id,NoticeDetailModel.class);
                }
                result = 1;
                if(noticeDetail!=null&&noticeDetail.size()>0){
                    map.put("response",noticeDetail.get(0));
                }
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result", result);
        }
        return map;
    }

    @POST
    @Path("/replyNotice")
    //@Consumes("application/json;charset=utf-8")
    @Produces("application/json;charset=utf-8")
    @Override
    public Map replyNotice(@FormParam("notice_id")Long notice_id, @FormParam("reply_content")String reply_content,
                                  @FormParam("reply_type")Integer reply_type,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(notice_id!=null && reply_type!=null && user_id>0){
                noticeUserRelBeanDao.update("update NoticeUserRelBean t set t.reply_type="+reply_type+
                        " where t.notice_id="+notice_id+" and t.user_id="+user_id);
                result = 1;
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch (Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result", result);
        }
        return map;
    }

    @GET
    @Path("/getNoticeReplyList/{notice_id}/{page}/{page_size}")
    @Produces("application/json;charset=utf-8")
    @Override
    public Map getNoticeReplyList(@PathParam("notice_id")Long notice_id, @PathParam("page")Integer page,
                                  @PathParam("page_size")Integer page_size,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        List<NoticeUserRelBean> list = new ArrayList<NoticeUserRelBean>();
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(notice_id!=null && user_id>0){
                int start = 0;
                if(page==null){
                    page=0;
                }else{
                    page = page-1;
                }
                if(page_size==null){
                    page_size = 15;
                }
                start = page*page_size;
                list = noticeUserRelBeanDao.findByNativeSql("select c.id,c.user_id,c.reply_type,c.reply_type,s.name " +
                        "from coop_notice_user_rel c left join sys_user s on s.id=c.user_id " +
                        "where c.notice_id="+notice_id+" limit "+start+","+page_size , UserNoticeResultModel.class);
                result = 1;
                if(list!=null && list.size()>0){
                    map.put("response",list);
                }
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch (Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result",result);
        }
        return map;
    }

    @Override
    @POST
    @Path("/urgeNotreplyUser")
    @Produces("application/json;charset=utf-8")
    public Map urgeNotreplyUser(@FormParam("notice_id")Long notice_id,@Context HttpServletRequest request) {
        Map map = new HashMap();
        int result = -1;
        try{
            Map cookieMap = CookieUtil.getCookieByName(request);
            long user_id = (long) cookieMap.get("userId");
            if(notice_id!=null && user_id>0){
                //发送推送
                NoticeBasicBean noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                List tokenList = userBeanService.getUserTokenListByNoticeId(notice_id,0,user_id);
                if(tokenList!=null&&!tokenList.isEmpty()) {
                    JSONObject pushjsoncontent = new JSONObject();
                    pushjsoncontent.put("function", 10002);
                    pushjsoncontent.put("message", "日程提醒:您有日程《" + noticeBasicBean.getTitle() + "》需要回复!");
                    pushjsoncontent.put("chat_id", 0);
                    SendMsgByXingeThread smxt = new SendMsgByXingeThread(tokenList, 1, "", "", pushjsoncontent);
                    Thread thread = new Thread(smxt);
                    thread.start();
                }
                result = 1;
            }else{
                map.put("errorCode",ErrorCodeValue.PARAM_ERROR);
            }
        }catch(Exception e){
            map.put("errorCode",ErrorCodeValue.INNER_ERROR);
            e.printStackTrace();
        }finally {
            map.put("result", result);
        }
        return map;
    }


}
