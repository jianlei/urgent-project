package com.daren.cooperate.api.biz;

import com.daren.cooperate.entities.NoticeBasicBean;
import com.daren.core.api.biz.IBizService;

import java.util.List;
import java.util.Map;

/**
 * @类描述：日程基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 10:27
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface INoticeBasicBeanService extends IBizService {

    /**
     * 创建日程
     * @param title
     * @param content
     * @param notice_time
     * @param ids   用户id串
     * @return
     */
    public Map createNotice(String title, String content, String notice_time, String ids);

    /**
     * 取消日程
     * @param notice_id
     * @return
     */
    public Map cancleNotice(Long notice_id);

    /**
     * 查询日程列表
     * @param page
     * @param page_size
     * @return
     */
    public List<NoticeBasicBean> getNoticeList(Integer page,Integer page_size);

    /**
     * 获取日程信息详情
     * @param notice_id
     * @return
     */
    public NoticeBasicBean getNoticeDetail(Long notice_id);
    /**
     * 回应日程
     * @param notice_id
     * @return
     */
    public Map replyNotice(Long notice_id, String reply_content);
    /**
     * 获取日程回应列表
     * @param notice_id
     * @param page
     * @param page_size
     * @return
     */
    public Map getNoticeReplyList(Long notice_id,Integer page,Integer page_size);

}
