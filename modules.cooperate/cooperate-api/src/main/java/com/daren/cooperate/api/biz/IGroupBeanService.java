package com.daren.cooperate.api.biz;

import com.daren.core.api.biz.IBizService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @类描述：群组基本信息
 * @创建人：xukexin
 * @创建时间：2014/9/4 10:26
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IGroupBeanService extends IBizService {
    /**
     * 创建群组
     * @param ids
     * @return
     */
    public Map createGroup(String group_name,String group_logo,String ids,String req_msg,HttpServletRequest request);

    /**
     * 修改群组名称
     * @param group_id
     * @param group_name
     * @return
     */
    public Map updateGroupName(Long group_id, String group_name, HttpServletRequest request);

    /**
     * 向群组中加人
     * @param group_id
     * @param ids
     * @param request
     * @return
     */
    public Map addUserToGroup(Long group_id, String ids, HttpServletRequest request);

    /**
     * 获取群组列表
     * @param page
     * @param page_size
     * @return
     */
    public Map getGroupList(Integer page, Integer page_size,HttpServletRequest request);

    /**
     * 被邀请人回应邀请
     * @param group_id
     * @param res_type
     * @return
     */
    public Map replyReq(Long group_id, Integer res_type,String name, HttpServletRequest request);

    /**
     * 获取新邀请的列表
     * @param page
     * @param page_size
     * @return
     */
    public Map getNewReqList(Integer page, Integer page_size,HttpServletRequest request);

    /**
     * 解散群组
     * @param group_id
     * @param request
     * @return
     */
    public Map disolveGroup(Long group_id, HttpServletRequest request);

    /**
     * 从群组中删除用户
     * @param group_id
     * @param delete_user_id
     * @param request
     * @return
     */
    public Map deleleUserFromGroup(Long group_id, Long delete_user_id, HttpServletRequest request);

    /**
     * 退出群组
     * @param group_id
     * @param request
     * @return
     */
    public Map quitGroup(Long group_id, HttpServletRequest request);

    /**
     * 获取群组详情
     * @return
     */
    public Map getGroupDetail(Long group_id, HttpServletRequest request);

}
