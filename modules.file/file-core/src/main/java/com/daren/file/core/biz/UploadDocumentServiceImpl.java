package com.daren.file.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.api.dao.IDocmentBeanDao;
import com.daren.file.entities.DocmentBean;

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
    private IDocmentBeanDao docmentBeanDao;

    public void setDocmentBeanDao(IDocmentBeanDao docmentBeanDao) {
        this.docmentBeanDao = docmentBeanDao;
        super.init(docmentBeanDao, DocmentBean.class.getName());
    }

    public List<DocmentBean> getDocmentBeanListByAttach(long id) {
        return docmentBeanDao.getDocmentBeanListByAttach(id);
    }

    @Override
    public List<DocmentBean> queryDocmentByAttach(long attachId) {
        List<DocmentBean> list = docmentBeanDao.find("select a from DocmentBean a where a.attach LIKE ?1", "%" + attachId + "%");
        if(null != list && list.size() > 0){
            return list;
        }else{
            list = new ArrayList<DocmentBean>();
            list.add(new DocmentBean());
            return list;
        }
    }

}


