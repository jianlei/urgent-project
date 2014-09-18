package com.daren.regulation.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.regulation.entities.DocmentBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUploadDocumentService extends IBizService {
    public List<DocmentBean> getDocmentBeanListByAttach(long id);
}
