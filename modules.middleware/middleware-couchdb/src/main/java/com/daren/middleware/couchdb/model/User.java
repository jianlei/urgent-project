package com.daren.middleware.couchdb.model;

import org.lightcouch.Document;

/**
 * @类描述：usre
 * @创建人：sunlf
 * @创建时间：2014-03-30 上午12:58
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class User extends Document {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
