package com.daren.attachment.core.biz;

import com.daren.attachment.api.biz.IAttachmentService;
import com.daren.attachment.api.dao.IAttachmentBeanDao;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * @类描述：审批附件
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AttachmentServiceImpl extends GenericBizServiceImpl implements IAttachmentService {
    IAttachmentBeanDao attachmentBeanDao;

    public void setAttachmentBeanDao(IAttachmentBeanDao attachmentBeanDao) {
        this.attachmentBeanDao = attachmentBeanDao;
        super.init(attachmentBeanDao, AttachmentBean.class.getName());
    }

    @Override
    public List<AttachmentBean> getAttachmentBeanByParentIdAndAppType(long preateId, String appType) {
        return attachmentBeanDao.find("select a from AttachmentBean a where a.parentId=?1 and a.appType=?2",preateId,appType);
    }
}