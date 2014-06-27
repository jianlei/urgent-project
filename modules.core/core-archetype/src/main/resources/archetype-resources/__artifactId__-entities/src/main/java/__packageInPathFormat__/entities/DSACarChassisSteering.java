package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：底盘转向系统
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:35
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarChassisSteering extends PersistentEntity {
    private String frontSuspensionType;                      // 前悬架类型
    private String rearSuspensionType;                       // 后悬架类型
    private String helpType;                                 // 助力类型
}
