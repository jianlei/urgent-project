package com.daren.file.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.file.api.dao.IImageBeanDao;
import com.daren.file.entities.ImageBean;

import javax.persistence.Query;
import java.util.List;

/**
 * 应急演练管理-图片Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class ImageBeanDaoOpenjpa extends GenericOpenJpaDao<ImageBean, Long> implements IImageBeanDao {
    public List<ImageBean> getImageBeanListByAttach(long id) {
        final Query query = entityManager.createQuery("select c from ImageBean c where c.attach=" + id);
        final List<ImageBean> resultList = query.getResultList();
        return resultList;
    }
}
