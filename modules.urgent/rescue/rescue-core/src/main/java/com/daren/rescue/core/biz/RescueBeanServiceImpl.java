package com.daren.rescue.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.api.dao.IRescueBeanDao;
import com.daren.rescue.entities.RescueBean;

import java.util.HashMap;
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

    @Override
    public HashMap<String, String> getAllBeansToHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        List<RescueBean> list = rescueBeanDao.getAll(RescueBean.class.getName());
        if (null != list && list.size() > 0) {
            for (RescueBean bean : list) {
                hashMap.put(bean.getId() + "", bean.getName());
            }
        }
        return hashMap;
    }


    @Override
    public List<RescueBean> getRescueList() {
        return rescueBeanDao.getAll(RescueBean.class.getName());
    }

    @Override
    public List<RescueBean> getRescueBeanByScope(String lng, String lat) {
        return rescueBeanDao.findByNativeSql("SELECT *  FROM urg_res_rescue  AS t where" +
                " (6371 * ACOS( COS( RADIANS(?2) ) * COS( RADIANS( wd) ) * COS( RADIANS(t.jd) - RADIANS(?1) ) + SIN( RADIANS(?2) ) * SIN( RADIANS(t.wd) ) ) ) < 500 ", RescueBean.class, lng, lat);
    }
}


