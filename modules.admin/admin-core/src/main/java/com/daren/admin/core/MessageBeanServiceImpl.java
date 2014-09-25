package com.daren.admin.core;

import com.daren.admin.api.biz.IMessageBeanService;
import com.daren.admin.api.dao.IMessageBeanDao;
import com.daren.admin.entities.MessageBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * @类描述：系统消息服务实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class MessageBeanServiceImpl extends GenericBizServiceImpl implements IMessageBeanService {
    private IMessageBeanDao messageBeanDao;

    public void setMessageBeanDao(IMessageBeanDao messageBeanDao) {
        this.messageBeanDao = messageBeanDao;
        super.init(messageBeanDao, MessageBean.class.getName());
    }

    @Override
    public List<MessageBean> query(MessageBean bean) {
        return null;
    }
}
