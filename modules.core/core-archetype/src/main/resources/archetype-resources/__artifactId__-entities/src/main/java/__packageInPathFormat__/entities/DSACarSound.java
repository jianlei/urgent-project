package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：音响系统
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
public class DSACarSound extends PersistentEntity {
    private boolean dtv;                             // 数字电视接收系统
    private boolean dvd;                             // 单碟DVD
    private boolean usb;                             // USB接口
    private boolean sdCard;                          // SD卡
    private boolean aux;                             // AUX接口
    private boolean sharkFinAntenna;                 // 鲨鱼鳍式天线
    private boolean bluetoothPhone;                  // 车载蓝牙电话系统
    private long surroundSoundSpeakers;       // BOSE环绕立体声扬声器（个）
}
