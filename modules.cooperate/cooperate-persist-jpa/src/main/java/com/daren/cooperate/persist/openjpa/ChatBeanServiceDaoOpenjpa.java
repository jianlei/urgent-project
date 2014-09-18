package com.daren.cooperate.persist.openjpa;

import com.daren.cooperate.api.dao.IGroupChatIndexBeanDao;
import com.daren.cooperate.entities.GroupChatIndexBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/16 18:08
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChatBeanServiceDaoOpenjpa extends GenericOpenJpaDao<GroupChatIndexBean, Long> implements IGroupChatIndexBeanDao {
}
