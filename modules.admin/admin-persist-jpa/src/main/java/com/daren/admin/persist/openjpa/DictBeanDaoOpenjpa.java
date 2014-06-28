package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IDictBeanDao;
import com.daren.admin.entities.DictBeanImpl;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

import javax.persistence.Query;
import java.util.List;

/**
 * @类描述：字典Dao实现类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午2:00
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DictBeanDaoOpenjpa extends GenericOpenJpaDao<DictBeanImpl, Long> implements IDictBeanDao {
    @Override
    public List<DictBeanImpl> getDictList(String type) {
        final Query query =createQuery("select c  from DictBeanImpl c where c.type=?1", type);
        final List<DictBeanImpl> resultList = query.getResultList();
        return resultList;
    }
}
