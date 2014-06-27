package ${package}.api.persistence;

import java.util.Date;

/**
 * Created by Administrator on 14-4-5.
 */
public interface IVersionable {

    String getNumber();

    void setNumber(String number);

    String getCheckPerson();

    void setCheckPerson(String checkPerson);

    String getSummary();

    void setSummary(String summary);

    String getState();

    void setState(String state);

    Date getDate();

    void setDate(Date date);
}
