package com.daren.regulation.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.regulation.api.biz.IUploadDocumentService;
import com.daren.regulation.api.dao.IDocmentBeanDao;
import com.daren.regulation.entities.DocmentBean;

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
    private IDocmentBeanDao docmentBeanDao;

    public void setDocmentBeanDao(IDocmentBeanDao docmentBeanDao) {
        this.docmentBeanDao = docmentBeanDao;
        super.init(docmentBeanDao, DocmentBean.class.getName());
    }

    public List<DocmentBean> getDocmentBeanListByAttach(long id) {
        return docmentBeanDao.getDocmentBeanListByAttach(id);
    }

}


