package com.daren.core.api.persistence;

import com.daren.core.api.IEntity;

import java.util.Date;

/**
 * 持久化实体接口
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
