package com.daren.danger.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.danger.api.biz.IDangerBeanService;
import com.daren.danger.api.dao.IDangerBeanDao;
import com.daren.danger.entities.DangerBean;

import java.util.List;

/**
 * @类描述：危化品服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DangerBeanServiceImpl extends GenericBizServiceImpl implements IDangerBeanService {

    private IDangerBeanDao dangerBeanDao;

    public void setDangerBeanDao(IDangerBeanDao dangerBeanDao) {
        this.dangerBeanDao = dangerBeanDao;
        super.init(dangerBeanDao, DangerBean.class.getName());
    }

    @Override
    public List<DangerBean> getDangerList() {
        return dangerBeanDao.getAll(DangerBean.class.getName());
    }

    @Override
    public List<DangerBean> queryDangerSource(DangerBean dangerBean) {
        return dangerBeanDao.find("select a from DangerBean a where a.name LIKE ?1", "%" + dangerBean.getName() + "%");
    }
}


