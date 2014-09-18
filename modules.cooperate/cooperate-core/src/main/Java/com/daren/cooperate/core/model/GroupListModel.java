package com.daren.cooperate.core.model;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/10 16:43
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class GroupListModel {

    private long id;
    private String group_name="";              //群组名称
    private String group_logo="";              //群组logo
    private int group_num = 1;                 //人数
    private long create_userid;

    public GroupListModel(){

    }

    public long getCreate_userid() {
        return create_userid;
    }

    public void setCreate_userid(long create_userid) {
        this.create_userid = create_userid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_logo() {
        return group_logo;
    }

    public void setGroup_logo(String group_logo) {
        this.group_logo = group_logo;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }
}
