package com.daren.middleware.couchdb.model.dsa;

/**
 * @类描述：公式类费用购车方案定义类
 * @创建人：sunlf
 * @创建时间：2014-04-09 下午4:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class FormulaSchemaDefine extends SchemaDefine {
    private String formula; //费用计算公式

    public FormulaSchemaDefine() {
        super();
        this.feeType = SchemaDefine.FORMULA_TYPE;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
