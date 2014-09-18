package com.daren.enterprise.core.model;

import java.io.Serializable;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/11 19:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrgnizationListModel implements Serializable {

    private String sjjgdm="-1";
    private String jgdm="";
    private String name="";
    private long user_id;
    private int flag=1;       //对象类型，1：组织机构，2：用户
    private String user_logo="";

    public OrgnizationListModel(){

    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getSjjgdm() {
        return sjjgdm;
    }

    public void setSjjgdm(String sjjgdm) {
        this.sjjgdm = sjjgdm;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUser_logo() {
        return user_logo;
    }

    public void setUser_logo(String user_logo) {
        this.user_logo = user_logo;
    }
}
