package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：通知-用户关联表
 * @创建人：xukexin
 * @创建时间：2014/9/4 9:43
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_notice_user_rel")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class NoticeUserRelBean extends PersistentEntity {

    private long notice_id;             //通知id
    private String user_id;             //用户id
    private String reply_type;          //回复：0:待回复；1-确认；2-拒绝
    private String reply_time;          //回复时间；
    private String reply_content;       //回复内容；

    public NoticeUserRelBean(){

    }

    public long getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(long notice_id) {
        this.notice_id = notice_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReply_type() {
        return reply_type;
    }

    public void setReply_type(String reply_type) {
        this.reply_type = reply_type;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }
}
