package com.daren.production.core;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.api.dao.IProductionBeanDao;
import com.daren.production.entities.ProductionBean;

import java.util.List;

/**
 * Created by Administrator on 2014/9/10.
 */
public class ProductionServiceImpl extends GenericBizServiceImpl implements IProductionService {
    IProductionBeanDao productionBeanDao;

    public void setProductionBeanDao(IProductionBeanDao productionBeanDao) {
        this.productionBeanDao = productionBeanDao;
        super.init(productionBeanDao, ProductionBean.class.getName());
    }

    @Override
    public List<ProductionBean> getProductionByLoginName(String loginName) {
        return productionBeanDao.find("select p from ProductionBean p where p.loginName=?1", loginName);
    }
}
