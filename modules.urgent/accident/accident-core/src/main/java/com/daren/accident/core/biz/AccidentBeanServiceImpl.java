package com.daren.accident.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.accident.api.biz.IaccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.entities.accidentBeanImpl;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentBeanServiceImpl extends GenericBizServiceImpl implements IaccidentBeanService {
    private IAccidentBeanDao accidentBeanDao;

    public void setaccidentBeanDao(IAccidentBeanDao accidentBeanDao) {
        this.accidentBeanDao = accidentBeanDao;
        super.init(accidentBeanDao, accidentBeanImpl.class.getName());
    }
}


