package com.daren.core.impl.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.core.api.persistence.IGenericDao;
import com.daren.core.api.persistence.IPersistentEntity;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @类描述： 对外服务的抽象接口实现, 封装了基本的对数据库的操作
 * @创建人：sunlf
 * @创建时间：2014-3-27 下午1:01:59
 * @修改人：sunlf
 * @修改时间：2014-3-27 下午1:01:59
 * @修改备注：
 */
public abstract class GenericBizServiceImpl<T extends IGenericDao> implements IBizService {
    private T dao;
    private String entityClassName;
    protected final Logger log = Logger.getLogger(getClass());

    @Override
    public void deleteEntity(long entityId) {
        log.debug("remove entity of " + entityClassName + ";PK is " + entityId);
        dao.remove(entityClassName, entityId);
    }

    @Override
    public void saveEntity(IPersistentEntity entity) {
        dao.save(entity);
    }

    @Override
    public List getAllEntity() {
        return dao.getAll(entityClassName);
    }

    @Override
    public IPersistentEntity getEntity(long entityId) {
        return (IPersistentEntity) dao.get(entityClassName, entityId);
    }

    /**
     * 具体的业务类必须调用该方法
     *
     * @param dao             具体的dao实现类
     * @param entityClassName 实体类的名字
     */
    public void init(T dao, String entityClassName) {
        this.dao = dao;
        this.entityClassName = entityClassName;
    }
}
