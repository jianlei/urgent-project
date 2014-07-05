package com.daren.core.api.persistence;

/**
 * @类描述：业务实体接口
 * @创建人：sunlf
 * @创建时间：2014-7-3 下午1:01:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IBizEntity extends IPersistentEntity {
    /**
     * 获得业务主键
     *
     * @return 业务主键
     */
    public String getBizKey();

    /**
     * 设置业务主键
     *
     * @key 业务主键
     */
    public void setBizKey(String key);
}
