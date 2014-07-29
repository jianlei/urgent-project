package com.daren.majorhazardsource.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.majorhazardsource.api.biz.IMajorHazardSourceBeanService;
import com.daren.majorhazardsource.api.dao.IMajorHazardSourceBeanDao;
import com.daren.majorhazardsource.entities.MajorHazardSourceBean;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MajorHazardSourceBeanServiceImpl extends GenericBizServiceImpl implements IMajorHazardSourceBeanService {
    private IMajorHazardSourceBeanDao majorHazardSourceBeanDao;

    public void setMajorHazardSourceBeanDao(IMajorHazardSourceBeanDao majorHazardSourceBeanDao) {
        this.majorHazardSourceBeanDao = majorHazardSourceBeanDao;
        super.init(majorHazardSourceBeanDao, MajorHazardSourceBean.class.getName());
    }
}


