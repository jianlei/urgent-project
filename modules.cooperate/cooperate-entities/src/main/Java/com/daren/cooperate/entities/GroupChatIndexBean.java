package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/13 11:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_group_chat_index")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class GroupChatIndexBean extends PersistentEntity {

    private long from_userid;
    private long group_id;
    private long user_id;
    private String neartime;
    private String chat_content="";
    private int chat_count;
    private int hasnews;//0无新消息 1 有新消息
    private String unsearch_userid="";//判断删除最近一次聊天记录用

    public GroupChatIndexBean(){

    }

    public GroupChatIndexBean(long from_userid, String neartime) {
        super();
        this.from_userid = from_userid;
        this.neartime = neartime;
    }
    public GroupChatIndexBean(long from_userid,long group_id, String neartime) {
        super();
        this.from_userid = from_userid;
        this.group_id = group_id;
        this.neartime = neartime;
    }

    public GroupChatIndexBean(long from_userid, String neartime,String chat_content,
                         int chat_count) {
        super();
        this.from_userid = from_userid;
        this.neartime = neartime;
        this.chat_content = chat_content;
        this.chat_count = chat_count;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getFrom_userid() {
        return from_userid;
    }

    public void setFrom_userid(long from_userid) {
        this.from_userid = from_userid;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getNeartime() {
        return neartime;
    }

    public void setNeartime(String neartime) {
        this.neartime = neartime;
    }

    public String getChat_content() {
        return chat_content;
    }

    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public int getChat_count() {
        return chat_count;
    }

    public void setChat_count(int chat_count) {
        this.chat_count = chat_count;
    }

    public int getHasnews() {
        return hasnews;
    }

    public void setHasnews(int hasnews) {
        this.hasnews = hasnews;
    }

    public String getUnsearch_userid() {
        return unsearch_userid;
    }

    public void setUnsearch_userid(String unsearch_userid) {
        this.unsearch_userid = unsearch_userid;
    }
}
