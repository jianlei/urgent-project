package com.daren.admin.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：字典实体类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:49
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_dict")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DictBean extends PersistentEntity {
    @NotNull(message = "'标签名'是必填项")
    private String label;    // 标签名
    @NotNull(message = "'数据值'是必填项")
    private String value;    // 数据值
    @NotNull(message = "'类型'是必填项")
    private String type;    // 类型
    private String description;// 描述
    @NotNull(message = "'排序'是必填项")
    private Integer sort;    // 排序

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
