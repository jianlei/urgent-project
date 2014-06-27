package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：安全装备
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarSafetyEquipment extends PersistentEntity {
    private boolean dualFrontAirbags;                      // 智能式前排双气囊（双级启爆）
    private boolean dualFrontKneeAirbags;                  // 前排膝部双气囊
    private boolean frontSideAirbags;                      // 前排侧气囊
    private boolean headCurtainSideAir;                    // 头部侧气帘
    private boolean frontRestraintPreloadSeatBelt;         // 前排三点约束机械式预紧安全带
    private boolean rearMechanicalSeatBelt;                // 后排三点机械式安全带
    private boolean rearChildSeatFixtures;                 // 后排儿童座椅固定装置
    private boolean electricAntiPinchWindowLifter;         // 前后电动防夹车窗
    private boolean electricAntiPinchSunroof;              // 电动防夹天窗
    private boolean vehicleAntiTheft;                      // 车身防盗系统
    private boolean engineImmobilizer;                     // 发动机防盗系统
    private boolean directTirePressureWarning;             // 直接式胎压报警系统
    private boolean front4ParkingRadar;                    // 前部4探头驻车雷达
    private boolean rear4ParkingRadar;                     // 后部4探头驻车雷达
    private boolean reversingVideoImaging;                 // 倒车视频影像系统
    private boolean absEbd;                                // ABS+EBD
    private boolean epb;                                   // 电子驻车制动系统（EPB）
    private boolean tcs;                                   // 牵引力控制系统（TCS）
    private boolean bas;                                   // 刹车辅助系统（BAS）
    private boolean cdp;                                   // 车辆动态减速功能（CDP）
    private boolean esp;                                   // 车身电子稳定系统（ESP）
    private boolean fcw;                                   // 前防撞预警系统（FCW）
    private boolean ldw;                                   // 道路偏航预警系统（LDW）
    private boolean driverStateMonitoring;                 // 驾驶员状态监控系统
    private boolean ccs;                                   // 定速巡航系统（CCS）
    private boolean aac;                                   // 带停-走功能的主动巡航系统（ACC）
    private boolean autoHold;                              // 自动保持功能（AUTO HOLD）
}
