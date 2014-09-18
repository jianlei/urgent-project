package com.daren.cooperate.persist.openjpa;

import com.daren.cooperate.api.dao.IChatIndexBeanDao;
import com.daren.cooperate.entities.ChatIndexBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/12 14:24
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChatIndexBeanDaoOpenjpa extends GenericOpenJpaDao<ChatIndexBean, Long> implements IChatIndexBeanDao {
}
