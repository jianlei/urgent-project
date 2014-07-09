package com.daren.middleware.couchdb.model;

import org.lightcouch.Document;

/**
 * @类描述：车型定义模型类
 * @创建人：sunlf
 * @创建时间：2014-03-30 上午11:37
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CarInfo extends Document {
    private String name;
    private String price;
    String schema;

    public CarInfo() {
        schema = "CarType";
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
