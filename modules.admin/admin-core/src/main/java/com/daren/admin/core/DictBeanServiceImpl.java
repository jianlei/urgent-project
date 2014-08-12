package com.daren.admin.core;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.dao.IDictBeanDao;
import com.daren.admin.entities.DictBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        super.init(dictBeanDao, DictBean.class.getName());
    }

    @Override
    public List<DictBean> getDictList(String type) {
        return dictBeanDao.getDictList(type);
    }

    @Override
    public Map<String, String> getDictMap(String type) {
        List<DictBean> dictBeanList = getDictList(type);
        Map map = new HashMap();
        if (dictBeanList.size() > 0) {
            for (DictBean dictBean : dictBeanList)
                map.put(dictBean.getValue(), dictBean.getLabel());
        }
        return map;
    }

    @Override
    public List<DictBean> query(DictBean dictBean) {
        return dictBeanDao.find("select a from DictBean a where a.type like ?1", "%" + dictBean.getType() + "%");
    }
}
