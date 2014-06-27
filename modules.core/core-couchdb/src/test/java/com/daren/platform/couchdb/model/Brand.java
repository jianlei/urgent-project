package com.daren.platform.couchdb.model;

import org.lightcouch.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：品牌类
 * @创建人：sunlf
 * @创建时间：2014-04-05 上午11:36
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Brand extends Document {
    private String describe;
    private String name;
    private String schema;
    private String type;
    private List<Deploy> deployList = new ArrayList<Deploy>();

    public List<Deploy> getDeployList() {
        return deployList;
    }

    public void setDeployList(List<Deploy> deployList) {
        this.deployList = deployList;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
