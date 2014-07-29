package com.daren.drill.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.drill.api.dao.IImageBeanDao;
import com.daren.drill.entities.ImageBean;

/**
 * 应急演练管理-图片Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class ImageBeanDaoOpenjpa extends GenericOpenJpaDao<ImageBean, Long> implements IImageBeanDao {
}
