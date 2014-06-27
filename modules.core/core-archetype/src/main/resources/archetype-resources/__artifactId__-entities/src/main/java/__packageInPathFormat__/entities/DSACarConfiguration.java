package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述： 车型配置
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:38
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarConfiguration extends PersistentEntity {
    private DSACarSafetyEquipment safetyEquipment;               // 安全装备
    private DSACarComfortEquipment comfortEquipment;             // 舒适装备
    private DSACarLighting lighting;                             // 照明系统
    private DSACarSound sound;                                   // 音响系统
}
