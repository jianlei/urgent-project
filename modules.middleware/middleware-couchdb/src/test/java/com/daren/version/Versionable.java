package com.daren.version;

import java.util.Date;

/**
 * @类描述：版本管理接口
 * @创建人：sunlf
 * @创建时间：上午10:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface Versionable {
    public Date getDeployDate();

    public void setDeployDate(Date deployDate);

    public String getCheckPerson();

    public void setCheckPerson(String checkPerson);

    public State getState();

    public void setState(State state);

    public String getNumber();

    public void setNumber(String number);

    public String getSummary();

    public void setSummary(String summary);

    public String getDeployPerson();

    public void setDeployPerson(String deployPerson);
}
