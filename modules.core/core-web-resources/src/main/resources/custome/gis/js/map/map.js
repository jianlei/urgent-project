﻿/**
 * dlw
 */
//--地图全局变量
var infocontent = " ";

var markers = [];//标注集合

var infowindow;//普通标注窗体对象
var infowindow_danger;//危险点的单独窗体 放在mouseon 关闭的

var openWindowMarket;

var geoAddress = "";

var map;//地图全局变量

var image = 'beachflag.png';

var maptype = "";

var nowPositon = "";

var index = -1;

var trackcurrnetindex = -1;

var geocoder;

var cityname = "四平市";//初始化城市
var lev = 13;//初始化城市视野级别
var seenView = 18;//查看视野级别
var current_danger_point_number = -1;//当前危险点个数 默认-1
var overlays = [];//圆圈收索结果集
var isPanelShow = false;//true 显示右侧面板 false 隐藏
var isClickBtn = false;//true 单击右侧按钮 false 为单击
var playStatus = true;//true 播放  false 禁止播放
var ref = "";
var searchAjax = function () {
};
var setSelectTips = function () {
};
var hasTips = function () {
};
var isMaxTips = function () {
};
var setTips = function () {
};
var delTips = function () {
};
var getTips = function () {
};
var getTipsId = function () {
};
var getTipsIdAndTag = function () {
};
var G_tocard_maxTips = 30;
//重大危险源标注集合
var markers_zdwxy = [];
//救援队标注集合
var markers_jydbz = [];
//专家标注集合
var markers_zjbz = [];
//物资标注集合
var markers_wzbj = [];
var markers_dbbj = [];
var contain_danger_point = false;
var distance = 0.5;//默认500米范围内
var search_list;//检索结果拼接resultList_search(json) 存放临时search 结果

var sendToServer;

//--初始化地图
function initialize(lng, lat, cityname, lev, contenid) {
    map = new BMap.Map(contenid);//在百度地图容器中创建一个地图
    if (cityname) {
        map.centerAndZoom(cityname, lev);
    } else {
        var point = new BMap.Point(lng, lat);//定义一个中心点坐标
        map.centerAndZoom(point, lev);//设定地图的中心点和坐标并将地图显示在地图容器中
    }
    //map = map;//将map变量存储在全局
    geocoder = new BMap.Geocoder();
    map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
    // map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));     //2D图，卫星图
    map.enableScrollWheelZoom();//启用地图滚轮放大缩小
    map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
    map.enableKeyboard();//启用键盘上下左右键移动地图
    //向地图中添加缩放控件
    map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
    //向地图中添加缩略图控件
    //var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
    //map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
    var ctrl_sca = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});
    map.addControl(ctrl_sca);
    rightTab();//地图表面控件
    return true;
}

//添加危险点
function addDangerPoint(_lng, _lat, lid, _icon, responseJson, content_qy, content_anj) {
    //样式3宽高
    var infowindow_width = 520;
    var infowindow_height = 360;

    var streamSvrIp = "";
    var TextIP = "";
    var TextName1 = "";
    var TextPwd1 = "";
    /**g
     var _lng =124.384483;
     var _lat =43.151036;
     var _icon="dkred";
     **/
    var point = new BMap.Point(_lng, _lat);
    var jsonicon = {w: 21, h: 35, l: 0, t: 0, x: 6, lb: 5};
    var iconImg = createIcon(jsonicon, _icon);
    var marker = new BMap.Marker(point, {icon: iconImg});
    map.addOverlay(marker);
    markers.push(marker);
    current_danger_point_number++;
    marker.addEventListener("click", function () {

        $.getJSON(
            "../../cxf/monitor/" + responseJson.id + "", {"rand": Math.random()}, function (json) {
                if (json != null) {
                    //信息窗口的内容定义
                    var content = '<div style="float:left; width:250px;margin:0;line-height:20px;padding:2px;">';
                    content += '事故标题:' + responseJson.accidentTitle + '<br/>';
                    content += '事故类别:' + responseJson.accidentType + '<br/>';
                    content += '事故级别:' + responseJson.accidentLevel + '<br/>';
                    content += '事故发生地点：' + responseJson.place + '<br/>';
                    content += '详细地点：' + responseJson.detailsPlace + '<br/>';
                    content += '事故单位：' + responseJson.qymc + '<br/>';
                    content += '企业联系方式:' + responseJson.qylxfs + '<br/>';
                    content += '上报人:' + responseJson.operator + '<br/>';
                    content += '上报人电话:' + responseJson.operatorPhone + '<br/>';
                    content += '<div id = "sg_btn" > <input name="" type="button" style="margin-right:10px;" value="事故确认" onClick="goConfirm(true,' + _lng + ',' + _lat + ',\'' + responseJson.id + '\',' + responseJson.accident_id + ')"/> <input name="" type="button" value="事故解除" onClick="goConfirm(false,' + _lng + ',' + _lat + ',\'' + responseJson.id + '\',' + responseJson.accident_id + ')"/></div>' +

                        '</div>';
                    content += '<div class="demo" style="float:left; ">' +
                        '<div class="plus-tag-add">' +
                        '<form id="" action="" class="login">' +
                        '<ul class="Form FancyForm">' +
                        '<li>' +
                        '<a href="javascript:void(0);">解决方案</a>' +
                        '</li>' +
                        '</ul>' +
                        '</form>' +
                        '</div>' +

                        '<div id="mycard-plus" style="display:none;">' +
                        '<div class="default-tag tagbtn"  id="clearfix_div">' +
                        '<div>----企业预案------------></div>' +
                        '<div class="clearfix">';
                    content += content_qy;
                    /* '<a value="1" title="互联网" href="javascript:void(0);"><span>互联网</span><em></em></a>' +
                     '<a value="2" title="移动互联网" href="javascript:void(0);"><span>移动互联网</span><em></em></a>' +
                     '<a value="3" title="it" href="javascript:void(0);"><span>it</span><em></em></a>' +
                     '<a value="4" title="电子商务" href="javascript:void(0);"><span>电子商务</span><em></em></a>' +
                     '<a value="5" title="广告" href="javascript:void(0);"><span>广告</span><em></em></a>' +
                     '<a value="6" title="网络编辑" href="javascript:void(0);"><span>网络编辑</span><em></em></a>' +*/
                    content += '</div>' +
                        '<div>----安监局预案---------></div>' +
                        '<div class="clearfix">';
                    content += content_anj;
                    content += '</div>' +
                        /* '<div class="clearfix" style="display:none;"><a value="-1" title="媒体" href="javascript:void(0);"><span>媒体</span><em></em></a></div>' +
                         '<div class="clearfix" style="display:none;"><a value="-1" title="网络营销" href="javascript:void(0);"><span>网络营销</span><em></em></a></div>' +*/
                        '</div>' +
                        '<div align="right"><a href="javascript:void(0);" id="change-tips" style="color:#3366cc;display:none">换一换</a></div>' +
                        '</div>' +
                        '</div>';
                    if (json.length > 0) {
                        if (/msie/.test(navigator.userAgent.toLowerCase())) {
                            TextIP = json[0].ipAddress;
                            TextName1 = json[0].admin;
                            TextPwd1 = json[0].password;
                            infowindow_width = 1070;
                            content += '<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                                '<iframe align="center" id="iframe_viedo" width="550" height="360" src="../../cus/gis/js/map/PlayViewDemo.htm?streamSvrIp=' + streamSvrIp + '&&TextIP=' + TextIP + '&&TextName1=' + TextName1 + '&&TextPwd1=' + TextPwd1 + '" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>' +
                                '</div>';
                        } else {
                            alert("请选择IE浏览器!否则影响监控视频观看!");
                        }
                    }

                    //样式3
                    infowindow_danger = new BMapLib.SearchInfoWindow(map, content, {
                        title: "事故详情", //标题
                        //width: 590, //宽度
                        //height: 250, //高度
                        width: infowindow_width, //宽度
                        height: infowindow_height, //高度
                        panel: "panel", //检索结果面板
                        enableAutoPan: true, //自动平移
                        searchTypes: [
                        ]
                    });
                    infowindow_danger.open(new BMap.Point(marker.getPosition().lng, marker.getPosition().lat));

                    //标签脚本
                    $(function () {

                        var a = $(".plus-tag");

                        hasTips = function (b) {
                            var d = $("a", a), c = false;
                            d.each(function () {
                                if ($(this).attr("title") == b) {
                                    c = true;
                                    return false
                                }
                            });
                            return c
                        };

                        isMaxTips = function () {
                            return
                            $("a", a).length >= G_tocard_maxTips
                        };

                        setTips = function (c, d) {
                            if (hasTips(c)) {
                                return false
                            }
                            if (isMaxTips()) {
                                alert("最多添加" + G_tocard_maxTips + "个标签！");
                                return false
                            }
                            /*  var	printer_btn_sec = document.createElement("img");
                             printer_btn_sec.style.width="16px";
                             printer_btn_sec.style.height="16px";
                             printer_btn_sec.src="../../cus/gis/css/images/printer.png";
                             printer_btn_sec.title=c;
                             printer_btn_sec.onclick = function(){
                             window.location.href("董龙文2014年第32周工作总结及下周工作计划（8 月 11 日- 8 月 15 日）.doc");
                             delTips(c,d);//移除右下角方案标签
                             $("img",$("#myTags")).each(function(){//移除右下角标签上打印机
                             if(printer_btn_sec.attr("title")==b){
                             printer_btn_sec.remove();
                             return false
                             }
                             });
                             };

                             $("#myTags").append(printer_btn_sec);
                             $("#myTags").append("	");*/
                            var b = d ? 'value="' + d + '"' : "";
                            a.append($("<a " + b + ' title="' + c + '" href="javascript:void(0);" ><span>' + c + "</span><em></em></a>"));
                            searchAjax(c, d, true);
                            //绑定添加tips删除
                            $("a em", a).bind("click", function () {
                                var c = $(this).parents("a"), b = c.attr("title"), d = c.attr("value");
                                delTips(b, d)
                            });
                            //显示打印图片
                            if ($("#printer_btn").css("display") == 'none') {
                                $("#printer_btn").show();
                                //div.style.border="1py solid red" ;    // 设置框粗细
                                $("#myTags").css({"border": "1px solid red"});
                            }
                            return true
                        };

                        delTips = function (b, c) {
                            if (!hasTips(b)) {
                                return false
                            }
                            $("a", a).each(function () {
                                var d = $(this);
                                if (d.attr("title") == b) {
                                    d.remove();
                                    return false
                                }
                            });
                            /* $("img",$("#myTags")).each(function(){//移除右下角标签上打印机
                             var d=$(this);
                             if(d.attr("title")==b){
                             d.remove();
                             return false
                             }
                             });*/
                            searchAjax(b, c, false);
                            return true
                        };

                        getTips = function () {
                            var b = [];
                            $("a", a).each(function () {
                                b.push($(this).attr("title"))
                            });
                            return b
                        };

                        getTipsId = function () {
                            var b = [];
                            $("a", a).each(function () {
                                b.push($(this).attr("value"))
                            });
                            return b
                        };

                        getTipsIdAndTag = function () {
                            var b = [];
                            $("a", a).each(function () {
                                b.push($(this).attr("value") + "##" + $(this).attr("title"))
                            });
                            return b
                        }

                    });
                    // 更新选中标签标签
                    $(function () {
                        setSelectTips();
                        //$('.plus-tag').append($('.plus-tag a'));
                    });
                    var searchAjax = function (name, id, isAdd) {
                        setSelectTips();
                    };
                    // 推荐标签
                    $(function () {
                        var str = ['解决方案', '解决方案']
                        $('.plus-tag-add a').click(function () {
                            var $this = $(this),
                                $con = $('#mycard-plus');

                            if ($this.hasClass('plus')) {
                                $this.removeClass('plus').text(str[0]);
                                $con.hide();
                            } else {
                                $this.addClass('plus').text(str[1]);
                                $con.show();
                            }
                        });
                        $('.default-tag a').bind('click', function () {
                            var $this = $(this),
                                name = $this.attr('title'),
                                id = $this.attr('value');
                            setTips(name, id);
                        });
                        // 更新高亮显示
                        setSelectTips = function () {
                            var arrName = getTips();
                            if (arrName.length) {
                                $('#myTags').show();
                            } else {
                                $('#myTags').hide();
                            }
                            $('.default-tag a').removeClass('selected');
                            $.each(arrName, function (index, name) {
                                $('.default-tag a').each(function () {
                                    var $this = $(this);
                                    if ($this.attr('title') == name) {
                                        $this.addClass('selected');
                                        return false;
                                    }
                                })
                            });
                        }

                    });
                    // 更换链接
                    (function () {
                        var $b = $('#change-tips'),
                            $d = $('.default-tag div'),
                            len = $d.length,
                            t = 'nowtips';
                        $b.click(function () {
                            var i = $d.index($('.default-tag .nowtips'));
                            i = (i + 1 < len) ? (i + 1) : 0;
                            $d.hide().removeClass(t);
                            $d.eq(i).show().addClass(t);
                        });
                        $d.eq(0).addClass(t);
                    })();

                    // map.centerAndZoom(point,seenView);

                }
            });
        return;

    });
    // moveMapByBound();
}
//添加普通标注
function addGeneralPoint(_lng, _lat, lid, _icon, content, title, width, height, markers_collecton, d, c, qyid) {
    var point = new BMap.Point(_lng, _lat);
    var jsonicon = {w: 21, h: 35, l: 0, t: 0, x: 6, lb: 5};
    var iconImg = createIcon(jsonicon, _icon);
    var marker = new BMap.Marker(point, {icon: iconImg});
    map.addOverlay(marker);
    markers_collecton.push(marker);
    marker.addEventListener("click", function () {
        if ($("#printer_btn").is(":visible")) {
            var a = $(".plus-tag");
            var b = d ? 'value="' + d + '"' : "";
            a.append($("<a " + b + ' title="' + c + '" href="javascript:void(0);" ><span>' + c + "</span><em></em></a>"));
            $("a em", a).bind("click", function () {//x绑定删除
                $("a", a).each(function () {
                    if ($(this).val() == d) {
                        $(this).remove();
                    }
                });
            });
        }
    });
    if (_icon == "label_zdwxy") {
        marker.addEventListener("click", function () {
            if (qyid != "") {
                $.getJSON(
                    "../../cxf/monitor/" + qyid + "", {"rand": Math.random()}, function (json) {
                        if (json != null && json.length > 0) {
                            if (/msie/.test(navigator.userAgent.toLowerCase())) {
                                var streamSvrIp = "";
                                var TextIP = "";
                                var TextName1 = "";
                                var TextPwd1 = "";
                                TextIP = json[0].ipAddress;
                                TextName1 = json[0].admin;
                                TextPwd1 = json[0].password;
                                content += '<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                                    '<iframe align="center" id="iframe_viedo" width="550" height="400" src="../../cus/gis/js/map/PlayViewDemo.htm?streamSvrIp=' + streamSvrIp + '&&TextIP=' + TextIP + '&&TextName1=' + TextName1 + '&&TextPwd1=' + TextPwd1 + '" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>' +
                                    '</div>';
                                width = 800;
                                openInfoW(_lng, _lat, title, width, height, content);
                            } else {
                                openInfoW(_lng, _lat, title, width, height, content);
                                alert("请选择IE浏览器!否则影响监控视频观看!");
                            }
                        } else {
                            openInfoW(_lng, _lat, title, width, height, content);
                        }
                    });
            }
        });
    }
    else {
        marker.addEventListener("mouseover", function () {
            openInfoW(_lng, _lat, title, width, height, content);
        });
        marker.addEventListener("mouseout", function () {
            closeInfoW();
        });
    }
}


function addSingleMonitorPoint(_lng, _lat, lid, _icon, content, title, width, height, markers_collecton, d, c, qyid) {
    var point = new BMap.Point(_lng, _lat);
    var jsonicon = {w: 21, h: 35, l: 0, t: 0, x: 6, lb: 5};
    var iconImg = createGifIcon(jsonicon, _icon);
    var marker = new BMap.Marker(point, {icon: iconImg});
    map.addOverlay(marker);
    markers_collecton.push(marker);
    marker.addEventListener("click", function () {
        if ($("#printer_btn").is(":visible")) {
            var a = $(".plus-tag");
            var b = d ? 'value="' + d + '"' : "";
            a.append($("<a " + b + ' title="' + c + '" href="javascript:void(0);" ><span>' + c + "</span><em></em></a>"));
            $("a em", a).bind("click", function () {//x绑定删除
                $("a", a).each(function () {
                    if ($(this).val() == d) {
                        $(this).remove();
                    }
                });
            });
        }
    });
    if (_icon == "label_db") {
        marker.addEventListener("click", function () {
            if (qyid != "") {
                $.getJSON(
                    "../../cxf/singleMonitor/" + qyid + "", {"rand": Math.random()}, function (json) {

                        if (json != null && json.length > 0) {
                            if (/msie/.test(navigator.userAgent.toLowerCase())) {
                                /*strName2 = GetQueryString("TextName2");
                                 strPwd2 = GetQueryString("Textpwd2");
                                 ChanNum2 = GetQueryString("SelectChan2");
                                 cameraID2 = GetQueryString("TextCameraID2");
                                 var streamSvrIp = GetQueryString("streamSvrIp");
                                 var streamSvrPort =GetQueryString("streamSvrPort");
                                 var deviceCode =  GetQueryString("deviceCode");
                                 var pagIP =GetQueryString("pagIP");
                                 var pagPort = GetQueryString("pagPort");*/
                                strName2 = json[0].admin;
                                strPwd2 = json[0].password
                                ChanNum2 = json[0].channel
                                cameraID2 = json[0].equipmentId
                                var streamSvrIp = json[0].streamingMediaIP
                                var streamSvrPort = json[0].streamingMediaPort
                                var deviceCode = json[0].equipmentNumber
                                var pagIP = json[0].pagIP
                                var pagPort = json[0].pagPort

                                content += '<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                                    '<iframe align="center" id="iframe_viedo" width="550" height="400" src="../../cus/gis/js/map/SinglePlayViewDemo.htm?TextName2=' + strName2
                                    + '&&Textpwd2=' + strPwd2
                                    + '&&SelectChan2=' + ChanNum2 + '&&TextCameraID2=' + cameraID2 + '&&streamSvrIp=' + streamSvrIp + '&&streamSvrPort=' + streamSvrPort
                                    + '&&deviceCode=' + deviceCode + '&&pagIP=' + pagIP + '&&pagPort=' + pagPort + '" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>' +
                                    '</div>';
                                width = 560;
                                openInfoW(_lng, _lat, title, width, height, content);
                            } else {
                                openInfoW(_lng, _lat, title, width, height, content);
                                alert("请选择IE浏览器!否则影响监控视频观看!");
                            }
                        } else {
                            openInfoW(_lng, _lat, title, width, height, content);
                        }
                    });
            }
        });
    }
    else {
        marker.addEventListener("mouseover", function () {
            openInfoW(_lng, _lat, title, width, height, content);
        });
        marker.addEventListener("mouseout", function () {
            closeInfoW();
        });
    }
}
//打开标注窗口
/*function openInfoW(responseJson,marker) {
 infowindow = new BMap.InfoWindow(getIWContent(responseJson));
 marker.openInfoWindow(infowindow);
 //图片加载完毕重绘infowindow
 document.getElementById('imgDemo').onload = function (){
 infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
 }
 }*/
function openInfoW(_lng, _lat, title, width, height, content) {
    //样式3
    infowindow = new BMapLib.SearchInfoWindow(map, content, {
        title: title, //标题
        //width: 590, //宽度
        //height: 250, //高度
        width: width, //宽度
        height: height, //高度
        panel: "panel", //检索结果面板
        enableAutoPan: true, //自动平移
        searchTypes: [
        ]
    });
    /* var point = new BMap.Point(_lng,_lat);
     map.centerAndZoom(point,seenView);*/
    infowindow.open(new BMap.Point(_lng, _lat));
}
function closeInfoW() {
    infowindow.close();
}

//--获取位置点的信息窗口内容
function getIWContent(responseJson) {
    var content = "<div style='padding-top:10px'>物资名称:<span style='color:green;'>" + responseJson.community_name + "</span></br>";
    content += "物资简介:<span style='color:green;'>" + responseJson.community_remak + "</span></br>";
    content += "</div>";
    return content;
}

//注定义图片
function createIcon(json, _icon) {
    var icon = new BMap.Icon("../../cus/gis/css/images/" + _icon + ".png", new BMap.Size(json.w, json.h), {imageOffset: new BMap.Size(-json.l, -json.t), infoWindowOffset: new BMap.Size(json.lb + 5, 1), offset: new BMap.Size(json.x, json.h)});
    return icon;
    /* var icon = new BMap.Icon("../../cus/gis/css/images/"+ _icon + ".png", new BMap.Size(json.w,json.h),{anchor: new BMap.Size(10, 0)})
     return icon;*/
}

function createGifIcon(json, _icon) {
    var icon = new BMap.Icon("../../cus/gis/css/images/" + _icon + ".gif", new BMap.Size(json.w, json.h), {imageOffset: new BMap.Size(-json.l, -json.t), infoWindowOffset: new BMap.Size(json.lb + 5, 1), offset: new BMap.Size(json.x, json.h)});
    return icon;
    /* var icon = new BMap.Icon("../../cus/gis/css/images/"+ _icon + ".png", new BMap.Size(json.w,json.h),{anchor: new BMap.Size(10, 0)})
     return icon;*/
}

//重新定义所有标注在可视范围内
function moveMapByBound() {
    var bound = [];
    for (var i = 0; i < markers.length; i++) {
        bound.push(markers[i].getPosition());
    }
    for (var i = 0; i < markers_zdwxy.length; i++) {
        bound.push(markers_zdwxy[i].getPosition());
    }
    for (var i = 0; i < markers_jydbz.length; i++) {
        bound.push(markers_jydbz[i].getPosition());
    }
    for (var i = 0; i < markers_zjbz.length; i++) {
        bound.push(markers_zjbz[i].getPosition());
    }
    for (var i = 0; i < markers_wzbj.length; i++) {
        alert(markers_wzbj[i].getPosition());
        bound.push(markers_wzbj[i].getPosition());
    }
    for (var i = 0; i < markers_dbbj.length; i++) {
        bound.push(markers_dbbj[i].getPosition());
    }
    map.setViewport(bound);
    if (map.getZoom() > 18)
        map.zoomTo(18);
}

//鼠标右件清空
function clearAll() {
    clearallmarker();
}

//清空所有覆盖物
function clearallmarker() {
    map.clearOverlays();
    //markers.splice(0);
    for (var mi = 0; mi < markers.length; mi++) {
        if (mi > current_danger_point_number) {
            map.removeOverlay(markers[mi]);
        } else {
            map.addOverlay(markers[mi]);
        }
    }
    current_danger_point_number = 0
    overlays.splice(0);//清空地图圆圈
    overlays.length = 0;
    markers_zdwxy.splice(0);//清空重大危险源
    markers_jydbz.splice(0);//清空救援队
    markers_zjbz.splice(0);//清空专家
    markers_wzbj.splice(0);//清空物资
    markers_dbbj.splice(0);//清空物资
    clearPrint();
}

/**
 * 清空重大危险源标注
 */
function clearall_zdwxy() {
    //清空地图已有重大危险源标注
    for (var mwi = 0; mwi < markers_zdwxy.length; mwi++) {
        map.removeOverlay(markers_zdwxy[mwi]);
        markers_zdwxy.remove(mwi);
    }
    // clearPrint();
}

/**
 * 清空企业专家标注
 */
function clearall_zj() {
    //清空地图已有企业专家标注
    for (var mwi = 0; mwi < markers_zjbz.length; mwi++) {
        map.removeOverlay(markers_zjbz[mwi]);
        markers_zjbz.remove(mwi);
    }
    //clearPrint();
}

/**
 * 清空救援队标注
 */
function clearall_jyd() {
    //清空地图已有救援队标注
    for (var mwi = 0; mwi < markers_jydbz.length; mwi++) {
        map.removeOverlay(markers_jydbz[mwi]);
        markers_jydbz.remove(mwi);
    }
    // clearPrint();
}

/**
 * 清空物资标注
 */
function clearall_wzbj() {
    //清空地图已有物资标注
    for (var mwi = 0; mwi < markers_wzbj.length; mwi++) {
        map.removeOverlay(markers_wzbj[mwi]);
        markers_wzbj.remove(mwi);
    }
    // clearPrint();
}
//地图右侧侧拉按钮
function showPanel() {
    if (isPanelShow == false) {
        isPanelShow = true;
        $("#showPanelBtn").css({marginRight: "230px" });
        $("#panelWrap").css("width", "230");
        $("#map").css({marginRight: "230px" });
        $("#showPanelBtn").html("隐藏检索结果信息<br/>>");
    } else {
        isPanelShow = false;
        $("#showPanelBtn").css({marginRight: "0px" });
        $("#panelWrap").css("width", "0");
        $("#map").css({marginRight: "0px" });
        $("#showPanelBtn").html("显示检索结果信息<br/><");
    }
}

//个性化检索
function searchAll(seachvalue) {
    if (seachvalue != "") {
        var search_radio = $('input:radio[name="search_radio"]:checked').val();
        if (search_radio == null) {
            alert("请选择其中一个查询条件!");
            return false;
        } else {
            rightList_search(seachvalue, search_radio);//1专家 2救援队3物资
        }
    } else {
        $("#showOverlayInfo").css("display", "");
        $("#panel").html("");
        alert("请输入检索value");
    }

}
//解决ie中右侧搜索非得离开输入框才好使
function immediately() {
    var element = document.getElementById("stylelist");
    if (/msie/.test(navigator.userAgent.toLowerCase())) {
        element.onpropertychange = webChange;
    }
    function webChange() {
        if (element.value) {
            searchAll(element.value);
        }
        ;
    }
}

//打印
function gotoprint() {
    // $("#print_container").jqprint();
    var reserve_arry = [];//企业预案
    var digital_arry = [];//监管机构预案
    var expert_arry = [];//专家
    var rescue_arry = [];//救援队
    var equipment_arry = [];//物资
    var res_dig = {"reserve": "", "digital": ""};//预案 集合
    var xls = {"reserve": "", "digital": "", "expert": "", "rescue": "", "equipment": ""};//物资专家救援队集合

    var reserve_str = "";//企业预案
    var digital_str = "";//监管机构预案
    var expert_str = "";//专家
    var rescue_str = "";//救援队
    var equipment_str = "";//物资
    $("a", $(".plus-tag")).each(function () {
        var d = $(this);
        // alert("获取选中值:"+d.attr("value"));
        var strs = new Array(); //定义一数组
        strs = d.attr("value").split(":"); //字符分割
        if (strs[0] == "reserve") {
            reserve_arry.push(strs[1]);
            reserve_str += strs[1] + "#";
        }
        if (strs[0] == "digital") {
            digital_arry.push(strs[1]);
            digital_str += strs[1] + "#";
        }
        if (strs[0] == "expert") {
            expert_arry.push(strs[1]);
            expert_str += strs[1] + "#";
        }
        if (strs[0] == "rescue") {
            rescue_arry.push(strs[1]);
            rescue_str += strs[1] + "#";
        }
        if (strs[0] == "equipment") {
            equipment_arry.push(strs[1]);
            equipment_str += strs[1] + "#";
        }
    });
    res_dig.reserve = reserve_arry;
    res_dig.digital = digital_arry;
    xls.reserve = reserve_arry;
    xls.digital = digital_arry;
    xls.expert = expert_arry;
    xls.rescue = rescue_arry;
    xls.equipment = equipment_arry;

    /* if(expert_arry.length != 0 || rescue_arry.length != 0 ||  equipment_arry.length != 0){
     $.ajax({
     url: "http://localhost:9191/cxf/accident/print",
     type: "post",
     data: JSON.stringify(xls),
     dataType: "json",
     contentType: "application/json; charset=utf-8",
     success: function(data){ }
     });
     }

     var parm = "reserve:"+reserve_str+"^digital:"+digital_str+"^expert:"+expert_str+"^rescue:"+rescue_str+"^equipment:"+equipment_str;*/

    if (reserve_arry.length != 0 || digital_arry.length != 0) {
        sendToServer(JSON.stringify(xls), "");
        //$.getJSON("../../cxf/accident/print/"+res_dig+"",{"rand":Math.random()},function(json){});
    }
    clearPrint();
}

/**
 * 右下打印角清空
 */
function clearPrint() {
    var a = $(".plus-tag");
    $("a", a).each(function () {
        $(this).remove();
    });
    $("#printer_btn").hide();
    $("#myTags").css({"border": ""});
}

//显示推荐方案
function goConfirm(parm, _lng, _lat, id, accident_id) {
    if (parm) {
        playStatus = false;
        distance = 0.5;//搜索范围500米
        $("#sg_btn").hide();
        $(".demo").show();
        //查询周围物资 500米半径圆
        var point = new BMap.Point(_lng, _lat);
        var circleFinveMiter = new BMap.Circle(point, 500);
        circleFinveMiter.setStrokeColor("red");
        circleFinveMiter.setFillOpacity(0);
        map.addOverlay(circleFinveMiter);
        overlays.push(circleFinveMiter);
        var bounds = getSquareBounds(circleFinveMiter.getCenter(), circleFinveMiter.getRadius() + circleFinveMiter.getRadius() * 0.36);
        var point_label = new BMap.Point(bounds.getNorthEast().lng, bounds.getSouthWest().lat);
        var opts = {
            position: point_label,    // 指定文本标注所在的地理位置
            offset: new BMap.Size(30, -30)    //设置文本偏移量
        }
        var label = new BMap.Label("搜索半径:" + Math.round(circleFinveMiter.getRadius()) + "米", opts);  // 创建文本标注对象
        label.setStyle({
            color: "red",
            fontSize: "12px",
            height: "20px",
            lineHeight: "20px",
            fontFamily: "微软雅黑"
        });
        map.addOverlay(label);
        //根据当前危险点位置的经纬度查询搜索500内的物质 并且标注
        /*
         alert("根据当前危险点位置的经纬度查询搜索500内的物质 并且标注");

         var responseJson ={"community_name":"吉林省达仁科技官方圈子","community_remak":"sdfsdfsd水电费水电费"};
         //var _lng  = 124.424072;
         var _lng  = 124.383333;
         var _lat  = 43.15072;
         //var _lat  = 43.140839;
         var lid  = 0;
         var _icon  = "dkred";
         var content = "<div style='float:left;width:240px;padding-top:10px'>ssdfs: <span style='color:green;'>aasdasdasd</span></br>";
         content += "</div>";
         addGeneralPoint(_lng,_lat,lid,_icon,content,"详情",500,400,markers_zdwxy);*/

        scope(_lng, _lat, id);
        // moveMapByBound();
    } else {
        playStatus = false;
        map.clearOverlays();
        /*map.clearOverlays();
         markers = [];
         overlays = [];
         */
        for (var i = 0; i < overlays.length; i++) {
            map.removeOverlay(overlays[i]);
            overlays.remove(i);
        }
        for (var di = 0; di < markers.length; di++) {
            var lng = markers[di].getPosition().lng;
            var lat = markers[di].getPosition().lat;

            if (_lng == lng && _lat == lat) {
                map.removeOverlay(markers[di]);
                markers.remove(di);
                // closeInfoW();
                infowindow_danger.close();
            }
            if (markers.length - 1 == di) {
                map.centerAndZoom(cityname, lev);
            }
        }
        overlays.splice(0);//清空地图圆圈
        markers_zdwxy.splice(0);//清空重大危险源
        markers_jydbz.splice(0);//清空救援队
        markers_zjbz.splice(0);//清空专家
        markers_wzbj.splice(0);//清空物资
        current_danger_point_number = current_danger_point_number - 1;
        //改变显示状态
        $.getJSON("../../cxf/accident/" + accident_id + "", {"rand": Math.random()}, function (json) {
        });

    }
    PlayStop();//停止预警声音
}

//右上角标签
function rightTab() {
    // 定义一个控件类，即function
    function BiaoZhuCustomerLocationControlgj() {
        // 设置默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
        //this.defaultOffset = new BMap.Size(-40, 30);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    BiaoZhuCustomerLocationControlgj.prototype = new BMap.Control();

    // 创建控件实例
    var BiaoZhuCustomerLocationControlgj = new BiaoZhuCustomerLocationControlgj();
    // 添加到地图当中
    map.addControl(BiaoZhuCustomerLocationControlgj);

    // 定义一个控件类,即function righTop
    function ZoomControl() {
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
        this.defaultOffset = new BMap.Size(5, 5);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function (map) {
        // 创建一个DOM元素
        // 创建一个DOM元素
        var div = document.createElement("div");
        // 添加按钮说明
        var drag_img = document.createElement("img");
        drag_img.id = "drag_btn";
        drag_img.name = "drag";
        drag_img.title = "拖动地图";
        drag_img.style.width = "50px";
        drag_img.style.height = "30px";
        drag_img.src = "../../cus/gis/css/images/drag.png";
        //	frist.style.display="none";//隐藏
        var circle_img = document.createElement("img");
        circle_img.id = "circle_btn";
        circle_img.name = "circle";
        circle_img.title = "区域资源搜索";
        circle_img.style.width = "50px";
        circle_img.style.height = "30px";
        circle_img.src = "../../cus/gis/css/images/circle.png";
        //   left.style.display="none";//隐藏
        var zdwx_img = document.createElement("img");
        zdwx_img.id = "zdwx_btn";
        zdwx_img.name = "zdwx";
        zdwx_img.title = "重大危险";
        zdwx_img.style.width = "50px";
        zdwx_img.style.height = "30px";
        zdwx_img.src = "../../cus/gis/css/images/zdwx.png";
        //  right.style.display="none";//隐藏
        var jyd_img = document.createElement("img");
        jyd_img.id = "jyd_btn";
        jyd_img.name = "jyd";
        jyd_img.title = "救援队";
        jyd_img.style.width = "50px";
        jyd_img.style.height = "30px";
        jyd_img.src = "../../cus/gis/css/images/jyd.png";
        // last.style.display="none";//隐藏
        var wz_img = document.createElement("img");
        wz_img.id = "wz_btn";
        wz_img.name = "wz";
        wz_img.title = "物资";
        wz_img.style.width = "50px";
        wz_img.style.height = "30px";
        wz_img.src = "../../cus/gis/css/images/wz.png";
        // viewgj.style.display="none";//隐藏

        var db_img = document.createElement("img");
        db_img.id = "db_btn";
        db_img.name = "db";
        db_img.title = "单兵";
        db_img.style.width = "50px";
        db_img.style.height = "30px";
        db_img.src = "../../cus/gis/css/images/db.png";
        // viewgj.style.display="none";//隐藏

        var zj_img = document.createElement("img");
        zj_img.id = "zj_btn";
        zj_img.name = "zj";
        zj_img.title = "专家";
        zj_img.style.width = "50px";
        zj_img.style.height = "30px";
        zj_img.src = "../../cus/gis/css/images/zj.png";
        // viewgj.style.display="none";//隐藏
        div.appendChild(drag_img);
        div.appendChild(circle_img);
        div.appendChild(db_img);
        div.appendChild(zdwx_img);
        div.appendChild(jyd_img);
        div.appendChild(zj_img);
        div.appendChild(wz_img);


        // 设置样式

        //div.style.backgroundColor = "red";
        // div.style.border="1py solid red" ;    // 设置框粗细
        //div.style.width = "40%";
        div.style.width = "18.5%";
        div.style.height = "30px";
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        $(function () {
            $('#drag_btn').bind({//拖拽
                click: function () {
                    isClickBtn = true;
                },
                mouseenter: function () {
                    $("#drag_btn").attr("src", "../../cus/gis/css/images/draged.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/draged.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    } else {
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                    }
                }
            });
            $('#circle_btn').bind({//圆形区域搜索
                click: function () {
                    isClickBtn = true;
                    //圆形收索
                    var options = {
                        renderOptions: {
                            map: map
                        },
                        onSearchComplete: function (results) {
                        }
                    };
                    var localSearch = new BMap.LocalSearch(map, options);

                    var drawingManager = new BMapLib.DrawingManager(map, {
                        isOpen: true, //是否开启绘制模式
                        enableDrawingTool: false, //是否显示工具栏
                        drawingToolOptions: {
                            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                            offset: new BMap.Size(5, 5), //偏离值
                            scale: 0.8, //工具栏缩放比例
                            drawingModes: [
                                BMAP_DRAWING_CIRCLE
                            ]
                        }
                    });
                    drawingManager.setDrawingMode(BMAP_DRAWING_CIRCLE);
                    var circle = null;
                    drawingManager.addEventListener('circlecomplete', function (e) {
                        circle = e;
                        circle.setFillOpacity(0);
                        overlays.push(circle);
                        var radius = parseInt(e.getRadius());
                        var center = e.getCenter();
                        drawingManager.close();
                        //初始化图标
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/draged.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        //localSearch.searchNearby('火车票代售点', center,radius,{customData:{databoxId:15611}});
                        distance = Math.round(circle.getRadius()) / 1000;//搜索范围500米
                        //alert("开始检索救援队!"+circle.getCenter().lng+"-------"+circle.getCenter().lat+"半径:"+Math.round(circle.getRadius()) +"米");
                        scope(circle.getCenter().lng, circle.getCenter().lat, 0);
                        var bounds = getSquareBounds(circle.getCenter(), circle.getRadius() + circle.getRadius() * 0.36);
                        var point_label = new BMap.Point(bounds.getNorthEast().lng, bounds.getSouthWest().lat);
                        var opts = {
                            position: point_label,    // 指定文本标注所在的地理位置
                            offset: new BMap.Size(30, -30)    //设置文本偏移量
                        }
                        var label = new BMap.Label("搜索半径:" + Math.round(circle.getRadius()) + "米", opts);  // 创建文本标注对象
                        label.setStyle({
                            color: "red",
                            fontSize: "12px",
                            height: "20px",
                            lineHeight: "20px",
                            fontFamily: "微软雅黑"
                        });
                        map.addOverlay(label);
                    });
                },
                mouseenter: function () {
                    $("#circle_btn").attr("src", "../../cus/gis/css/images/circled.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circled.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    } else {
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                    }
                }
            });
            $('#zdwx_btn').bind({//重大危险
                click: function () {
                    isClickBtn = true;
                    zdwxy();
                },
                mouseenter: function () {
                    $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwxed.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwxed.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    } else {
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                    }
                }
            });
            $('#jyd_btn').bind({//救援队
                click: function () {
                    isClickBtn = true;
                    jydbz();
                },
                mouseenter: function () {
                    $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyded.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyded.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    } else {
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyd.png");
                    }
                }
            });
            $('#zj_btn').bind({//专家
                click: function () {
                    isClickBtn = true;
                    zjbz();
                },
                mouseenter: function () {
                    $("#zj_btn").attr("src", "../../cus/gis/css/images/zjed.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zjed.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#jyd_btn").attr("src", "../../cus/gis/css/images/jyd.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    } else {
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                    }
                }
            });
            $('#wz_btn').bind({//物资
                click: function () {
                    isClickBtn = true;
                    wzbj();
                },
                mouseenter: function () {
                    $("#wz_btn").attr("src", "../../cus/gis/css/images/wzed.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wzed.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");

                    } else {
                        $("#wz_btn").attr("src", "../../cus/gis/css/images/wz.png");
                    }
                }
            });

            $('#db_btn').bind({//单兵设备
                click: function () {
                    isClickBtn = true;
                    dbzk();
                },
                mouseenter: function () {
                    $("#db_btn").attr("src", "../../cus/gis/css/images/dbed.png");
                },
                mouseleave: function () {
                    if (isClickBtn) {
                        $("#db_btn").attr("src", "../../cus/gis/css/images/dbed.png");
                        $("#drag_btn").attr("src", "../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src", "../../cus/gis/css/images/circle.png");
                        $("#zj_btn").attr("src", "../../cus/gis/css/images/zj.png");
                        $("#zdwx_btn").attr("src", "../../cus/gis/css/images/zdwx.png");
                    } else {
                        $("#db_btn").attr("src", "../../cus/gis/css/images/db.png");
                    }
                }
            });
        });
        // 将DOM元素返回
        return div;
    }
    // 创建控件
    var myZoomCtrl = new ZoomControl();
    // 添加到地图当中
    map.addControl(myZoomCtrl);

    // 定义一个控件类,即function BOTTOM_tag
    function TagZoomControl() {
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
        this.defaultOffset = new BMap.Size(35, 10);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    TagZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    TagZoomControl.prototype.initialize = function (map) {

        /*        var	printer_btn = document.createElement("img");
         printer_btn.id = "printer_btn";
         printer_btn.name = "printer_btn";
         printer_btn.style.width="16px";
         printer_btn.style.height="16px";
         printer_btn.src="../../cus/gis/css/images/printer.png";
         printer_btn.style.display="none";//隐藏*/

        var div = document.createElement("div");
        div.id = "myTags";
        div.name = "myTags";
        //div.style.border="1py solid red" ;    // 设置框粗细
        //div.appendChild(printer_btn);
        // 设置样式
        // div.style.backgroundColor = "red";
        //div.style.width="83.5%";
        //div.style.width="58%";
        //div.style.height="30px";
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        $(function () {
            $("#myTags").addClass('plus-tag');    //添加css样式
            $(".plus-tag").append($('<button id = "printer_btn" style="display:none" title="打 印" href="javascript:void(0);"><span>打 印</span><em></em></button>'));
            $("#myTags").addClass('tagbtn');    //添加css样式
            $("#myTags").addClass('clearfix');    //添加css样式
            $('#printer_btn').bind({
                click: function () {
                    gotoprint();
                }
            });
        });
        // 将DOM元素返回
        return div;
    }
    // 创建控件
    var tagMyZoomCtrl = new TagZoomControl();
    // 添加到地图当中
    map.addControl(tagMyZoomCtrl);


    //右键菜单
    var contextMenu = new BMap.ContextMenu();
    var txtMenuItem = [
        {
            text: '还原放大地图',
            callback: function () {
                map.zoomTo(15);
            }
        },
        {
            text: '清空全部标注',
            callback: function () {
                clearAll();
            }
        },
        {
            text: '在此添加标注',
            callback: function (p) {
                var marker = new BMap.Marker(p), px = map.pointToPixel(p);
                var streamSvrIp = "222.163.103.198";
                var TextIP = "58.244.242.6";
                var TextName1 = "admin";
                var TextPwd1 = "12345";

                var _icon = "dkred";
                var point = new BMap.Point(marker.getPosition().lng, marker.getPosition().lat);
                var jsonicon = {w: 21, h: 35, l: 0, t: 0, x: 6, lb: 5};
                var iconImg = createIcon(jsonicon, _icon);
                var marker = new BMap.Marker(point, {icon: iconImg});
                map.addOverlay(marker);
                markers.push(marker);
                current_danger_point_number++;
                marker.addEventListener("click", function () {
                    if (/msie/.test(navigator.userAgent.toLowerCase())) {
                        map.centerAndZoom(point, seenView);
                        //信息窗口的内容定义
                        var content = '<div style="float:left; width:250px;margin:0;line-height:20px;padding:2px;">' +
                            '地址：北京市海淀区上地十街10号<br/>电话：(010)59928888<br/>简介：百度大厦位于北京市海淀区西二旗地铁站附近，为百度公司综合研发及办公总部。' +
                            '</div>' +
                            '<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                            '<iframe align="center" width="550" height="360" src="js/map/PlayViewDemo.htm?streamSvrIp=' + streamSvrIp + '&&TextIP=' + TextIP + '&&TextName1=' + TextName1 + '&&TextPwd1=' + TextPwd1 + '" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>' +
                            '</div>';
                        //样式3
                        var infowindow = new BMapLib.SearchInfoWindow(map, content, {
                            title: "隐患详情", //标题
                            //width: 590, //宽度
                            //height: 250, //高度
                            width: 820, //宽度
                            height: 360, //高度
                            panel: "panel", //检索结果面板
                            enableAutoPan: true, //自动平移
                            searchTypes: [
                            ]
                        });
                        infowindow.open(new BMap.Point(marker.getPosition().lng, marker.getPosition().lat));
                        return;
                    } else {
                        alert("请选择IE浏览器!否则影响监控视频观看!");
                        return;
                    }
                });
                moveMapByBound();
            }
        }
    ];
    for (var i = 0; i < txtMenuItem.length; i++) {
        contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
        if (i == 1 || i == 3) {
            contextMenu.addSeparator();
        }
    }
    map.addContextMenu(contextMenu);
}

/**
 * 绑定回车
 * @param event
 */
function enterHandler(event) {
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    if (keyCode == 13) {
        searchAll($("#stylelist").val());
    }
}
//地图警告音
$(function () {
    $("#jplayer").jPlayer({
        swfPath: "../../cus/gis/js/jq/Jplayer.swf",
        ready: function () {
            $(this).jPlayer("setMedia", {
                mp3: "../../cus/gis/js/jq/message.mp3"
            });
        },
        ended: function (event) {
            $(this).jPlayer("play");
        },
        supplied: "mp3"
    });
});
function PlaySound() {
    $("#jplayer").jPlayer('play');
    return true;
}
function PlayStop() {
    $("#jplayer").jPlayer("stop");
    return true;
}

/**
 * 得到圆的内接正方形bounds
 * @param {Point} centerPoi 圆形范围的圆心
 * @param {Number} r 圆形范围的半径
 * @return 无返回值
 */
function getSquareBounds(centerPoi, r) {
    var a = Math.sqrt(2) * r; //正方形边长

    mPoi = getMecator(centerPoi);
    var x0 = mPoi.x, y0 = mPoi.y;

    var x1 = x0 + a / 2 , y1 = y0 + a / 2;//东北点
    var x2 = x0 - a / 2 , y2 = y0 - a / 2;//西南点

    var ne = getPoi(new BMap.Pixel(x1, y1)), sw = getPoi(new BMap.Pixel(x2, y2));
    return new BMap.Bounds(sw, ne);

}
//根据球面坐标获得平面坐标。
function getMecator(poi) {
    return map.getMapType().getProjection().lngLatToPoint(poi);
}
//根据平面坐标获得球面坐标。
function getPoi(mecator) {
    return map.getMapType().getProjection().pointToLngLat(mecator);
}

Array.prototype.remove = function (index) {
    if (isNaN(index) || index >= this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[index]) {
            this[n++] = this[i];
        }
    }
    this.length -= 1;
};
