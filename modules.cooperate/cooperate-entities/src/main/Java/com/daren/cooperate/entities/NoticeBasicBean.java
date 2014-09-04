package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：通知基本表
 * @创建人：xukexin
 * @创建时间：2014/9/4 9:37
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_notice_basic")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class NoticeBasicBean extends PersistentEntity {

    private String jgdm;            //机构代码
    private String user_id;         //发布人id
    @NotNull(message = "'日程标题'是必填项")
    private String title;           //标题
    @NotNull(message = "'日程内容'是必填项")
    private String content;         //内容
    @NotNull(message = "'日程时间'是必填项")
    private String notice_time;     //日程时间
    private String create_time;     //发布时间
    private String is_cancle;       //是否取消：0-未取消；1-已取消

    public NoticeBasicBean(){

    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIs_cancle() {
        return is_cancle;
    }

    public void setIs_cancle(String is_cancle) {
        this.is_cancle = is_cancle;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }
}
