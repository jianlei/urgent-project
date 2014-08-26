package com.daren.digitalplan.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.api.dao.IDigitalPlanBeanDao;
import com.daren.digitalplan.entities.DigitalPlanBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DigitalPlanBeanServiceImpl extends GenericBizServiceImpl implements IDigitalPlanBeanService {
    private IDigitalPlanBeanDao digitalPlanBeanDao;

    public void setDigitalPlanBeanDao(IDigitalPlanBeanDao digitalPlanBeanDao) {
        this.digitalPlanBeanDao = digitalPlanBeanDao;
        super.init(digitalPlanBeanDao, DigitalPlanBean.class.getName());
    }

    @Override
    public List<DigitalPlanBean> queryDigitalPlanSource(DigitalPlanBean digitalplanBean) {
        return digitalPlanBeanDao.find("select a from DigitalPlanBean a where a.name LIKE ?1", "%" + digitalplanBean.getName() + "%");
    }
}


