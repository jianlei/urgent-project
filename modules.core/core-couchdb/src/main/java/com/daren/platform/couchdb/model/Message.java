package com.daren.platform.couchdb.model;

import org.lightcouch.Document;

import java.util.Date;

/**
 * @类描述：消息测试类
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午11:00
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Message extends Document {
    private String message;
    private String name;
    private Date createDate;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
