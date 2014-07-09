package com.daren.middleware.couchdb.model.dsa;

/**
 * @类描述：固定费用购车方案定义类
 * @创建人：sunlf
 * @创建时间：2014-04-09 下午4:08
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class FixedFeeSchemaDefine extends SchemaDefine {
    private double fee; //固定费用数值

    public FixedFeeSchemaDefine() {
        super();
        this.feeType = SchemaDefine.FIXED_TYPE;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
