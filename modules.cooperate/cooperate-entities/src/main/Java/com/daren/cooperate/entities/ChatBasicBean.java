package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：聊天基本表
 * @创建人：xukexin
 * @创建时间：2014/9/4 8:56
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_chat_basic")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ChatBasicBean extends PersistentEntity {

    private long group_id=0;               //0-单一唠嗑；>1就是与该group_id的聊天
    private int chat_type=0;              //聊天方式 0-文字；1-语音；2-图片；3-视频；4-文件；5-位置
    private int is_read=0;                //0-未读；1-已读
    private String chat_content="";        //聊天内容
    private String voice_time="";          //语音时间长度
    private String chat_attach="";         //附件
    private String picture_url="";         //图片地址
    private String chat_time="";           //聊天时间
    private long from_userid=0;         //说话人id
    private long to_userid=0;           //接收人id
    private double chat_lat=0;            //聊天纬度
    private double chat_lng=0.0;            //聊天经度

    public ChatBasicBean(){

    }

    public ChatBasicBean(int chat_type, int is_read, String chat_content,
                String chat_attach, String picture_url, long to_userid,
                long from_userid, double chat_lat, double chat_lng,String voice_time) {
        super();
        this.chat_type = chat_type;
        this.is_read = is_read;
        this.chat_content = chat_content;
        this.chat_attach = chat_attach;
        this.picture_url = picture_url;
        this.to_userid = to_userid;
        this.from_userid = from_userid;
        this.chat_lat = chat_lat;
        this.chat_lng = chat_lng;
        this.voice_time = voice_time;
    }
    public ChatBasicBean(long group_id,int chat_type, int is_read, String chat_content,
                String chat_attach, String picture_url,long from_userid, double chat_lat, double chat_lng,String voice_time) {
        super();
        this.group_id = group_id;
        this.chat_type = chat_type;
        this.is_read = is_read;
        this.chat_content = chat_content;
        this.chat_attach = chat_attach;
        this.picture_url = picture_url;
        this.from_userid = from_userid;
        this.chat_lat = chat_lat;
        this.chat_lng = chat_lng;
        this.voice_time = voice_time;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getChat_content() {
        return chat_content;
    }

    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public String getVoice_time() {
        return voice_time;
    }

    public void setVoice_time(String voice_time) {
        this.voice_time = voice_time;
    }

    public String getChat_attach() {
        return chat_attach;
    }

    public void setChat_attach(String chat_attach) {
        this.chat_attach = chat_attach;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
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

    public double getChat_lat() {
        return chat_lat;
    }

    public void setChat_lat(double chat_lat) {
        this.chat_lat = chat_lat;
    }

    public double getChat_lng() {
        return chat_lng;
    }

    public void setChat_lng(double chat_lng) {
        this.chat_lng = chat_lng;
    }
}
