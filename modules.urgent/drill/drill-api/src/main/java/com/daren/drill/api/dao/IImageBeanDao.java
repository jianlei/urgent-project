package com.daren.drill.api.dao;

import com.daren.core.api.persistence.IGenericDao;
import com.daren.drill.entities.ImageBean;

import java.util.List;

/**
 * 应急演练管理-图片Dao接口
 * Created by 张清欣 on 14-7-28.
 */
public interface IImageBeanDao extends IGenericDao<ImageBean, Long> {
    public List<ImageBean> getImageBeanListByAttach(long id);
}
