package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：车型参数
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:26
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarParameter extends PersistentEntity {
    // 整车尺寸及质量
    private DSACarSizeQuality sizeQuality;
    // 发动机
    private DSACarEngine engine;
    // 动力传动系统
    private DSACarPowertrain powertrain;
    // 底盘转向系统
    private DSACarChassisSteering chassisStreering;
    // 制动系统
    private DSACarBraking braking;
}
