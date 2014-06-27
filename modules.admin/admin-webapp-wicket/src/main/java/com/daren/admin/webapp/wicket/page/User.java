package com.daren.admin.webapp.wicket.page;

import org.apache.wicket.util.io.IClusterable;


/**
 * Created by Administrator on 14-3-24.
 */
public class User implements IClusterable {
    String userName;
    String passWord;
    String email;
    String date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
