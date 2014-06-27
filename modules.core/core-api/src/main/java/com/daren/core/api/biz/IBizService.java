package com.daren.core.api.biz;

import com.daren.core.api.IService;
import com.daren.core.api.persistence.IPersistentEntity;

import java.util.List;

/**
 * @类描述： 对外服务的根接口
 * @创建人：sunlf
 * @创建时间：2014-3-27 下午1:01:59
 * @修改人：sunlf
 * @修改时间：2014-3-27 下午1:01:59
 * @修改备注：
 */


public interface IBizService<T extends IPersistentEntity> extends IService {
    public T addEntity(T entity);

    public void deleteEntity(long entityId);

    public void saveEntity(T entity);

    public List getAllEntity();

    public T getEntity(long entityId);
}
