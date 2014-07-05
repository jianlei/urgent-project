package com.daren.core.api.cache;

import com.daren.core.api.IService;

/**
 * @类描述：cache interface
 * @创建人：sunlf
 * @创建时间：2014-07-01 下午3:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface ICacheManager extends IService {

    /**
     * 保存对象
     *
     * @param key   key
     * @param value value
     */
    public <T> void save(String key, T value);

    /**
     * 保存对象（带失效时间）
     *
     * @param key
     * @param value
     * @param sec   单位（秒）
     * @param <T>
     */
    public <T> void save(String key, T value, int sec);

    /**
     * 是否存在KEY
     *
     * @param key
     * @return
     */
    public Boolean exists(String key);

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    public String get(String key);

    public byte[] getObj(String key);

    /**
     * 删除对象
     *
     * @param key
     */
    public void del(String key);
}
