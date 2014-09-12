package com.daren.attachment.persist.openjpa;


import com.daren.attachment.api.dao.IAttachmentBeanDao;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * @类描述：审批附件
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AttachmentBeanDaoOpenjpa extends GenericOpenJpaDao<AttachmentBean, Long> implements IAttachmentBeanDao {
}
