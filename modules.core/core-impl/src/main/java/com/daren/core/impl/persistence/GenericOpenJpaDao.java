package com.daren.core.impl.persistence;


import com.daren.core.api.exception.SearchException;
import com.daren.core.api.persistence.IGenericDao;
import com.daren.core.api.persistence.IPersistentEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 14-1-16.
 */
public class GenericOpenJpaDao<T extends IPersistentEntity, PK extends Serializable> implements IGenericDao<T, PK> {
    protected final Logger log = Logger.getLogger(getClass());
    private Class<T> persistentClass;
    protected EntityManager entityManager;

    public void setEntityManager(EntityManager em) {
        entityManager = em;
    }

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
        entityManager.merge(object);
//        entityManager.persist(object);
        entityManager.flush();
        return object;
    }

    //    @Override
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
    public <T> T findUnique(String hql, Object... values) {
        List list = this.find(hql, values);
        if (list != null && list.size() > 0) {

            return (T) list.get(0);
        }
        return null;
    }

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
