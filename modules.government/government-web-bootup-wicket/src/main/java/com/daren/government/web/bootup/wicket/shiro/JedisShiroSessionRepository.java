package com.daren.government.web.bootup.wicket.shiro;


import com.daren.core.util.SerializeUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 22:37
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 22:37
 * 修改备注:  [说明本次修改内容]
 */
public class JedisShiroSessionRepository implements
        ShiroSessionRepository {
    private static Logger logger = Logger.getLogger(JedisShiroSessionRepository.class);
    /**
     * redis session key前缀
     */
    private final String REDIS_SHIRO_SESSION = "shiro-session:";

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error(
                    "session或者session id为空");
            return;
        }
        byte[] key = SerializeUtil
                .serialize(getRedisSessionKey(session.getId()));
        byte[] value = SerializeUtil.serialize(session);
        Jedis jedis = this.getJedis();
        try {
            Long timeOut = session.getTimeout() / 1000;
            jedis.set(key, value);
            jedis.expire(key, Integer.parseInt(timeOut.toString()));
        } catch (JedisException e) {
            logger.error("保存session失败",
                    e);
        } finally {
            this.returnResource(jedis);
        }
    }

    private void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            logger.error("id为空");
            return;
        }
        Jedis jedis = this.getJedis();
        try {
            jedis.del(SerializeUtil.serialize(getRedisSessionKey(id)));
        } catch (JedisException e) {
            logger.error("删除session失败",
                    e);
        } finally {
            this.returnResource(jedis);
        }
    }

    @Override
    public Session getSession(Serializable id) {
        if (id == null) {
            logger.error("id为空");
            return null;
        }
        Session session = null;
        Jedis jedis = this.getJedis();
        try {
            byte[] value = jedis.get(SerializeUtil
                    .serialize(getRedisSessionKey(id)));
            session = SerializeUtil.unserialize(value);
        } catch (JedisException e) {
            logger.error("获取id为" + id
                    + "的session失败", e);
        } catch (Exception e) {
            logger.error("登录异常", e);
        } finally {
            this.returnResource(jedis);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Jedis jedis = this.getJedis();
        Set<Session> sessions = new HashSet<Session>();
        try {
            Set<byte[]> byteKeys = jedis.keys(SerializeUtil
                    .serialize(this.REDIS_SHIRO_SESSION + "*"));
            if (byteKeys != null && byteKeys.size() > 0) {
                for (byte[] bs : byteKeys) {
                    Session s = SerializeUtil.unserialize(jedis.get(bs)
                    );
                    sessions.add(s);
                }
            }
        } catch (JedisException e) {
            logger.error("获取所有session失败", e);
        } finally {
            this.returnResource(jedis);
        }
        return sessions;
    }

    /**
     * 获取redis中的session key
     *
     * @param sessionId
     * @return
     */
    private String getRedisSessionKey(Serializable sessionId) {
        return this.REDIS_SHIRO_SESSION + sessionId;
    }

}