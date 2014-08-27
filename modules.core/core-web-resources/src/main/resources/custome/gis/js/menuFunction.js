/**
 * 重大危险源
 * dlw
 */
function zdwxy(){
    $.getJSON(
        "http://localhost:9191/cxf/hazard",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                var responseJson = json.hazardBean;
                for(var i=0;i<responseJson.length;i++){
                    var response =responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>危险源名称: <span style='color:green;'>"+response.name+"</span></br>";
                    if (response.level!=undefined) {
                        content += "危险源级别: <span style='color:green;'>" + response.level + "</span></br>";
                    }
                    if (response.createBy!=undefined) {
                        content += "原因: <span style='color:green;'>"+response.createBy+"</span></br>";
                    }
                    if (response.creationDate!=undefined) {
                        content += "时间: <span style='color:green;'>"+response.creationDate+"</span></br>";
                    }
                    if (response.place!=undefined) {
                        content += "所在地址: <span style='color:green;'>"+response.place+"</span></br>";
                    }
                    if (response.startTime!=undefined) {
                        content += "投用时间: <span style='color:green;'>"+response.startTime+"</span></br>";
                    }
                    if (response.describe!=undefined) {
                        content += "主要装置: <span style='color:green;'>"+response.describe+"</span></br>";
                    }
                    if (response.inChemical!=undefined) {
                        content += "是否在化工园区: <span style='color:green;'>"+response.inChemical+"</span></br>";
                    }
                    if (response.distanceOtherHazard!=undefined) {
                        content += "距离其他重危目标距离: <span style='color:green;'>"+response.distanceOtherHazard+"</span></br>";
                    }
                    if (response.roadConditions!=undefined) {
                        content += "道路情况: <span style='color:green;'>"+response.roadConditions+"</span></br>";
                    }
                    content += "</div>";

                    content+='<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                        if (response.lastThreeYearAccident!=undefined) {
                            content += "近三年事故: <span style='color:green;'>"+response.lastThreeYearAccident+"</span></br>";
                        }
                        if (response.expertName!=undefined) {
                            content += "专家名称: <span style='color:green;'>"+response.expertName+"</span></br>";
                        }
                        if (response.accidentRate!=undefined) {
                            content += "可能发生的事故率: <span style='color:green;'>"+response.accidentRate+"</span></br>";
                        }
                        if (response.scope500MHaveMans!=undefined) {
                            content += "范围500米内人数估算值: <span style='color:green;'>"+response.scope500MHaveMans+"</span></br>";
                        }
                        if (response.estimate!=undefined) {
                            content += "评估: <span style='color:green;'>"+response.estimate+"</span></br>";
                        }
                    content+= '</div>';

                    addGeneralPoint(response.jd,response.wd,i,"label_zdwxy",content,"重大危险源详情",500,300);
                    if(i==responseJson.length-1){
                        moveMapByBound();
                    }
                }
            }else{
                alert("没有检索到重大危险源!");
            }
        });
}

/**
 * 救援队标注
 * dlw
 */
function jydbz(){
    $.getJSON(
        "http://localhost:9191/cxf/rescue",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                var responseJson = json.rescueBean;
                for(var i=0;i<responseJson.length;i++) {
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>救援队名称: <span style='color:green;'>" + response.name + "</span></br>";
                    if (response.head != undefined) {
                        content += "负责人: <span style='color:green;'>" + response.head + "</span></br>";
                    }
                    if (response.headPhone != undefined) {
                        content += "负责人电话: <span style='color:green;'>" + response.headPhone + "</span></br>";
                    }
                    if (response.telephone != undefined) {
                        content += "值班电话: <span style='color:green;'>" + response.telephone + "</span></br>";
                    }
                    if (response.totalNumber != undefined) {
                        content += "总人数: <span style='color:green;'>" + response.totalNumber + "</span></br>";
                    }

                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                    if (response.address != undefined) {
                        content += "地址: <span style='color:green;'>" + response.address + "</span></br>";
                    }
                    if (response.equipment != undefined) {
                        content += "主要装备描述: <span style='color:green;'>" + response.equipment + "</span></br>";
                    }
                    if (response.lastThreeYearAccident != undefined) {
                        content += "专长描述: <span style='color:green;'>" + response.expertise + "</span></br>";
                    }
                    if (response.remarks != undefined) {
                        content += "备注: <span style='color:green;'>" + response.remarks + "</span></br>";
                    }
                    content += '</div>';

                    addGeneralPoint(response.jd, response.wd, i, "label_jyd", content, "救援队详情", 500, 170);
                    if (i == responseJson.length - 1) {
                        moveMapByBound();
                    }
                }
            }else{
                alert("没有检索到救援队!");
            }
        });
}

/**
 * 专家标注
 * dlw
 */
function zjbz(){
    $.getJSON(
        "http://localhost:9191/cxf/expert",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                var responseJson = json.enterpriseExpertBean;
                for(var i=0;i<responseJson.length;i++) {
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>专家姓名: <span style='color:green;'>" + response.name + "</span></br>";
                    if (response.date != undefined) {
                        content += "出生日期: <span style='color:green;'>" + response.date + "</span></br>";
                    }
                    if (response.sex != undefined) {
                        content += "性别: <span style='color:green;'>" + response.sex + "</span></br>";
                    }
                    if (response.skillTitle != undefined) {
                        content += "技术职称: <span style='color:green;'>" + response.skillTitle + "</span></br>";
                    }
                    if (response.degree != undefined) {
                        content += "学位: <span style='color:green;'>" + response.degree + "</span></br>";
                    }
                    if (response.nation != undefined) {
                        content += "民族: <span style='color:green;'>" + response.nation + "</span></br>";
                    }
                    if (response.type != undefined) {
                        content += "专家类别: <span style='color:green;'>" + response.type + "</span></br>";
                    }
                    if (response.city != undefined) {
                        content += "所在城市: <span style='color:green;'>" + response.city + "</span></br>";
                    }
                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                    if (response.address != undefined) {
                        content += "通信地址: <span style='color:green;'>" + response.address + "</span></br>";
                    }
                    if (response.tel != undefined) {
                        content += "单位电话: <span style='color:green;'>" + response.tel + "</span></br>";
                    }
                    if (response.phone != undefined) {
                        content += "手机: <span style='color:green;'>" + response.phone + "</span></br>";
                    }
                    if (response.eMail != undefined) {
                        content += "邮箱: <span style='color:green;'>" + response.eMail + "</span></br>";
                    }
                    if (response.language != undefined) {
                        content += "外语语种: <span style='color:green;'>" + response.language + "</span></br>";
                    }
                    if (response.domain != undefined) {
                        content += "技术领域: <span style='color:green;'>" + response.domain + "</span></br>";
                    }
                    if (response.direction != undefined) {
                        content += "研究方向: <span style='color:green;'>" + response.direction + "</span></br>";
                    }
                    content += '</div>';

                    addGeneralPoint(response.jd, response.wd, i, "label_qyzj", content, "专家详情", 500, 200);
                    if (i == responseJson.length - 1) {
                        moveMapByBound();
                    }
                }
            }else{
                alert("没有检索到专家!");
            }
        });
}

/**
 * 物资标注
 * dlw
 */
function wzbj(){
    $.getJSON(
        "http://localhost:9191/cxf/equipment",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                var responseJson = json.equipmentBean;
                for(var i=0;i<responseJson.length;i++){
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>物资装备名称: <span style='color:green;'>" + response.name + "</span></br>";
                    if (response.date != undefined) {
                        content += "属性: <span style='color:green;'>" + response.property + "</span></br>";
                    }
                    if (response.sex != undefined) {
                        content += "登记类型: <span style='color:green;'>" + response.registrationType + "</span></br>";
                    }
                    if (response.rescueId != undefined) {
                        content += "所属救援队: <span style='color:green;'>" + response.rescueId + "</span></br>";
                    }
                    if (response.unitName != undefined) {
                        content += "所属单位: <span style='color:green;'>" + response.unitName + "</span></br>";
                    }
                    if (response.equipmentSources != undefined) {
                        content += "装备来源: <span style='color:green;'>" + response.equipmentSources + "</span></br>";
                    }
                    if (response.equipmentType != undefined) {
                        content += "物资类型: <span style='color:green;'>" + response.equipmentType + "</span></br>";
                    }
                    if (response.parametersSpecifications != undefined) {
                        content += "参数规格: <span style='color:green;'>" + response.parametersSpecifications + "</span></br>";
                    }
                    if (response.measuringUnit != undefined) {
                        content += "计量单位: <span style='color:green;'>" + response.measuringUnit + "</span></br>";
                    }
                    if (response.amount != undefined) {
                        content += "数量: <span style='color:green;'>" + response.amount + "</span></br>";
                    }
                    if (response.regularMaintenanceInterval != undefined) {
                        content += "定期保修间隔: <span style='color:green;'>" + response.regularMaintenanceInterval + "</span></br>";
                    }
                    if (response.durableYears != undefined) {
                        content += "使用年限: <span style='color:green;'>" + response.durableYears + "</span></br>";
                    }
                    if (response.lastMaintenanceDate != undefined) {
                        content += "上一次保养日期: <span style='color:green;'>" + response.lastMaintenanceDate + "</span></br>";
                    }
                    if (response.manufacturer != undefined) {
                        content += "生产厂家: <span style='color:green;'>" + response.manufacturer + "</span></br>";
                    }
                    if (response.manufactureDate != undefined) {
                        content += "生产日期: <span style='color:green;'>" + response.manufactureDate + "</span></br>";
                    }
                    if (response.purchaseDate != undefined) {
                        content += "购买日期: <span style='color:green;'>" + response.purchaseDate + "</span></br>";
                    }
                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                    if (response.unitFax != undefined) {
                        content += "单位传真: <span style='color:green;'>" + response.unitFax + "</span></br>";
                    }
                    if (response.direction != undefined) {
                        content += "主要负责人: <span style='color:green;'>" + response.principal + "</span></br>";
                    }
                    if (response.officePhone != undefined) {
                        content += "办公电话: <span style='color:green;'>" + response.officePhone + "</span></br>";
                    }
                    if (response.homePhone != undefined) {
                        content += "家庭电话: <span style='color:green;'>" + response.homePhone + "</span></br>";
                    }
                   if (response.mobilePhone != undefined) {
                        content += "移动电话: <span style='color:green;'>" + response.mobilePhone + "</span></br>";
                    }
                    if (response.describeOrPurposes != undefined) {
                        content += "装备描述或装备用途: <span style='color:green;'>" + response.describeOrPurposes + "</span></br>";
                    }
                    if (response.warehouse != undefined) {
                        content += "存放的仓库名: <span style='color:green;'>" + response.warehouse + "</span></br>";
                    }
                    if (response.storagePlace != undefined) {
                        content += "存放场所: <span style='color:green;'>" + response.storagePlace + "</span></br>";
                    }
                    if (response.img != undefined) {
                        content += "装备图片: <span style='color:green;'>" + response.img + "</span></br>";
                    }
                    if (response.remark != undefined) {
                        content += "备注: <span style='color:green;'>" + response.remark + "</span></br>";
                    }
                    content += '</div>';

                    addGeneralPoint(response.jd, response.wd, i, "label_wz", content, "物资详情", 500, 250);
                    if (i == responseJson.length - 1) {
                        moveMapByBound();
                    }
                }
            }else{
                alert("没有检索到物资!");
            }
        });
}