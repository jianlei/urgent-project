package com.daren.equipment.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.api.dao.IEquipmentBeanDao;
import com.daren.equipment.entities.EquipmentBean;

import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：dlw
 * @修改时间：2014-08-026 下午17:19
 * @修改备注：添加获取专家列表getEquipmentList()方法
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EquipmentBeanServiceImpl extends GenericBizServiceImpl implements IEquipmentBeanService {
    private IEquipmentBeanDao equipmentBeanDao;

    public void setEquipmentBeanDao(IEquipmentBeanDao equipmentBeanDao) {
        this.equipmentBeanDao = equipmentBeanDao;
        super.init(equipmentBeanDao, EquipmentBean.class.getName());
    }

    /**
     * 获取专家列表
     * @return
     */
    @Override
    public List<EquipmentBean> getEquipmentList() {
            return equipmentBeanDao.getAll(EquipmentBean.class.getName());
    }

    @Override
    public List<EquipmentBean> getRescueBeanByScope(String lng, String lat) {
        return equipmentBeanDao.findByNativeSql("SELECT *  FROM urgent_equipment  AS t where" +
                " (6371 * ACOS( COS( RADIANS(?2) ) * COS( RADIANS( wd) ) * COS( RADIANS(t.jd) - RADIANS(?1) ) + SIN( RADIANS(?2) ) * SIN( RADIANS(t.wd) ) ) ) < 500 ", EquipmentBean.class, lng, lat);
    }
}


