package com.daren.version;

import org.lightcouch.Document;

import java.util.Date;

/**
 * @类描述：版本管理抽象类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:08
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AbstractVersion extends Document implements Versionable {
    private Date deployDate;
    private String checkPerson;
    private State state;
    private String number;
    private String summary;
    private String deployPerson;

    public Date getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Date deployDate) {
        this.deployDate = deployDate;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDeployPerson() {
        return deployPerson;
    }

    public void setDeployPerson(String deployPerson) {
        this.deployPerson = deployPerson;
    }
}
