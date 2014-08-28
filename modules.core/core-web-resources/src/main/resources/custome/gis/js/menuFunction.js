/**
 * 重大隐患定时器
 */
/*$(function () {
//每10秒执行，无限次，并命名计时器名称为C
//若时间间隔抵到，但函式程序仍未完成则需等待执行函式完成后再继续计时
    $('body').everyTime('3das','C',function(){
        //执行一个会超过20秒以上的程式
        console.log("正在收索重大隐患中.............");
        //循环添加危险点
       //clearallmarker();
       var _lng =124.384483;
        var _lat =43.151036;
        var _icon="dkred";
        var lid=0;
        var responseJson ={};

       for(var i = 0;i<markers.length;i++) {
           alert(markers[i].getIcon());
            var lng =  markers[i].getPosition().lng;
            var lat  = markers[i].getPosition().lat;
            if(_lng === lng && _lat === lat){
                //map.removeOverlay(markers[i]);
            }else{
                addDangerPoint(_lng,_lat,lid,_icon,responseJson);
            }
           if(i==responseJson.length-1){
               moveMapByBound();
           }
        }

    },0,true);
});*/

/**
 * 重大危险源
 * dlw
 */
function zdwxy(){
    $.getJSON(
        "../../cxf/hazard",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                //var responseJson = json.hazardBean;
                var responseJson = json;
                for(var i=0;i<responseJson.length;i++){
                    var response =responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>危险源名称: <span style='color:green;'>"+response.name+"</span></br>";

                        content += "危险源级别: <span style='color:green;'>" + response.level + "</span></br>";


                        content += "原因: <span style='color:green;'>"+response.createBy+"</span></br>";


                        content += "时间: <span style='color:green;'>"+response.creationDate+"</span></br>";


                        content += "所在地址: <span style='color:green;'>"+response.place+"</span></br>";

                        content += "投用时间: <span style='color:green;'>"+response.startTime+"</span></br>";

                        content += "主要装置: <span style='color:green;'>"+response.describe+"</span></br>";

                        content += "是否在化工园区: <span style='color:green;'>"+response.inChemical+"</span></br>";

                        content += "距离其他重危目标距离: <span style='color:green;'>"+response.distanceOtherHazard+"</span></br>";

                        content += "道路情况: <span style='color:green;'>"+response.roadConditions+"</span></br>";

                    content += "</div>";

                    content+='<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';

                            content += "近三年事故: <span style='color:green;'>"+response.lastThreeYearAccident+"</span></br>";

                            content += "专家名称: <span style='color:green;'>"+response.expertName+"</span></br>";

                            content += "可能发生的事故率: <span style='color:green;'>"+response.accidentRate+"</span></br>";

                            content += "范围500米内人数估算值: <span style='color:green;'>"+response.scope500MHaveMans+"</span></br>";

                            content += "评估: <span style='color:green;'>"+response.estimate+"</span></br>";

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
        "../../cxf/rescue",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
                //var responseJson = json.rescueBean;
                var responseJson = json;
                for(var i=0;i<responseJson.length;i++) {
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>救援队名称: <span style='color:green;'>" + response.name + "</span></br>";
                        content += "负责人: <span style='color:green;'>" + response.head + "</span></br>";
                        content += "负责人电话: <span style='color:green;'>" + response.headPhone + "</span></br>";
                        content += "值班电话: <span style='color:green;'>" + response.telephone + "</span></br>";
                        content += "总人数: <span style='color:green;'>" + response.totalNumber + "</span></br>";
                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                        content += "地址: <span style='color:green;'>" + response.address + "</span></br>";
                        content += "主要装备描述: <span style='color:green;'>" + response.equipment + "</span></br>";
                        content += "专长描述: <span style='color:green;'>" + response.expertise + "</span></br>";
                        content += "备注: <span style='color:green;'>" + response.remarks + "</span></br>";
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
        "../../cxf/expert",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
               // var responseJson = json.enterpriseExpertBean;
                var responseJson = json;
                for(var i=0;i<responseJson.length;i++) {
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>专家姓名: <span style='color:green;'>" + response.name + "</span></br>";
                        content += "出生日期: <span style='color:green;'>" + response.date + "</span></br>";
                        content += "性别: <span style='color:green;'>" + response.sex + "</span></br>";
                        content += "技术职称: <span style='color:green;'>" + response.skillTitle + "</span></br>";
                        content += "学位: <span style='color:green;'>" + response.degree + "</span></br>";
                        content += "民族: <span style='color:green;'>" + response.nation + "</span></br>";
                        content += "专家类别: <span style='color:green;'>" + response.type + "</span></br>";
                        content += "所在城市: <span style='color:green;'>" + response.city + "</span></br>";
                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                        content += "通信地址: <span style='color:green;'>" + response.address + "</span></br>";
                        content += "单位电话: <span style='color:green;'>" + response.tel + "</span></br>";
                        content += "手机: <span style='color:green;'>" + response.phone + "</span></br>";
                        content += "邮箱: <span style='color:green;'>" + response.eMail + "</span></br>";
                        content += "外语语种: <span style='color:green;'>" + response.language + "</span></br>";
                        content += "技术领域: <span style='color:green;'>" + response.domain + "</span></br>";
                        content += "研究方向: <span style='color:green;'>" + response.direction + "</span></br>";
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
        "../../cxf/equipment",{"rand":Math.random()},function(json){
            if(json!=null){
                clearallmarker();
               // var responseJson = json.equipmentBean;
                var responseJson = json;
                for(var i=0;i<responseJson.length;i++){
                    var response = responseJson[i];
                    var content = "<div style='float:left;width:240px;padding-top:10px'>物资装备名称: <span style='color:green;'>" + response.name + "</span></br>";
                        content += "属性: <span style='color:green;'>" + response.property + "</span></br>";
                        content += "登记类型: <span style='color:green;'>" + response.registrationType + "</span></br>";
                        content += "所属救援队: <span style='color:green;'>" + response.rescueId + "</span></br>";
                        content += "所属单位: <span style='color:green;'>" + response.unitName + "</span></br>";
                        content += "装备来源: <span style='color:green;'>" + response.equipmentSources + "</span></br>";
                        content += "物资类型: <span style='color:green;'>" + response.equipmentType + "</span></br>";
                        content += "参数规格: <span style='color:green;'>" + response.parametersSpecifications + "</span></br>";
                        content += "计量单位: <span style='color:green;'>" + response.measuringUnit + "</span></br>";
                        content += "数量: <span style='color:green;'>" + response.amount + "</span></br>";
                        content += "定期保修间隔: <span style='color:green;'>" + response.regularMaintenanceInterval + "</span></br>";
                        content += "使用年限: <span style='color:green;'>" + response.durableYears + "</span></br>";
                        content += "上一次保养日期: <span style='color:green;'>" + response.lastMaintenanceDate + "</span></br>";
                        content += "生产厂家: <span style='color:green;'>" + response.manufacturer + "</span></br>";
                        content += "生产日期: <span style='color:green;'>" + response.manufactureDate + "</span></br>";
                        content += "购买日期: <span style='color:green;'>" + response.purchaseDate + "</span></br>";
                    content += "</div>";

                    content += '<div style="float:left;width:240px;margin:10px 0 0 5px;line-height:20px;padding:2px;">';
                        content += "单位传真: <span style='color:green;'>" + response.unitFax + "</span></br>";
                        content += "主要负责人: <span style='color:green;'>" + response.principal + "</span></br>";
                        content += "办公电话: <span style='color:green;'>" + response.officePhone + "</span></br>";
                        content += "家庭电话: <span style='color:green;'>" + response.homePhone + "</span></br>";
                        content += "移动电话: <span style='color:green;'>" + response.mobilePhone + "</span></br>";
                        content += "装备描述或装备用途: <span style='color:green;'>" + response.describeOrPurposes + "</span></br>";
                        content += "存放的仓库名: <span style='color:green;'>" + response.warehouse + "</span></br>";
                        content += "存放场所: <span style='color:green;'>" + response.storagePlace + "</span></br>";
                        content += "装备图片: <span style='color:green;'>" + response.img + "</span></br>";
                        content += "备注: <span style='color:green;'>" + response.remark + "</span></br>";
                    content += '</div>';

                    addGeneralPoint(response.jd, response.wd, i, "label_wz", content, "物资详情", 500, 280);
                    if (i == responseJson.length - 1) {
                        moveMapByBound();
                    }
                }
            }else{
                alert("没有检索到物资!");
            }
        });
}