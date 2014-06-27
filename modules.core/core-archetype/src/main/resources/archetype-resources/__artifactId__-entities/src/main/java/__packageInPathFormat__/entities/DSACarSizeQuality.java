package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：整车尺寸及质量
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:29
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarSizeQuality extends PersistentEntity {
    private long length;                      // 长 [mm]
    private long width;                       // 宽 [mm]
    private long height;                      // 高 [mm]
    private long wheelbase;                   // 轴距 [mm]
    private long frontTrack;                  // 前轮距[mm]
    private long rearTrack;                   // 后轮距[mm]
    private long curbWeight;                  // 整备质量 [kg]
}
