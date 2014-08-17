package com.daren.accident.core.biz;

import com.daren.accident.entities.AccidentBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentBeanServiceImpl extends GenericBizServiceImpl implements IAccidentBeanService {
    private IAccidentBeanDao accidentBeanDao;

    public void setAccidentBeanDao(IAccidentBeanDao accidentBeanDao) {
        this.accidentBeanDao = accidentBeanDao;
        super.init(accidentBeanDao, AccidentBean.class.getName());
    }

    @Override
    public List<AccidentBean> queryAccidentByAccidentLevel() {
        return accidentBeanDao.find("select a from AccidentBean a where a.accidentLevel ='1' ");
    }
}


