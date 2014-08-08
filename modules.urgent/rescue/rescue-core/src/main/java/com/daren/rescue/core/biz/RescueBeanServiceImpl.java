package com.daren.rescue.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.api.dao.IRescueBeanDao;
import com.daren.rescue.entities.RescueBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RescueBeanServiceImpl extends GenericBizServiceImpl implements IRescueBeanService {
    private IRescueBeanDao rescueBeanDao;

    public void setRescueBeanDao(IRescueBeanDao rescueBeanDao) {
        this.rescueBeanDao = rescueBeanDao;
        super.init(rescueBeanDao, RescueBean.class.getName());
    }

    @Override
    public List<RescueBean> query(RescueBean dictBean) {
        return rescueBeanDao.find("select r from RescueBean r where r.name=?1", dictBean.getName());
    }
}


