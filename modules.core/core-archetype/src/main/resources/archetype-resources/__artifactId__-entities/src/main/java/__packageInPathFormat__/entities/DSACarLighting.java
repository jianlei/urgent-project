package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：照明系统
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:45
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarLighting extends PersistentEntity {
    private boolean highIntensityGasDischargeHeadlight;                  // 高强度氙气前照灯
    private boolean afs;                                                 // 智能前照灯（AFS）
    private boolean headlampCleaningDevice;                              // 前大灯清洗装置
    private boolean frontRearFogLamps;                                   // 前后雾灯
    private boolean brakeLights;                                         // 高位制动灯
    private boolean turnSignalWelcomeLighting;                           // 外后视镜迎宾照明+转向灯（LED）
    private boolean frontRearReadingLights;                              // 前后排阅读灯
}
