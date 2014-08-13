package com.daren.equipment.core.biz;

import com.daren.equipment.entities.EquipmentBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.api.dao.IEquipmentBeanDao;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
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
}

