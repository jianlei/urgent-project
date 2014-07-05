package com.daren.core.impl.cache;


import com.daren.core.api.cache.ICacheManager;
import com.daren.core.util.SerializeUtil;
import com.google.gson.Gson;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @类描述：cache implement class
 * @创建人：sunlf
 * @创建时间：2014-07-01 下午3:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DefaultCacheManager implements ICacheManager {

    private ShardedJedisPool pool;
    private Gson gson = new Gson();


    public void setPool(ShardedJedisPool pool) {
        this.pool = pool;
    }

    /**
     * 使用gson存储json对象
     *
     * @param key   key
     * @param value value
     * @param <T>
     */
    @Override
    public <T> void save(String key, T value) {
        // 从池中获取一个Jedis对象
        ShardedJedis jedis = pool.getResource();
        if (value instanceof String) {
            jedis.set(key, (String) value);
        } else {
            jedis.set(key, gson.toJson(value));
        }
        // 释放对象池
        pool.returnResource(jedis);
    }

    @Override
    public <T> void save(String key, T value, int sec) {
        // 从池中获取一个Jedis对象
        ShardedJedis jedis = pool.getResource();
        if (value instanceof String) {
            jedis.setex(key, sec, (String) value);
        } else {
            jedis.setex(key.getBytes(), sec, SerializeUtil.serialize(value));
        }
    }

    @Override
    public String get(String key) {
        // 从池中获取一个Jedis对象
        ShardedJedis jedis = pool.getResource();
        String value = null;
        if (jedis.exists(key)) {
            value = jedis.get(key);
        }
        // 释放对象池
        pool.returnResource(jedis);
        return value;
    }

    @Override
    public Boolean exists(String key) {
        ShardedJedis jedis = pool.getResource();
        boolean flag = jedis.exists(key);
        pool.returnResource(jedis);
        return flag;
    }

    @Override
    public byte[] getObj(String key) {
        // 从池中获取一个Jedis对象
        ShardedJedis jedis = pool.getResource();
        byte[] value = null;
        if (jedis.exists(key)) {
            value = jedis.get(key.getBytes());
        }
        // 释放对象池
        pool.returnResource(jedis);
        return value;
    }

    @Override
    public void del(String key) {
        // 从池中获取一个Jedis对象
        ShardedJedis jedis = pool.getResource();
        if (jedis.exists(key)) {
            jedis.del(key);
        }
    }
}
