package ${package}.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：发动机
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:32
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarEngine {
    private String engineModel;                        // 发动机型号
    private String displacement;                       // 排量 [L]
    private long cylinders;                                   // 气缸数
    private String maxOutputTorque;                    // 最大输出扭矩 [Nm/rpm]
    private String ratedPower;                         // 额定功率 [kW/rpm]
    private String environmentalStandards;             // 环保标准
}
