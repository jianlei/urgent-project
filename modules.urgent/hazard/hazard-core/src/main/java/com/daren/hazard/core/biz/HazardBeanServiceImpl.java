package com.daren.hazard.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.hazard.api.dao.IHazardBeanDao;
import com.daren.hazard.entities.HazardBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class HazardBeanServiceImpl extends GenericBizServiceImpl implements IHazardBeanService {
    private IHazardBeanDao hazardBeanDao;

    public void setHazardBeanDao(IHazardBeanDao hazardBeanDao) {
        this.hazardBeanDao = hazardBeanDao;
        super.init(hazardBeanDao, HazardBean.class.getName());
    }

    @Override
    public List<HazardBean> getHazardList() {
        return hazardBeanDao.getAll(HazardBean.class.getName());
    }

    @Override
    public List<HazardBean> queryHazardSource(HazardBean hazardBean) {
        return hazardBeanDao.find("select a from HazardBean a where a.name LIKE ?1", "%" + hazardBean.getName() + "%");
    }
}


