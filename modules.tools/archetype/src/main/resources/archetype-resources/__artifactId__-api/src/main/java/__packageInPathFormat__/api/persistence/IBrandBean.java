package ${package}.api.persistence;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-4.
 */
public interface IBrandBean extends Serializable {
    Date getCreateDate();

    void setCreateDate(Date createDate);

    String getName();

    void setName(String name);

    String getDescribe();

    void setDescribe(String describe);
}
