package com.daren.regulation.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.regulation.api.biz.IRegulationBeanService;
import com.daren.regulation.api.dao.IRegulationBeanDao;
import com.daren.regulation.entities.RegulationBean;

import java.util.List;

/**
 * @类描述：法律法规
 * @创建人：张清欣
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RegulationBeanServiceImpl extends GenericBizServiceImpl implements IRegulationBeanService {
    private IRegulationBeanDao regulationBeanDao;

    public void setRegulationBeanDao(IRegulationBeanDao regulationBeanDao) {
        this.regulationBeanDao = regulationBeanDao;
        super.init(regulationBeanDao, RegulationBean.class.getName());
    }

    @Override
    public List<RegulationBean> query(RegulationBean dictBean) {
        return regulationBeanDao.find("select r from RegulationBean r where r.name=?1", dictBean.getName());
    }
}


