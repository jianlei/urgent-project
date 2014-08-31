package com.daren.accident.core.util;

import com.daren.admin.api.biz.IDictConstService;

/**
 * @类描述：导出Excel需要的常量类
 * @创建人：xukexin
 * @创建时间：2014/8/31 12:06
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ColumnStaticValue {

    //文件生成地址
    public final static String FILE_URI = "D:/a.xls";
    //excel对应的sheet名
    public final static String EQU_SHEET_NAME = "物资";
    public final static String EXP_SHEET_NAME = "专家";
    public final static String RES_SHEET_NAME = "救援队";
    //sheet对应的头
    public final static String EQU_HDADER_NAME = "物资统计清单";
    public final static String EXP_HEADER_NAME = "专家统计清单";
    public final static String RES_HEADER_NAME = "救援队统计清单";
    //excel要显示的列头，字段名和字段类型
    public final static String[][] equStrArr = {{"物资装备名称","name",""},{"属性","property", IDictConstService.EQUIPMENT_PROPERTY},
            {"登记类型","registrationType",IDictConstService.REGISTRATION_TYPE},{"所属救援队","rescueId",""},
            {"所属单位","unitName",""},{"装备来源","equipmentSources",""},{"物资类型","equipmentType",""},
            {"参数规格","parametersSpecifications",""},{"计量单位","measuringUnit",""},{"数量","amount",""},
            {"定期保修间隔","regularMaintenanceInterval",""},{"使用年限","durableYears",""},{"上一次保养日期","lastMaintenanceDate",""},
            {"生产厂家","manufacturer",""},{"生产日期","manufactureDate",""},{"购买日期","purchaseDate",""},{"单位传真","unitFax",""},
            {"主要负责人","principal",""} ,{"办公电话","officePhone",""} ,{"家庭电话","homePhone",""} ,{"移动电话","mobilePhone",""} ,
            {"装备描述或装备用途","describeOrPurposes",""} ,{"存放的仓库名","warehouse",""} ,{"存放场所","storagePlace",""},
            {"备注","remark",""} ,{"经度","jd",""} ,{"维度","wd",""}};
    public final static String[][] expStrArr = {{"专家姓名","name",""},{"出生日期","date",""},{"性别","sex",""},
            {"技术职称","skillTitle",""},{"学位","degree",""},{"民族","nation",""},{"专家类别","type",""},{"所在城市","city",""},
            {"通信地址","address",""},{"单位电话","tel",""},{"手机","phone",""},{"外语语种","language",""},
            {"技术领域","domain",""},{"研究方向","direction",""},{"经度","jd",""},{"纬度","wd",""}};
    public final static String[][] resStrArr = {{"救援队名称","name",""},{"负责人","head",""},{"负责人电话","headPhone",""},
            {"值班电话","telephone",""},{"总人数","totalNumber",""},{"地址","address",""},{"经度","jd",""},{"纬度","wd",""},
            {"主要装备描述","equipment",""},{"专长描述","expertise",""},{"备注","remarks",""}};
}
