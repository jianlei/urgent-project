package com.daren.admin.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：用户扩展关联表
 * @创建人：xukexin
 * @创建时间：2014/9/12 9:41
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_user_rel")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class UserRelBean extends PersistentEntity {

    private long user_id;       //用户ID
    private String user_logo;   //用户logo
    private String token;       //手机端上传的token
    private int position;       //职位
    private int order_num;      //排序

    public UserRelBean(){

    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_logo() {
        return user_logo;
    }

    public void setUser_logo(String user_logo) {
        this.user_logo = user_logo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }
}
