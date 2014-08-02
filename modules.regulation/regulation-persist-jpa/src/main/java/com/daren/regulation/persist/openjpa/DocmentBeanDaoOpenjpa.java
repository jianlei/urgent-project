package com.daren.regulation.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.regulation.api.dao.IDocmentBeanDao;
import com.daren.regulation.entities.DocmentBean;

import javax.persistence.Query;
import java.util.List;

/**
 * 应急演练管理-文档Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class DocmentBeanDaoOpenjpa extends GenericOpenJpaDao<DocmentBean, Long> implements IDocmentBeanDao {
    public List<DocmentBean> getDocmentBeanListByAttach(long id) {
        final Query query = entityManager.createQuery("select c from DocmentBean c where c.attach=" + id);
        final List<DocmentBean> resultList = query.getResultList();
        return resultList;
    }
}
