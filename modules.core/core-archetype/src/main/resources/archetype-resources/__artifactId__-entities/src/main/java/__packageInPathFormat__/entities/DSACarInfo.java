package ${package}.entities;

import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：车型展示实体类
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:21
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "DSACarBraking")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DSACarInfo extends PersistentEntity {
    // 车型名称
    private String name;
    // 卖点主题
    private String theme;
    // 车型参数
    private DSACarParameter parameter;
    // 车型配置
    private DSACarConfiguration configuration;

}
