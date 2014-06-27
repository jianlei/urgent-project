package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：舒适装备
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:43
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarComfortEquipment extends PersistentEntity {
    private boolean fourSpokeLeatherMultifunctionSteeringWheel;               // 四辐真皮多功能方向盘
    private boolean eightInchesLCD;                                           // 8英寸触控液晶屏
    private boolean anv;                                                      // 主动夜视系统（ANV）
    private boolean smartEntryStart;                                          // 智能进入和启动系统
    private boolean gpsNavigation;                                            // GPS导航系统
    private boolean gpsAutomaticCalibrationClock;                             // GPS自动校准时钟
    private boolean leatherSeats;                                             // 真皮座椅（带靠背袋）
    private boolean eightWayPowerDriverSeatAdjustment;                        // 驾驶员座椅8向电动调节
    private boolean fourWayPowerFrontPassengerSeatAdjustment;                 // 副驾驶员座椅4向电动调节
    private boolean driverSeatMemoryFunction;                                 // 驾驶员座椅记忆功能
    private boolean heatedFrontRearSeats;                                     // 前后排座椅加热
    private boolean rearSeatVentilationMassageFunction;                       // 后排座椅通风按摩功能
    private boolean rearSeatTiltAngleAdjustable;                              // 后排座椅两侧靠背倾斜角度可调
    private boolean fourAdjustFrontHeadrests;                                 // 前排头枕4向调节
    private boolean twoAdjustRearHeadrests;                                   // 后排头枕2向调节
    private boolean frontSunVisorWithMirror;                                  // 前排遮阳板带化妆镜
    private boolean autoDimmingInsideRearviewMirror;                          // 自动防眩目内后视镜
    private boolean electricFoldingExteriorMirrorsWithMemory;                 // 电动折叠、带记忆功能的外后视镜
    private boolean uvInsulationAroundWindows;                                // 隔热防紫外线前后窗玻璃
    private boolean frontRearAntiInfraredHeatUvGlassDoor;                     // 前后隔热防红外/紫外线车门玻璃
    private boolean frontWindscreenDefoggingDefrost;                          // 前后风窗除霜除雾功能
    private boolean electricRearWindowSunshade;                               // 后风窗电动遮阳帘
    private boolean manualRearWindowsunshade;                                 // 后车窗手动遮阳帘
    private boolean pushAshtrayCigaretteLighter;                              // 按压式烟灰盒、点烟器
    private boolean multiFunctionRearCenterArmrest;                           // 后排多功能中央扶手
    private boolean threeZoneAutomaticAirConditioning;                        // 三温区自动空调
    private boolean frontCenterAirOutletAutomaticalSwing;                     // 前排中央出风口自动摆风功能
    private boolean electricTrunk;                                            // 电动行李箱
    private boolean carHeatingBox;                                            // 车载冷暖箱（8L）
    private boolean standbyPower;                                             // 备用电源
}
