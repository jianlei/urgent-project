package com.daren.cooperate.core.model;

import java.io.Serializable;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/13 15:44
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UserNoticeResultModel implements Serializable {

    private long id;
    private long user_id;           //用户id
    private int reply_type;         //回复：0:待回复；1-确认；2-拒绝
    private String reply_time;      //回复时间；
    private String name;            //姓名

    public UserNoticeResultModel(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getReply_type() {
        return reply_type;
    }

    public void setReply_type(int reply_type) {
        this.reply_type = reply_type;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
