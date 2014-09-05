package com.daren.cooperate.core.biz;

import com.daren.cooperate.api.biz.INoticeBeanService;
import com.daren.cooperate.api.dao.INoticeBasicBeanDao;
import com.daren.cooperate.api.dao.INoticeUserRelBeanDao;
import com.daren.cooperate.api.model.StatusJson;
import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.cooperate.entities.NoticeUserRelBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;

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

    public void setNoticeBasicBeanDao(INoticeBasicBeanDao noticeBasicBeanDao) {
        this.noticeBasicBeanDao = noticeBasicBeanDao;
    }

    public void setNoticeUserRelBeanDao(INoticeUserRelBeanDao noticeUserRelBeanDao) {
        this.noticeUserRelBeanDao = noticeUserRelBeanDao;
    }

    @POST
    @Path("/createNotice")
    @Consumes("application/json;charset=utf-8")
    @Override
    public StatusJson createNotice(String title, String content, String notice_time, String ids) {
        StatusJson statusJson = new StatusJson();
        statusJson.setMessage("创建失败！");
        try{
            NoticeBasicBean noticeBasicBean = new NoticeBasicBean();
            noticeBasicBean.setTitle(title);
            noticeBasicBean.setContent(content);
            noticeBasicBean.setNotice_time(notice_time);
            noticeBasicBean.setCreate_time(DateUtil.convertDateToString(new Date(),DateUtil.longSdf));
            //noticeBasicBean.setJgdm();                //取监管机构
            noticeBasicBean = noticeBasicBeanDao.save(noticeBasicBean);
            String[] idArr;
            if(ids!=null && !"".equals(ids)){
                idArr = ids.split(",");
                NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                for(int i=0;i<idArr.length;i++){
                    noticeUserRelBean.setUser_id(idArr[i]);
                    noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                }
            }
            statusJson.setResult(1);
            statusJson.setMessage("创建成功！");
        }catch(Exception e){
            statusJson.setMessage("服务异常！");
            e.printStackTrace();
        }
        return statusJson;
    }

    @POST
    @Path("/cancelNotice")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map cancelNotice(Long notice_id) {
        Map map = new HashMap();
        int result = -1;
        String message = "取消失败！";
        try{
            if(notice_id!=null){
                NoticeBasicBean noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                noticeBasicBean.setIs_cancle(1);
                noticeBasicBeanDao.save(noticeBasicBean);
                result = 1;
                message = "取消成功";
            }else{
                message = "请选择要取消的日程！";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
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
        String message = "获取日程列表失败！";
        List<NoticeBasicBean> list = new ArrayList<NoticeBasicBean>();
        try{
            list = noticeBasicBeanDao.getAll(NoticeBasicBean.class.getName());
            result = 1;
            if(list!=null && list.size()>0){
                map.put("response",list);
                message = "获取日程列表成功！";
            }else{
                message = "没有日程记录！";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }
        return map;
    }

    @POST
    @Path("/getNoticeDetail")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map getNoticeDetail(Long notice_id) {
        Map map = new HashMap();
        int result = -1;
        String message = "获取日程详情失败！";
        try{
            NoticeBasicBean noticeBasicBean = new NoticeBasicBean();
            if(notice_id!=null){
                noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                result = 1;
                if(noticeBasicBean!=null){
                    message = "获取日程详情成功！";
                    map.put("response",noticeBasicBean);
                }else{
                    message = "没有日程详情信息！";
                }
            }else{
                message = "请输入正确的参数！";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }
        return map;
    }
    @POST
    @Path("/replyNotice")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map replyNotice(Long notice_id, String reply_content,Integer reply_type) {
        Map map = new HashMap();
        int result = -1;
        String message = "日程回复失败！";
        try{
            NoticeUserRelBean nurb = new NoticeUserRelBean();
            if(notice_id!=null){
                nurb.setNotice_id(notice_id);
                //nurb.setUser_id();
                nurb.setReply_time(DateUtil.convertDateToString(new Date(),DateUtil.longSdf));
                nurb.setReply_type(reply_type);
                nurb.setReply_content(reply_content);
                noticeUserRelBeanDao.save(nurb);
                result = 1;
                message = "日程回复成功！";
            }else{
                message = "参数有误！";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }
        return map;
    }

    @POST
    @Path("/getnoticereply")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map getNoticeReplyList(Long notice_id, Integer page, Integer page_size) {
        Map map = new HashMap();
        int result = -1;
        String message = "获取日程回复列表失败！";
        List<NoticeUserRelBean> list = new ArrayList<NoticeUserRelBean>();
        try{
            list = noticeUserRelBeanDao.findbyPage("select t from NoticeUserRelBean t where t.notice_id="+notice_id,page,page_size);
            result = 1;
            if(list!=null && list.size()>0){
                map.put("response",list);
                message = "获取日程回复列表成功！";
            }else{
                message = "没有日程回复记录！";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }
        return map;
    }

}
