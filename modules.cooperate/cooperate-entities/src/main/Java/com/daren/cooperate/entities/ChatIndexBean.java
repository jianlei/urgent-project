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
 * @创建时间：2014/9/12 13:57
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_chat_index")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ChatIndexBean extends PersistentEntity {

    private long from_userid;
    private long to_userid;
    private long group_id;
    private String neartime;
    private String chat_content="";
    private String unsearch_userid="";//判断删除最近一次聊天记录用
    private int chat_count;
    private int hasnews;//0无新消息 1 有新消息

    public ChatIndexBean(){

    }

    public long getFrom_userid() {
        return from_userid;
    }

    public void setFrom_userid(long from_userid) {
        this.from_userid = from_userid;
    }

    public long getTo_userid() {
        return to_userid;
    }

    public void setTo_userid(long to_userid) {
        this.to_userid = to_userid;
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

    public String getUnsearch_userid() {
        return unsearch_userid;
    }

    public void setUnsearch_userid(String unsearch_userid) {
        this.unsearch_userid = unsearch_userid;
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
}
