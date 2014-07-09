package com.daren.dsa.product;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：参数表概念类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:05
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ParameterTable {
    private List<ParameterItem> parameterItemList = new ArrayList<ParameterItem>();

    public List<ParameterItem> getParameterItemList() {
        return parameterItemList;
    }

    public void setParameterItemList(List<ParameterItem> parameterItemList) {
        this.parameterItemList = parameterItemList;
    }
}
