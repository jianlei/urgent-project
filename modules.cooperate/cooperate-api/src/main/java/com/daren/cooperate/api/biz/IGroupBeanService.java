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
    public Map replyReq(Integer group_id, Integer res_type,HttpServletRequest request);

    /**
     * 获取新邀请的列表
     * @param page
     * @param page_size
     * @return
     */
    public Map getNewReqList(Integer page, Integer page_size,HttpServletRequest request);

}
