package com.daren.dsa.product;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：产品概念类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:00
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Product {
    private List<ConfigurationTable> configurationTableList = new ArrayList<ConfigurationTable>();

    public List<ConfigurationTable> getConfigurationTableList() {
        return configurationTableList;
    }

    public void setConfigurationTableList(List<ConfigurationTable> configurationTableList) {
        this.configurationTableList = configurationTableList;
    }
}
