package com.daren.cooperate.api.biz;

import com.daren.core.api.biz.IBizService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @类描述：聊天基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IChatBeanService extends IBizService {
    /**
     * 聊天信息创建
     * @param chat_type
     * @param chat_content
     * @param chat_lat
     * @param chat_lng
     * @param to_userid
     * @param group_id
     * @param voice_time
     * @param request
     * @return
     */
    public Map sendmes(int chat_type, String chat_content,String chat_lat, String chat_lng,String to_userid,
                       String group_id,String voice_time,HttpServletRequest request);

    /**
     * 删除最后一条聊天消息记录
     * @author dlw
     * @param to_userid
     * @param group_id
     * @param request
     * @return
     */
    public Map deleteLastChat(Long to_userid,Long group_id, HttpServletRequest request);

    /**
     * 获取私信列表
     * @param page
     * @param limit
     * @param request
     * @return
     */
    public Map getChatList(Integer page, Integer limit, HttpServletRequest request);

    /**
     * 获取与某人的私信列表
     * @param page
     * @param limit
     * @param to_userid
     * @param request
     * @return
     */
    public Map getChatMsgLsit(Integer page, Integer limit, long to_userid,HttpServletRequest request);

    /**
     * 获取讨论组聊天列表
     * @param page
     * @param limit
     * @param group_id
     * @param request
     * @return
     */
    public Map getGroupChatList(Integer page, Integer limit, long group_id,HttpServletRequest request);

    /**
     * 根据发私信push的chat_id获取聊天内容(to_userid:给我push人的id)：
     * @param chat_id
     * @param request
     * @return
     */
    public Map getMsgDetail(long chat_id, HttpServletRequest request);

    /**
     * 更新语音状态为已读
     * @param chat_id
     * @param request
     * @return
     */
    public Map updatechatvoiceisread(Long chat_id, HttpServletRequest request);

}
