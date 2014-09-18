package com.daren.production.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.production.entities.ProductionBean;

import java.util.List;

/**
 * @类描述：安全资格证书(培训)
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IProductionService extends IBizService {
    public List<ProductionBean> getProductionByPhone(String phone);
}
