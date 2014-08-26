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
                    content += "危险源级别: <span style='color:green;'>"+response.level+"</span></br>";
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

                    addGeneralPoint(response.lng,response.lat,i,"label_zdwxy",content,"重大危险源详情",500,300);
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
                for(var i=0;i<responseJson.length;i++){

                }
                alert("已检索到救援队");
            }else{
                alert("没有检索到救援队!");
            }
        });
}
