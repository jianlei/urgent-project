package com.daren.admin.core;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.dao.IDictBeanDao;
import com.daren.admin.entities.DictBeanImpl;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * @类描述：字典服务实现类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DictBeanServiceImpl extends GenericBizServiceImpl implements IDictBeanService {
    private IDictBeanDao dictBeanDao;


    public void setDictBeanDao(IDictBeanDao dictBeanDao) {
        this.dictBeanDao = dictBeanDao;
        super.init(dictBeanDao, com.daren.admin.entities.DictBeanImpl.class.getName());
    }

    @Override
    public List<DictBeanImpl> getDictList(String type) {
        return dictBeanDao.getDictList(type);
    }
}
