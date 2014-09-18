package com.daren.cooperate.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：群组-用户关系表
 * @创建人：xukexin
 * @创建时间：2014/9/4 8:45
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "coop_group_user_rel")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class GroupUserRelBean extends PersistentEntity {

    private long group_id;      //群组id
    private long user_id;       //用户id
    private int hasnew;         //是否有新消息:0-没有；1-有

    public GroupUserRelBean(){

    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getHasnew() {
        return hasnew;
    }

    public void setHasnew(int hasnew) {
        this.hasnew = hasnew;
    }
}
