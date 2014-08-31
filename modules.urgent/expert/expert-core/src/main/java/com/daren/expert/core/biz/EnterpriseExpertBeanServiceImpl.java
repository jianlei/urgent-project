package com.daren.expert.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.api.dao.IEnterpriseExpertBeanDao;
import com.daren.expert.entities.EnterpriseExpertBean;

import java.util.List;

/**
 * @类描述：企业专家服务实现类
 * @创建人：张清欣
 * @创建时间：2014-04-04 下午2:42
 * @修改人：dlw
 * @修改时间：8-26
 * @修改备注：创建getExpertList()方法
 */

public class EnterpriseExpertBeanServiceImpl extends GenericBizServiceImpl implements IEnterpriseExpertBeanService {
    private IEnterpriseExpertBeanDao enterpriseExpertBeanDao;

    public void setEnterpriseExpertBeanDao(IEnterpriseExpertBeanDao enterpriseExpertBeanDao) {
        this.enterpriseExpertBeanDao = enterpriseExpertBeanDao;
        super.init(enterpriseExpertBeanDao, EnterpriseExpertBean.class.getName());
    }

    @Override
    public List<EnterpriseExpertBean> query(EnterpriseExpertBean dictBean) {
        return enterpriseExpertBeanDao.find("select e from EnterpriseExpertBean e where e.name=?1 ", dictBean.getName());
    }

    /**
     *
     * @return
     */
    @Override
    public List<EnterpriseExpertBean> getExpertList() {
        return enterpriseExpertBeanDao.getAll(EnterpriseExpertBean.class.getName());
    }

    @Override
    public List<EnterpriseExpertBean> getEnterpriseExpertByScope(String lng, String lat,String distance) {
        return enterpriseExpertBeanDao.findByNativeSql("SELECT *  FROM urg_exp_enterprise  AS t where" +
                " (6371 * ACOS( COS( RADIANS(?2) ) * COS( RADIANS( wd) ) * COS( RADIANS(t.jd) - RADIANS(?1) ) + SIN( RADIANS(?2) ) * SIN( RADIANS(t.wd) ) ) ) < ?3", EnterpriseExpertBean.class, lng, lat,distance);
    }
}


