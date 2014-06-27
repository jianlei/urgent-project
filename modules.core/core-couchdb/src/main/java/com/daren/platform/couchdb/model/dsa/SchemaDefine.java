package com.daren.platform.couchdb.model.dsa;

import org.lightcouch.Document;

/**
 * @类描述：购车方案定义类
 * @创建人：sunlf
 * @创建时间：2014-04-09 下午4:04
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SchemaDefine extends Document {
    public static final String FIXED_TYPE = "fixed";
    public static final String OPTIONAL_TYPE = "optional";
    public static final String FORMULA_TYPE = "formula";
    public static final String GROUP_TYPE = "group";

    protected String name;//款项名称
    protected String desc;//款项描述
    protected String feeType;//费用类别（fixed,optional,formula,group）四种
    protected boolean isEdit;//页面是否可编辑
    protected int showIndex;//显示索引
    private String schemaType = "SchemaDefine";//couchdb中用于区分document
    private SchemaDefine parent;

    public SchemaDefine() {
    }

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public SchemaDefine getParent() {
        return parent;
    }

    public void setParent(SchemaDefine parent) {
        this.parent = parent;
    }
}
