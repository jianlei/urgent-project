package com.daren.core.api.persistence;

import com.daren.core.api.IEntity;

import java.util.Date;

/**
 * @类描述：持久化实体根接口
 * @创建人：sunlf
 * @创建时间：2014-7-3 下午1:01:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IPersistentEntity extends IEntity {
    long getId();

    void setId(long id);

    long getVersion();

    void setVersion(long vers);

    String getCreateBy();

    void setCreateBy(String createBy);

    String getUpdateBy();

    void setUpdateBy(String updateBy);

    Date getUpdateDate();

    void setUpdateDate(Date updateDate);

    Date getCreationDate();

    void setCreationDate(Date creationDate);
}
