package com.daren.file.api.dao;


import com.daren.core.api.persistence.IGenericDao;
import com.daren.file.entities.DocmentBean;

import java.util.List;

/**
 * 应急演练管理-文档Dao接口
 * Created by 张清欣 on 14-7-28.
 */
public interface IDocmentBeanDao extends IGenericDao<DocmentBean, Long> {
    public List<DocmentBean> getDocmentBeanListByAttach(long id);
}
