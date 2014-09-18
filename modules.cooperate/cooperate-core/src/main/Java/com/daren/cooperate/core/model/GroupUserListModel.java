package com.daren.cooperate.core.model;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/16 14:28
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class GroupUserListModel  {

    private long user_id;
    private String name;
    private String head_spicurl;

    public GroupUserListModel(){

    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_spicurl() {
        return head_spicurl;
    }

    public void setHead_spicurl(String head_spicurl) {
        this.head_spicurl = head_spicurl;
    }
}
