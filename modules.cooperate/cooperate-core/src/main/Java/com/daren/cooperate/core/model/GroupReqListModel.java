package com.daren.cooperate.core.model;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/11 9:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class GroupReqListModel {

    private long id;
    private long group_id;
    private String name;
    private String req_time;
    private String req_msg;
    private String group_name;

    public GroupReqListModel(){

    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReq_msg() {
        return req_msg;
    }

    public void setReq_msg(String req_msg) {
        this.req_msg = req_msg;
    }

    public String getReq_time() {
        return req_time;
    }

    public void setReq_time(String req_time) {
        this.req_time = req_time;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
