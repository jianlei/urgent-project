package com.daren.core.impl.persistence;


import com.daren.core.api.persistence.IGenericDao;
import com.daren.core.api.persistence.PersistentEntity;
import com.daren.core.api.persistence.SearchException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @类描述：DAO数据访问通用实现类
 * @创建人：sunlf
 * @创建时间：2014-7-3 下午1:01:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class GenericOpenJpaDao<T extends PersistentEntity, PK extends Serializable> implements IGenericDao<T, PK> {
    protected final Logger log = Logger.getLogger(getClass());
    protected EntityManager entityManager;
    private Class<T> persistentClass;

    public GenericOpenJpaDao() {
        Object obj = this.getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        java.lang.reflect.Type type = genericSuperclass.getActualTypeArguments()[0];
        if (type instanceof Class) {
            this.persistentClass = (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            this.persistentClass = (Class<T>) ((ParameterizedType) type).getRawType();
        }
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericOpenJpaDao(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void setEntityManager(EntityManager em) {
        entityManager = em;
    }

    @Override
    public List<T> getAll(String className) {
        final Query query = entityManager.createQuery("select c from " + className + " c ");
        final List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> getAllDistinct(String className) {
        final Query query = entityManager.createQuery("select Distinct c from " + className + " c ");
        final List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<T> search(String searchTerm) throws SearchException {
        return null;
    }

    @Override
    public T get(String className, PK id) {

        try {
            return (T) entityManager.find(Class.forName(className), id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(PK id) {
        return false;
    }

    @Override
    public T save(T object) {
        if (object.getId() == 0)//do not persist
            entityManager.persist(object);
        else {
            object.setUpdateDate(new Date());
            entityManager.merge(object);
        }

        entityManager.flush();
        return object;
    }

    @Override
    public T save(T object, String userName) {
        if (object.getId() == 0)//do not persist
        {
            object.setCreateBy(userName);
            entityManager.persist(object);
        } else {
            object.setUpdateBy(userName);
            object.setUpdateDate(new Date());
            entityManager.merge(object);
        }
        entityManager.flush();
        return object;
    }

    //@Override
    public void remove(String className, T object) {
        entityManager.remove(object);
        entityManager.flush();
    }

    @Override
    public void remove(String className, PK id) {
        Object object = get(className, id);
        entityManager.remove(object);
        entityManager.flush();
    }

    @Override
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        return null;
    }

    @Override
    public void reindex() {

    }

    @Override
    public void reindexAll(boolean async) {

    }

    /**
     * 按HQL查询唯一对象.
     */
    @Override
    public <T> T findUnique(String hql, Object... values) {
        List list = this.find(hql, values);
        if (list != null && list.size() > 0) {

            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public List find(String hql, Object... values) {
        return createQuery(hql, values).getResultList();
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     */

    protected Query createQuery(String queryString, Object... parameter) {
        Query queryObject = entityManager.createQuery(queryString);
        if (parameter != null) {
            for (int i = 0; i < parameter.length; i++) {
                queryObject.setParameter(i + 1, parameter[i]);
            }
        }

        return queryObject;
    }

    /**
     * 更新
     *
     * @param qlString
     * @param parameter
     * @return
     */
    @Override
    public int update(String qlString, Object... parameter) {
        return createQuery(qlString, parameter).executeUpdate();
    }
}
