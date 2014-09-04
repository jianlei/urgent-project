package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：群组-用户请求-响应实体
 * @创建人：xukexin
 * @创建时间：2014/9/4 9:08
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_group_user_req")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class GroupUserReqBean extends PersistentEntity {

    private long group_id;              //群组id
    private String req_id;              //请求人id
    private String res_id;              //被邀请人id
    private String req_time;            //请求时间
    private String res_time;            //回应时间
    private String res_type;            //响应类型：0-未到；1-通过；2-拒绝
    private String req_msg;             //请求的附加信息

    public GroupUserReqBean(){

    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getReq_time() {
        return req_time;
    }

    public void setReq_time(String req_time) {
        this.req_time = req_time;
    }

    public String getRes_time() {
        return res_time;
    }

    public void setRes_time(String res_time) {
        this.res_time = res_time;
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public String getReq_msg() {
        return req_msg;
    }

    public void setReq_msg(String req_msg) {
        this.req_msg = req_msg;
    }
}
