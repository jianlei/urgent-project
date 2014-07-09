package com.daren.middleware.couchdb.impl;

import com.daren.middleware.couchdb.api.IMessageService;
import com.daren.middleware.couchdb.model.Message;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

/**
 * @类描述：implement class
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午11:07
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MessageServiceImpl implements IMessageService {
    private static CouchDbClient dbClient;

    public void init() {
//        dbClient = new CouchDbClient("sunlf", true, "https", "127.0.0.1", 443, "", "");
        dbClient = new CouchDbClient("carinfo", true, "http", "127.0.0.1", 5984, "admin", "secret");


    }

    @Override
    public Message addMeg(Message message) {
//        dbClient = new CouchDbClient("sunlf", true, "http", "127.0.0.1",5984, "admin", "secret");
        Response response = dbClient.save(message);
        Message foo = dbClient.find(Message.class, response.getId());

//        dbClient.save(message);
        return foo;
    }
}
