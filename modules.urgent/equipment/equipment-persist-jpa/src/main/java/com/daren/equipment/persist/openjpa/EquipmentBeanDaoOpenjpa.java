package com.daren.equipment.persist.openjpa;

import com.daren.equipment.entities.EquipmentBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.equipment.api.dao.IEquipmentBeanDao;


/**
 * Created by dell on 14-1-16.
 */
public class EquipmentBeanDaoOpenjpa extends GenericOpenJpaDao<EquipmentBean, Long> implements IEquipmentBeanDao {

}
