package com.daren.file.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.api.dao.IDocumentBeanDao;
import com.daren.file.entities.DocumentBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadDocumentServiceImpl extends GenericBizServiceImpl implements IUploadDocumentService {
    private IDocumentBeanDao documentBeanDao;

    public void setDocumentBeanDao(IDocumentBeanDao docmentBeanDao) {
        this.documentBeanDao = docmentBeanDao;
        super.init(docmentBeanDao, DocumentBean.class.getName());
    }

    public List<DocumentBean> getDocumentBeanListByAttach(long id) {
        return documentBeanDao.getDocumentBeanListByAttach(id);
    }

    @Override
    public List<DocumentBean> queryDocumentByAttach(long attachId) {
        List<DocumentBean> list = documentBeanDao.find("select a from DocumentBean a where a.attach LIKE ?1", "%" + attachId + "%");
        if(null != list && list.size() > 0){
            return list;
        }else{
            list = new ArrayList<DocumentBean>();
            list.add(new DocumentBean());
            return list;
        }
    }
}


