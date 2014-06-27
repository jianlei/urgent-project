package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：动力传动系统
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:34
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarPowertrain extends PersistentEntity {
    private String driveMode;                    // 驱动方式
    private String transmission;                 // 变速器
    private String tire;                         // 轮胎
    private String spareTire;                    // 备胎

}
