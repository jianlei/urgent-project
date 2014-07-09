package com.daren.middleware.couchdb.api;

import com.daren.middleware.couchdb.model.Message;

/**
 * @类描述：消息服务接口类
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午11:04
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IMessageService {
    Message addMeg(Message message);

}
