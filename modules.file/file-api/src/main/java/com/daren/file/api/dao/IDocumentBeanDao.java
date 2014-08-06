package com.daren.file.api.dao;


import com.daren.core.api.persistence.IGenericDao;
import com.daren.file.entities.DocumentBean;

import java.util.List;

/**
 * 应急演练管理-文档Dao接口
 * Created by 张清欣 on 14-7-28.
 */
public interface IDocumentBeanDao extends IGenericDao<DocumentBean, Long> {
    public List<DocumentBean> getDocumentBeanListByAttach(long id);
}
