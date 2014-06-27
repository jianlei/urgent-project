package ${package}.core.persistence;

import ${package}.api.persistence.IVersionable;
import org.lightcouch.Document;

import java.util.Date;

/**
 * @类描述：
 * @创建人：wangkr
 * @创建时间：2014-04-06 上午10:48
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AbstractDocument extends Document implements IVersionable {
    String checkPerson;
    String number;
    String summary;
    String state;
    Date date;

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getCheckPerson() {
        return checkPerson;
    }

    @Override
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}


