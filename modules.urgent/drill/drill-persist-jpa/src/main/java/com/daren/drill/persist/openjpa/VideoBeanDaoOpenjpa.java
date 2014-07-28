package com.daren.drill.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.drill.api.dao.IVideoBenaDao;
import com.daren.drill.entities.VideoBean;

/**
 * 应急演练管理-视频Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class VideoBeanDaoOpenjpa extends GenericOpenJpaDao<VideoBean, Long> implements IVideoBenaDao{
}
