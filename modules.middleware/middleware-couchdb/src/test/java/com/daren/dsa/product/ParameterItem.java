package com.daren.dsa.product;

/**
 * @类描述：参数项概念类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:06
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ParameterItem {
    private String name;
    private String value;

    public ParameterItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
