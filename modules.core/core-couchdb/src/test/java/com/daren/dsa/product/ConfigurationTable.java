package com.daren.dsa.product;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：配置表类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:02
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ConfigurationTable {
    private List<ConfigurationItem> configurationItemList = new ArrayList<ConfigurationItem>();

    public List<ConfigurationItem> getConfigurationItemList() {
        return configurationItemList;
    }

    public void setConfigurationItemList(List<ConfigurationItem> configurationItemList) {
        this.configurationItemList = configurationItemList;
    }
}
