package com.daren.drill.api.dao;

import com.daren.core.api.persistence.IGenericDao;
import com.daren.drill.entities.VideoBean;

import java.util.List;

/**
 * 应急演练管理-视频Dao接口
 * Created by 张清欣 on 14-7-28.
 */
public interface IVideoBenaDao extends IGenericDao<VideoBean, Long> {
    public List<VideoBean> getVideoBeanListByAttach(long id);
}
