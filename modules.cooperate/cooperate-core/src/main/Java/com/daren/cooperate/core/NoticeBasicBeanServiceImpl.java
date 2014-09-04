package com.daren.cooperate.core;

import com.daren.cooperate.api.biz.INoticeBasicBeanService;
import com.daren.cooperate.api.dao.INoticeBasicBeanDao;
import com.daren.cooperate.api.dao.INoticeUserRelBeanDao;
import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.cooperate.entities.NoticeUserRelBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.core.util.DateUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类描述：日程基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class NoticeBasicBeanServiceImpl extends GenericBizServiceImpl implements INoticeBasicBeanService {

    private INoticeBasicBeanDao noticeBasicBeanDao;
    private INoticeUserRelBeanDao noticeUserRelBeanDao;

    public INoticeBasicBeanDao getNoticeBasicBeanDao() {
        return noticeBasicBeanDao;
    }

    public void setNoticeBasicBeanDao(INoticeBasicBeanDao noticeBasicBeanDao) {
        this.noticeBasicBeanDao = noticeBasicBeanDao;
    }

    public INoticeUserRelBeanDao getNoticeUserRelBeanDao() {
        return noticeUserRelBeanDao;
    }

    public void setNoticeUserRelBeanDao(INoticeUserRelBeanDao noticeUserRelBeanDao) {
        this.noticeUserRelBeanDao = noticeUserRelBeanDao;
    }

    @POST
    @Path("/createnotice")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map createNotice(String title, String content, String notice_time, String ids) {
        Map map = new HashMap();
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
                NoticeUserRelBean noticeUserRelBean = new NoticeUserRelBean();
                noticeUserRelBean.setNotice_id(noticeBasicBean.getId());
                for(int i=0;i<idArr.length;i++){
                    noticeUserRelBean.setUser_id(idArr[i]);
                    noticeUserRelBeanDao.save(noticeUserRelBean);           //保存日程和人员关系
                }
            }
            result = 1;
            message = "创建成功";
        }catch(Exception e){
            message = "服务异常";
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }
        return map;
    }

    @POST
    @Path("/canclenotice")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map cancleNotice(Long notice_id) {
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

    @POST
    @Path("/getnoticelist")
    @Consumes("application/json;charset=utf-8")
    @Override
    public List<NoticeBasicBean> getNoticeList(Integer page, Integer page_size) {
        Map map = new HashMap();
        int result = -1;
        String message = "取消失败！";
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            map.put("result",result);
            map.put("message",message);
        }

        return null;
    }

    @POST
    @Path("/print")
    @Consumes("application/json;charset=utf-8")
    @Override
    public NoticeBasicBean getNoticeDetail(Long notice_id) {
        return null;
    }
    @POST
    @Path("/print")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map replyNotice(Long notice_id, String reply_content) {
        return null;
    }
    @POST
    @Path("/print")
    @Consumes("application/json;charset=utf-8")
    @Override
    public Map getNoticeReplyList(Long notice_id, Integer page, Integer page_size) {
        return null;
    }

}
