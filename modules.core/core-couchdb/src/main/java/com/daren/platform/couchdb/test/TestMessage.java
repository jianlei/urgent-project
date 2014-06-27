package com.daren.platform.couchdb.test;

import com.daren.platform.couchdb.api.IMessageService;

/**
 * @类描述：test
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午11:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class TestMessage {
    private IMessageService messageService;

    public void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

    public void init() {

        /*Message message = new Message();
        message.setName("sunlf");
        message.setCreateDate(new Date());
        message.setMessage("hello every");
        messageService.addMeg(message);*/
    }
}
