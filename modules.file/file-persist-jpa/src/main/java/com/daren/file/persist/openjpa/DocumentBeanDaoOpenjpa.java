package com.daren.file.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.file.api.dao.IDocumentBeanDao;
import com.daren.file.entities.DocumentBean;

import javax.persistence.Query;
import java.util.List;

/**
 * 应急演练管理-文档Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class DocumentBeanDaoOpenjpa extends GenericOpenJpaDao<DocumentBean, Long> implements IDocumentBeanDao {
    public List<DocumentBean> getDocumentBeanListByAttach(long id) {
        final Query query = entityManager.createQuery("select c from DocmentBean c where c.attach=" + id);
        final List<DocumentBean> resultList = query.getResultList();
        return resultList;
    }
}
