package com.daren.file.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.file.entities.DocumentBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUploadDocumentService extends IBizService {
    public List<DocumentBean> getDocumentBeanListByAttach(long id);
    List<DocumentBean> queryDocumentByAttach(long attachId);
}
