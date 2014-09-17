package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：群组基本表bean
 * @创建人：xukexin
 * @创建时间：2014/9/4 7:55
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_group_Basic")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class GroupBasicBean extends PersistentEntity {

    private long jgdm;                    //机构代码
    private String group_name="";              //群组名称
    private String group_logo="";              //群组logo
    private long manager_user;             //创建人id
    private String create_time="";             //创建时间
    private int is_generate;                //群组是否为自动生成，0-否；1-是
    private int group_num;                  //群组人数

    public GroupBasicBean(){

    }

    public long getJgdm() {
        return jgdm;
    }

    public void setJgdm(long jgdm) {
        this.jgdm = jgdm;
    }

    public long getManager_user() {
        return manager_user;
    }

    public void setManager_user(long manager_user) {
        this.manager_user = manager_user;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_generate() {
        return is_generate;
    }

    public void setIs_generate(int is_generate) {
        this.is_generate = is_generate;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }
}
