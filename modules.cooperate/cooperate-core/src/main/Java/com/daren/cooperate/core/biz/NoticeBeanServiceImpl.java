package com.daren.cooperate.core.biz;

import com.daren.cooperate.api.biz.INoticeBeanService;
import com.daren.cooperate.api.dao.INoticeBasicBeanDao;
import com.daren.cooperate.api.dao.INoticeUserRelBeanDao;
import com.daren.cooperate.api.model.ResultListJson;
import com.daren.cooperate.api.model.ResultSingelJson;
import com.daren.cooperate.api.model.StatusJson;
import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.cooperate.entities.NoticeUserRelBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    //@Consumes("application/json;charset=utf-8")
    @Path("/createNotice/{title}/{content}/{notice_time}/{ids}")
    public StatusJson createNotice(@PathParam("title")String title, @PathParam("content")String content,
                                   @PathParam("notice_time")String notice_time, @PathParam("ids")String ids) {
        StatusJson statusJson = new StatusJson();
        int result = -1;
        String message = "创建失败！";
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
                for(int i=0;i<idArr.length;i++){
                    NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                    noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                    noticeUserRelBean.setUser_id(idArr[i]);
                    noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                }
            }
            result =  1;
            message ="创建成功！";
        }catch(Exception e){
            message = "服务异常！";
            e.printStackTrace();
        }finally {
            statusJson.setResult(result);
            statusJson.setMessage(message);
        }
        return statusJson;
    }

    @Override
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/cancelNotice/{notice_id}/")
    public StatusJson cancelNotice(@PathParam("notice_id")Long notice_id) {
        StatusJson statusJson = new StatusJson();
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
            statusJson.setResult(result);
            statusJson.setMessage(message);
        }
        return statusJson;
    }

    @Override
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("getNoticeList/{page}/{pageSize}")
    //@Consumes("application/json;charset=utf-8")
    public ResultListJson getNoticeList(@PathParam("page")Integer page, @PathParam("pageSize")Integer page_size) {
        ResultListJson rlj = new ResultListJson();
        int result = -1;
        String message = "获取日程列表失败！";
        List<NoticeBasicBean> list = new ArrayList<NoticeBasicBean>();
        try{
            if(page==null){
                page=0;
            }else{
                page = page-1;
            }
            if(page_size==null){
                page_size = 15;
            }
            list = noticeBasicBeanDao.findbyPage("select t from NoticeBasicBean t ", page, page_size);
            result = 1;
            if(list!=null && list.size()>0){
                rlj.setList(list);
                message = "获取日程列表成功！";
            }else{
                message = "没有日程记录！";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rlj.getStatusJson().setMessage(message);
            rlj.getStatusJson().setResult(result);
        }
        return rlj;
    }

    @GET
    @Path("/getNoticeDetail/{notice_id}")
    @Produces("application/json;charset=utf-8")
    @Override
    public ResultSingelJson getNoticeDetail(@PathParam("notice_id")Long notice_id) {
        ResultSingelJson resultSingelJson = new ResultSingelJson();
        int result = -1;
        String message = "获取日程详情失败！";
        try{
            NoticeBasicBean noticeBasicBean = new NoticeBasicBean();
            if(notice_id!=null){
                noticeBasicBean = noticeBasicBeanDao.get(NoticeBasicBean.class.getName(),notice_id);
                result = 1;
                if(noticeBasicBean!=null){
                    message = "获取日程详情成功！";
                    resultSingelJson.setEntity(noticeBasicBean);
                }else{
                    message = "没有日程详情信息！";
                }
            }else{
                message = "请输入正确的参数！";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            resultSingelJson.getStatusJson().setResult(result);
            resultSingelJson.getStatusJson().setMessage(message);
        }
        return resultSingelJson;
    }

    @POST
    @Path("/replyNotice/{notice_id}/{reply_content}/{reply_type}")
    //@Consumes("application/json;charset=utf-8")
    @Produces("application/json;charset=utf-8")
    @Override
    public StatusJson replyNotice(@PathParam("notice_id")Long notice_id, @PathParam("reply_content")String reply_content,
                                  @PathParam("reply_type")Integer reply_type) {
        StatusJson statusJson = new StatusJson();
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
            statusJson.setResult(result);
            statusJson.setMessage(message);
        }
        return statusJson;
    }

    @GET
    @Path("/getNoticeReplyList/{notice_id}/{page}/{page_size}")
    @Produces("application/json;charset=utf-8")
    @Override
    public ResultListJson getNoticeReplyList(@PathParam("notice_id")Long notice_id, @PathParam("page")Integer page, @PathParam("page_size")Integer page_size) {
        ResultListJson rlj = new ResultListJson();
        int result = -1;
        String message = "获取日程回复列表失败！";
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
                    rlj.setList(list);
                    message = "获取日程回复列表成功！";
                }else{
                    message = "没有日程回复记录！";
                }
            }else{
                message = "参数错误！";
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rlj.getStatusJson().setMessage(message);
            rlj.getStatusJson().setResult(result);
        }
        return rlj;
    }

}
