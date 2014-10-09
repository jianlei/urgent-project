package com.daren.admin.api.dao;

import com.daren.admin.entities.UserRelBean;
import com.daren.core.api.persistence.IGenericDao;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/10/9 11:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IUserRelBeanDao extends IGenericDao<UserRelBean, Long> {

    /**
     * 保存用户关联
     * @param userRelBean
     * @return
     */
    public UserRelBean saveUserRel(UserRelBean userRelBean);

    /**
     * 移除用户关联
     * @param user_id
     */
    public void removeUserRel(Long user_id);

}
