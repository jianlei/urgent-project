package com.daren.core.api.persistence;

/**
 * 业务实体接口
 */
public interface IBizEntity extends IPersistentEntity {
    public String getBizKey();

    public void setBizKey(String key);
}
