package com.daren.enterprise.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.api.dao.IEnterpriseBeanDao;
import com.daren.enterprise.entities.EnterpriseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseBeanServiceImpl extends GenericBizServiceImpl implements IEnterpriseBeanService {
    private IEnterpriseBeanDao enterpriseBeanDao;

    public void setEnterpriseBeanDao(IEnterpriseBeanDao enterpriseBeanDao) {
        this.enterpriseBeanDao = enterpriseBeanDao;
        super.init(enterpriseBeanDao, EnterpriseBean.class.getName());
    }

    @Override
    public List<EnterpriseBean> queryEnterprise(EnterpriseBean enterpriseBean) {
        return enterpriseBeanDao.find("select a from EnterpriseBean a where a.qymc LIKE ?1", "%" + enterpriseBean.getQymc() + "%");
    }

    @Override
    public Map<String, String> getAllBeansToHashMap() {
        Map<String, String> hashMap = new HashMap<>();
        List<EnterpriseBean> list = enterpriseBeanDao.getAll(EnterpriseBean.class.getName());
        if (null != list && list.size() > 0) {
            for (EnterpriseBean bean : list) {
                hashMap.put(bean.getId() + "", bean.getQymc());
            }
        }
        return hashMap;
    }

    @Override
    public List<EnterpriseBean> findByName(String term, int page, int page_size) {
        return enterpriseBeanDao.findbyPage("select a from EnterpriseBean a where a.qymc LIKE ?1", page, page_size, "%" + term + "%");
    }

    @Override
    public EnterpriseBean getEnterpriseBeanByUUId(String uuId) {
        return enterpriseBeanDao.findUnique("select a from EnterpriseBean a where a.qyid LIKE ?1", "%" + uuId + "%");
    }
}


