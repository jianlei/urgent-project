package com.daren.fireworks.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.api.dao.IFireworksBeanDao;
import com.daren.fireworks.entities.FireworksBean;

import java.util.List;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksServiceImpl extends GenericBizServiceImpl implements IFireworksService {
    IFireworksBeanDao fireworksBeanDao;

    public void setFireworksBeanDao(IFireworksBeanDao fireworksBeanDao) {
        this.fireworksBeanDao = fireworksBeanDao;
        super.init(fireworksBeanDao, FireworksBean.class.getName());
    }

    @Override
    public List<FireworksBean> query(FireworksBean bean) {
        return null;
    }

    @Override
    public List<FireworksBean> getFireworksBeanByPhone(String phone) {
        return fireworksBeanDao.find("select f from FireworksBean f where f.phone=?1",phone);
    }
}