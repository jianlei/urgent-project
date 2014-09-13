package com.daren.cooperate.core.biz;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.cooperate.api.biz.INoticeBeanService;
import com.daren.cooperate.api.dao.INoticeBasicBeanDao;
import com.daren.cooperate.api.dao.INoticeUserRelBeanDao;
import com.daren.cooperate.core.model.ErrorCodeValue;
import com.daren.cooperate.core.model.NoticeDetailModel;
import com.daren.cooperate.core.model.NoticeListModel;
import com.daren.cooperate.core.model.OrgContainerModel;
import com.daren.cooperate.core.util.SendMsgByXingeThread;
import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.cooperate.entities.NoticeUserRelBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;
import com.daren.enterprise.core.model.OrgnizationListModel;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.ws.rs.*;
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
                                   @FormParam("notice_time")String notice_time, @FormParam("ids")String ids ) {
        Map map = new HashMap();
        int result = -1;
        try{
            NoticeBasicBean noticeBasicBean = new NoticeBasicBean();
            noticeBasicBean.setTitle(title);
            noticeBasicBean.setContent(content);
            noticeBasicBean.setNotice_time(notice_time);
            noticeBasicBean.setCreate_time(DateUtil.convertDateToString(new Date(),DateUtil.longSdf));
            //noticeBasicBean.setJgdm();                //取监管机构
            noticeBasicBean = noticeBasicBeanDao.save(noticeBasicBean);
            //保存直接传过来的id串
            List tokenAllList = new ArrayList();
            tokenAllList.add("81768099bdd7924d426db3aa643b3dc846fdc381");
            if(ids!=null && !"".equals(ids)){
                Gson gson = new Gson();
                OrgContainerModel orgJson = gson.fromJson(ids,OrgContainerModel.class);
                List<OrgnizationListModel> orgList = orgJson.getModels();
                for(int i=0;i<orgList.size();i++){
                    OrgnizationListModel om = orgList.get(i);
                    if(om.getFlag()==2){        //用户
                        NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                        noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                        noticeUserRelBean.setUser_id(om.getUser_id());
                        noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                        List tokenList = userBeanService.getUserTokenListByIds(om.getUser_id());
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
    @Path("/cancelNotice/{notice_id}")
    public Map cancelNotice(@PathParam("notice_id")Long notice_id) {
        Map map = new HashMap();
        int result = -1;
        try{
            if(notice_id!=null){
                NoticeBasicBean noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                noticeBasicBean.setIs_cancle(1);
                noticeBasicBeanDao.save(noticeBasicBean);
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
    public Map getNoticeList(@PathParam("page")Integer page, @PathParam("pageSize")Integer page_size) {
        Map map = new HashMap();
        int result = -1;
        List<NoticeListModel> list = new ArrayList<NoticeListModel>();
        try{
            int start = 0;
            if(page==null){
                start=0;
            }else{
                start = (page-1)*page_size;
            }
            if(page_size==null){
                page_size = 15;
            }
            Long user_id = 1l;
            list = noticeBasicBeanDao.findByNativeSql("select * from \n" +
                    "(\n" +
                    "select t1.* from (select cnb.id,cnb.title,cnb.content,cnb.notice_time,cnb.user_id,cnb.create_time,su.name,cnur.reply_type\n" +
                    "from coop_notice_basic cnb left join sys_user su on su.id=cnb.user_id \n" +
                    "left join coop_notice_user_rel cnur on cnur.notice_id=cnb.id \n" +
                    "where cnur.user_id="+user_id+" and cnb.notice_time >= substring(now(),1,16) and cnb.is_cancle=0 order by cnb.notice_time) t1 \n" +
                    "union\n" +
                    "select t2.* from (select cnb.id,cnb.title,cnb.content,cnb.notice_time,cnb.user_id,cnb.create_time,su.name,cnur.reply_type \n" +
                    "from coop_notice_basic cnb left join sys_user su on su.id=cnb.user_id \n" +
                    "left join coop_notice_user_rel cnur on cnur.notice_id=cnb.id \n" +
                    "where cnur.user_id="+user_id+" and cnb.notice_time<substring(now(),1,16) and cnb.is_cancle=0 order by cnb.notice_time desc) t2 \n" +
                    ") t limit "+start+","+page_size, NoticeListModel.class );
            result = 1;
            if(list!=null && list.size()>0){
                map.put("response",list);
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
    public Map getNoticeDetail(@PathParam("notice_id")Long notice_id) {
        Map map = new HashMap();
        int result = -1;
        try{
            List<NoticeDetailModel> noticeDetail = new ArrayList<NoticeDetailModel>();
            if(notice_id!=null){
                //noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                Long user_id = 1l;
                noticeDetail = noticeBasicBeanDao.findByNativeSql("select cnb.id,cnb.title,cnb.content,cnb.notice_time,cnb.user_id,cnb.create_time,su.name,cnur.reply_type,\n" +
                        "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id) as total_num,\n" +
                        "(select count(cnur.id) from coop_notice_user_rel cnur where cnur.notice_id=cnb.id and cnur.reply_type=1) as join_num\n" +
                        "from coop_notice_basic cnb left join sys_user su on su.id=cnb.user_id " +
                        "left join coop_notice_user_rel cnur on cnur.notice_id=cnb.id \n" +
                        "where cnur.user_id="+user_id+" and cnb.id="+notice_id,NoticeDetailModel.class);
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
                                  @FormParam("reply_type")Integer reply_type) {
        Map map = new HashMap();
        int result = -1;
        try{
            if(notice_id!=null && reply_type!=null){
                Long user_id = 1l;
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
    public Map getNoticeReplyList(@PathParam("notice_id")Long notice_id, @PathParam("page")Integer page, @PathParam("page_size")Integer page_size) {
        Map map = new HashMap();
        int result = -1;
        List<NoticeUserRelBean> list = new ArrayList<NoticeUserRelBean>();
        try{
            if(notice_id!=null){
                if(page==null){
                    page=0;
                }else{
                    page = page-1;
                }
                if(page_size==null){
                    page_size = 15;
                }
                list = noticeUserRelBeanDao.findbyPage("select t from NoticeUserRelBean t where t.notice_id="+notice_id,page,page_size);
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

}
