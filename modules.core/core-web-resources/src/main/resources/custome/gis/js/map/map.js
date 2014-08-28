/**
 * dlw
 */
//--地图全局变量
var infocontent = " ";

var markers = [];

var infowindow;

var openWindowMarket;

var geoAddress = "";

var map;

var image = 'beachflag.png';

var maptype = "";

var nowPositon="";

var index=-1;

var trackcurrnetindex=-1;

var geocoder;

var cityname ="四平市";
var lev =13;
var seenView = 18;
var current_danger_point_number=-1;
var overlays = [];
var isPanelShow = false;
var isClickBtn = false;

var searchAjax=function(){};
var setSelectTips=function(){};
var hasTips=function(){};
var isMaxTips=function(){};
var setTips=function(){};
var delTips=function(){};
var getTips=function(){};
var getTipsId=function(){};
var getTipsIdAndTag=function(){};
var G_tocard_maxTips=30;

//--初始化地图
function initialize(lng,lat,cityname,lev,contenid) {
    map = new BMap.Map(contenid);//在百度地图容器中创建一个地图
    if(cityname){
        map.centerAndZoom(cityname,lev);
    }else{
        var point = new BMap.Point(lng,lat);//定义一个中心点坐标
        map.centerAndZoom(point,lev);//设定地图的中心点和坐标并将地图显示在地图容器中
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
    var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
    map.addControl(ctrl_sca);
    rightTab();//地图表面控件
    return true;
}

//添加危险点
function addDangerPoint(_lng,_lat,lid,_icon,responseJson) {
    //样式3宽高
    var infowindow_width =820;
    var infowindow_height=360;

    var streamSvrIp ="";
    var TextIP =responseJson.videoLink;
    var TextName1 ="";
    var TextPwd1 ="";
    /**
     var _lng =124.384483;
     var _lat =43.151036;
     var _icon="dkred";
     **/
    var point = new BMap.Point(_lng,_lat);
    var jsonicon ={w:21,h:35,l:0,t:0,x:6,lb:5};
    var iconImg = createIcon(jsonicon,_icon);
    var marker = new BMap.Marker(point,{icon:iconImg});
    map.addOverlay(marker);
    markers.push(marker);
    current_danger_point_number++;
    marker.addEventListener("click",function(){
        if( /msie/.test(navigator.userAgent.toLowerCase())){
                //信息窗口的内容定义
                var content = '<div style="float:left; width:250px;margin:0;line-height:20px;padding:2px;">';
                    content+=  '事故标题:'+responseJson.accidentTitle+'<br/>';
                    content+=  '事故类别:'+responseJson.accidentType+'<br/>';
                    content+=  '事故级别:'+responseJson.accidentLevel+'<br/>';
                    content+=  '事故发生地点：'+responseJson.place+'<br/>';
                    content+=  '详细地点：'+responseJson.detailsPlace+'<br/>';
                    content+=  '事故单位：'+responseJson.qymc+'<br/>';
                    content+=  '经办人:'+responseJson.operator+'<br/>';
                    content+=  '经办人电话:'+responseJson.operatorPhone+'<br/>';
                    content+='<div> <input name="" type="button" style="margin-right:10px;" value="确认" onClick="goConfirm(true,'+_lng+','+_lat+')"/> <input name="" type="button" value="解除" onClick="goConfirm(false,0,0)"/></div>'+
                    '<div class="demo">'+
                    '<div class="plus-tag-add">' +
                    '<form id="" action="" class="login">' +
                    '<ul class="Form FancyForm">' +
                    '<li>' +
                    '<a href="javascript:void(0);">推荐解决方案</a>' +
                    '</li>' +
                    '</ul>' +
                    '</form>' +
                    '</div>' +

                    '<div id="mycard-plus" style="display:none;">' +
                    '<div class="default-tag tagbtn">' +
                    '<div class="clearfix">' +
                    '<a value="1" title="互联网" href="javascript:void(0);"><span>互联网</span><em></em></a>' +
                    '<a value="2" title="移动互联网" href="javascript:void(0);"><span>移动互联网</span><em></em></a>' +
                    '<a value="3" title="it" href="javascript:void(0);"><span>it</span><em></em></a>' +
                    '<a value="4" title="电子商务" href="javascript:void(0);"><span>电子商务</span><em></em></a>' +
                    '<a value="5" title="广告" href="javascript:void(0);"><span>广告</span><em></em></a>' +
                    '<a value="6" title="网络编辑" href="javascript:void(0);"><span>网络编辑</span><em></em></a>' +
                    '</div>' +
                    '<div class="clearfix" style="display:none;"><a value="-1" title="媒体" href="javascript:void(0);"><span>媒体</span><em></em></a></div>' +
                    '<div class="clearfix" style="display:none;"><a value="-1" title="网络营销" href="javascript:void(0);"><span>网络营销</span><em></em></a></div>' +
                    '</div>' +
                    '<div align="right"><a href="javascript:void(0);" id="change-tips" style="color:#3366cc;display:none">换一换</a></div>' +
                    '</div>' +
                    '</div>'+
                    '</div>';
                if(TextIP!=null && TextIP!=""){
                    content+='<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                        '<iframe align="center" width="550" height="360" src="../../cus/gis/js/map/PlayViewDemo.htm?streamSvrIp='+streamSvrIp+'&&TextIP='+TextIP+'&&TextName1='+TextName1+'&&TextPwd1='+TextPwd1+'" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>'+
                        '</div>';
                }else{
                    infowindow_width=infowindow_width-550;
                }

                //样式3
                var infowindow = new BMapLib.SearchInfoWindow(map, content, {
                    title: "隐患详情", //标题
                    //width: 590, //宽度
                    //height: 250, //高度
                    width: 820, //宽度
                    height: 360, //高度
                    panel : "panel", //检索结果面板
                    enableAutoPan : true, //自动平移
                    searchTypes :[
                    ]
                });
                infowindow.open(new BMap.Point(marker.getPosition().lng,marker.getPosition().lat));

                //标签脚本
                $(function(){

                    var a=$(".plus-tag");

                    hasTips=function(b){
                        var d=$("a",a),c=false;
                        d.each(function(){
                            if($(this).attr("title")==b){
                                c=true;
                                return false
                            }
                        });
                        return c
                    };

                    isMaxTips=function(){
                        return
                        $("a",a).length>=G_tocard_maxTips
                    };

                    setTips=function(c,d){
                        if(hasTips(c)){
                            return false
                        }if(isMaxTips()){
                            alert("最多添加"+G_tocard_maxTips+"个标签！");
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
                        var b=d?'value="'+d+'"':"";
                        a.append($("<a "+b+' title="'+c+'" href="javascript:void(0);" ><span>'+c+"</span><em></em></a>"));
                        searchAjax(c,d,true);
                        //绑定添加tips删除
                        $("a em",a).bind("click",function(){
                            var c=$(this).parents("a"),b=c.attr("title"),d=c.attr("value");
                            delTips(b,d)
                        });
                        //显示打印图片
                        if($("#printer_btn").css("display") == 'none' ){
                           $("#printer_btn").show();
                            //div.style.border="1py solid red" ;    // 设置框粗细
                            $("#myTags").css({"border":"1px solid red"});
                        }
                        return true
                    };

                    delTips=function(b,c){
                        if(!hasTips(b)){
                            return false
                        }
                        $("a",a).each(function(){
                            var d=$(this);
                            if(d.attr("title")==b){
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
                        searchAjax(b,c,false);
                        return true
                    };

                    getTips=function(){
                        var b=[];
                        $("a",a).each(function(){
                            b.push($(this).attr("title"))
                        });
                        return b
                    };

                    getTipsId=function(){
                        var b=[];
                        $("a",a).each(function(){
                            b.push($(this).attr("value"))
                        });
                        return b
                    };

                    getTipsIdAndTag=function(){
                        var b=[];
                        $("a",a).each(function(){
                            b.push($(this).attr("value")+"##"+$(this).attr("title"))
                        });
                        return b
                    }

                });
                // 更新选中标签标签
                $(function(){
                    setSelectTips();
                    //$('.plus-tag').append($('.plus-tag a'));
                });
                var searchAjax = function(name, id, isAdd){
                    setSelectTips();
                };
                // 推荐标签
                $(function(){
                    var str = ['推荐解决方案', '推荐解决方案']
                    $('.plus-tag-add a').click(function(){
                        var $this = $(this),
                            $con = $('#mycard-plus');

                        if($this.hasClass('plus')){
                            $this.removeClass('plus').text(str[0]);
                            $con.hide();
                        }else{
                            $this.addClass('plus').text(str[1]);
                            $con.show();
                        }
                    });
                    $('.default-tag a').bind('click', function(){
                        var $this = $(this),
                            name = $this.attr('title'),
                            id = $this.attr('value');
                        setTips(name, id);
                    });
                    // 更新高亮显示
                    setSelectTips = function(){
                        var arrName = getTips();
                        if(arrName.length){
                            $('#myTags').show();
                        }else{
                            $('#myTags').hide();
                        }
                        $('.default-tag a').removeClass('selected');
                        $.each(arrName, function(index,name){
                            $('.default-tag a').each(function(){
                                var $this = $(this);
                                if($this.attr('title') == name){
                                    $this.addClass('selected');
                                    return false;
                                }
                            })
                        });
                    }

                });
                // 更换链接
                (function(){
                    var $b = $('#change-tips'),
                        $d = $('.default-tag div'),
                        len = $d.length,
                        t = 'nowtips';
                    $b.click(function(){
                        var i = $d.index($('.default-tag .nowtips'));
                        i = (i+1 < len) ? (i+1) : 0;
                        $d.hide().removeClass(t);
                        $d.eq(i).show().addClass(t);
                    });
                    $d.eq(0).addClass(t);
                })();

                map.centerAndZoom(point,seenView);
                return;
            }else{
        		alert("请选择IE浏览器!否则影响监控视频观看!");
        		return;
        	}
    });
   // moveMapByBound();
}
//添加普通标注
function addGeneralPoint(_lng,_lat,lid,_icon,content,title,width,height,markers_collecton) {

    var point = new BMap.Point(_lng,_lat);
    var jsonicon ={w:21,h:35,l:0,t:0,x:6,lb:5};
    var iconImg = createIcon(jsonicon,_icon);
    var marker = new BMap.Marker(point,{icon:iconImg});

    map.addOverlay(marker);
    //markers.push(marker);
    markers_collecton.push(marker);
    marker.addEventListener("mouseover",function(){
        openInfoW(_lng,_lat,title,width,height,content);
    });
    marker.addEventListener("mouseout",function(){
        closeInfoW();
    });
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
function openInfoW(_lng,_lat,title,width,height,content) {
    //样式3
    infowindow = new BMapLib.SearchInfoWindow(map, content, {
        title: title, //标题
        //width: 590, //宽度
        //height: 250, //高度
        width: width, //宽度
        height: height, //高度
        panel : "panel", //检索结果面板
        enableAutoPan : true, //自动平移
        searchTypes :[
        ]
    });
   /* var point = new BMap.Point(_lng,_lat);
    map.centerAndZoom(point,seenView);*/
    infowindow.open(new BMap.Point(_lng,_lat));
}
function closeInfoW() {
    infowindow.close();
}

//--获取位置点的信息窗口内容
function getIWContent(responseJson) {
    var content = "<div style='padding-top:10px'>物资名称:<span style='color:green;'>"+responseJson.community_name+"</span></br>";
    content += "物资简介:<span style='color:green;'>"+responseJson.community_remak+"</span></br>";
    content += "</div>";
    return content;
}

//注定义图片
function createIcon(json,_icon){
    var icon = new BMap.Icon("../../cus/gis/css/images/"+ _icon + ".png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
    return icon;
}

//重新定义所有标注在可视范围内
function moveMapByBound() {
    var bound = [];
    for(var i = 0;i<markers.length;i++) {
        bound.push(markers[i].getPosition());
    }
    for(var i = 0;i<markers_zdwxy.length;i++) {
        bound.push(markers_zdwxy[i].getPosition());
    }
    for(var i = 0;i<markers_jydbz.length;i++) {
        bound.push(markers_jydbz[i].getPosition());
    }
    for(var i = 0;i<markers_zjbz.length;i++) {
        bound.push(markers_zjbz[i].getPosition());
    }
    for(var i = 0;i<markers_wzbj.length;i++) {
        bound.push(markers_wzbj[i].getPosition());
    }
    map.setViewport(bound);

    if(map.getZoom()>18)
        map.zoomTo(18);

}

//鼠标右件清空
function clearAll() {
    for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    for(var i = 0;i<markers.length;i++) {
        if(i>current_danger_point_number){
            map.removeOverlay(markers[i]);
        }
    }
    for(var i = 0;i<markers_zdwxy.length;i++) {
        map.removeOverlay(markers_zdwxy[i]);
    }
    for(var i = 0;i<markers_jydbz.length;i++) {
        map.removeOverlay(markers_jydbz[i]);
    }
    for(var i = 0;i<markers_zjbz.length;i++) {
        map.removeOverlay(markers_zjbz[i]);
    }
    for(var i = 0;i<markers_wzbj.length;i++) {
        map.removeOverlay(markers_wzbj[i]);
    }
    overlays.length = 0
}

//清空所有覆盖物
function clearallmarker() {
    map.clearOverlays();
    markers.splice(0);
    overlays.splice(0);//清空地图圆圈
    markers_zdwxy.splice(0);//清空重大危险源
    markers_jydbz.splice(0);//清空救援队
    markers_zjbz.splice(0);//清空专家
    markers_wzbj.splice(0);//清空物资
}
//地图右侧侧拉按钮
function showPanel(){
    if (isPanelShow == false) {
        isPanelShow = true;
        $("#showPanelBtn").css("right","230");
        $("#panelWrap").css("width","230");
        $("#map").css("marginRight","230");
        $("#showPanelBtn").html("隐藏绘制结果信息<br/>>");
    } else {
        isPanelShow = false;
        $("#showPanelBtn").css("right","0");
        $("#panelWrap").css("width","0");
        $("#map").css("marginRight","0");
        $("#showPanelBtn").html("显示绘制结果信息<br/><");
    }
}

//个性化检索
function searchAll(seachvalue){
    if(seachvalue!=""){
        var result = "";
        result = "<p>";
        result +="检索key:"+seachvalue;
        result += "</p>";
        $("#showOverlayInfo").css("display","none");
        $("#panel").html(result); //将绘制的覆盖物信息结果输出到
    }else{
        $("#showOverlayInfo").css("display","");
        $("#panel").html("");
        alert("请输入检索value");
    }

}
//解决ie中右侧搜索非得离开输入框才好使
function immediately(){
    var element = document.getElementById("stylelist");
    if( /msie/.test(navigator.userAgent.toLowerCase())){
        element.onpropertychange = webChange;
    }
    function webChange(){
        if(element.value){searchAll(element.value);};
    }
}

//打印
function gotoprint(){
   // $("#print_container").jqprint();
    $("a",$(".plus-tag")).each(function(){
        var d=$(this);
        alert("获取选中值:"+d.attr("value"));
    });
}

//显示推荐方案
function goConfirm(parm,_lng,_lat){
    if(parm){
        $(".demo").show();
        //查询周围物资 500米半径圆
        var point = new BMap.Point(_lng,_lat);
        var circleFinveMiter = new BMap.Circle(point,500);
        circleFinveMiter.setStrokeColor("red");
        circleFinveMiter.setFillOpacity(0);
        map.addOverlay(circleFinveMiter);
        overlays.push(circleFinveMiter);
        //根据当前危险点位置的经纬度查询搜索500内的物质 并且标注

        alert("根据当前危险点位置的经纬度查询搜索500内的物质 并且标注");

        responseJson ={"community_name":"吉林省达仁科技官方圈子","community_remak":"sdfsdfsd水电费水电费"};
        //var _lng  = 124.424072;
        var _lng  = 124.383333;
        var _lat  = 43.15072;
        //var _lat  = 43.140839;
        var lid  = 0;
        var _icon  = "dkred";
        addGeneralPoint(_lng,_lat,lid,_icon,responseJson);

        moveMapByBound();
    }else{
        /*map.clearOverlays();
        markers = [];*/
        overlays = [];
        for(var i = 0; i < overlays.length; i++){
            map.removeOverlay(overlays[i]);
        }
        for(var i = 0;i<markers.length;i++) {
            if(_lng == lng && _lat == lat){
                map.removeOverlay(markers[i]);
            }
            if(markers.length==1){
                map.centerAndZoom(cityname,lev);
            }
        }

    }
}

//右上角标签
function rightTab(){
    // 定义一个控件类，即function
    function BiaoZhuCustomerLocationControlgj(){
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
    function ZoomControl(){
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
        this.defaultOffset = new BMap.Size(5, 5);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function(map){
        // 创建一个DOM元素
        // 创建一个DOM元素
        var div = document.createElement("div");
        // 添加按钮说明
        var drag_img = document.createElement("img");
        drag_img.id = "drag_btn";
        drag_img.name = "drag";
        drag_img.title="拖动地图";
        drag_img.style.width="50px";
        drag_img.style.height="30px";
        drag_img.src="../../cus/gis/css/images/drag.png";
        //	frist.style.display="none";//隐藏
        var circle_img = document.createElement("img");
        circle_img.id = "circle_btn";
        circle_img.name = "circle";
        circle_img.title="区域资源搜索";
        circle_img.style.width="50px";
        circle_img.style.height="30px";
        circle_img.src="../../cus/gis/css/images/circle.png";
        //   left.style.display="none";//隐藏
        var	zdwx_img = document.createElement("img");
        zdwx_img.id = "zdwx_btn";
        zdwx_img.name = "zdwx";
        zdwx_img.title="重大危险";
        zdwx_img.style.width="50px";
        zdwx_img.style.height="30px";
        zdwx_img.src="../../cus/gis/css/images/zdwx.png";
        //  right.style.display="none";//隐藏
        var	jyd_img = document.createElement("img");
        jyd_img.id = "jyd_btn";
        jyd_img.name = "jyd";
        jyd_img.title="救援队";
        jyd_img.style.width="50px";
        jyd_img.style.height="30px";
        jyd_img.src="../../cus/gis/css/images/jyd.png";
        // last.style.display="none";//隐藏
        var	wz_img = document.createElement("img");
        wz_img.id = "wz_btn";
        wz_img.name = "wz";
        wz_img.title="物资";
        wz_img.style.width="50px";
        wz_img.style.height="30px";
        wz_img.src="../../cus/gis/css/images/wz.png";
        // viewgj.style.display="none";//隐藏
        var	zj_img = document.createElement("img");
        zj_img.id = "zj_btn";
        zj_img.name = "zj";
        zj_img.title="专家";
        zj_img.style.width="50px";
        zj_img.style.height="30px";
        zj_img.src="../../cus/gis/css/images/zj.png";
        // viewgj.style.display="none";//隐藏
        div.appendChild(drag_img);
        div.appendChild(circle_img);
        div.appendChild(zdwx_img);
        div.appendChild(jyd_img);
        div.appendChild(zj_img);
        div.appendChild(wz_img);

        // 设置样式

        //div.style.backgroundColor = "red";
       // div.style.border="1py solid red" ;    // 设置框粗细
        div.style.width="18.5%";
        //div.style.width="58%";
        div.style.height="30px";
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        $(function () {
            $('#drag_btn').bind({//拖拽
                click: function() {
                    isClickBtn = true;
                },
                mouseenter: function() {
                    $("#drag_btn").attr("src","../../cus/gis/css/images/draged.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#drag_btn").attr("src","../../cus/gis/css/images/draged.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wz.png");
                    }else{
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                    }
                }
            });
            $('#circle_btn').bind({//圆形区域搜索
                click: function() {
                    isClickBtn = true;
                    //圆形收索
                    var options = {
                        renderOptions: {
                            map: map
                        },
                        onSearchComplete: function(results){
                        }
                    };
                    var localSearch = new BMap.LocalSearch(map,options);

                    var drawingManager = new BMapLib.DrawingManager(map, {
                        isOpen: true, //是否开启绘制模式
                        enableDrawingTool: false, //是否显示工具栏
                        drawingToolOptions: {
                            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                            offset: new BMap.Size(5, 5), //偏离值
                            scale: 0.8, //工具栏缩放比例
                            drawingModes : [
                                BMAP_DRAWING_CIRCLE
                            ]
                        }
                    });
                    drawingManager.setDrawingMode(BMAP_DRAWING_CIRCLE);
                    var circle = null;
                    drawingManager.addEventListener('circlecomplete', function(e) {
                        circle = e;
                        circle.setFillOpacity(0);
                        overlays.push(circle);
                        var radius= parseInt(e.getRadius());
                        var center= e.getCenter();
                        drawingManager.close();
                        //初始化图标
                        $("#drag_btn").attr("src","../../cus/gis/css/images/draged.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        //localSearch.searchNearby('火车票代售点', center,radius,{customData:{databoxId:15611}});
                        alert("开始检索救援队!"+circle.getCenter().lng+"-------"+circle.getCenter().lat+"半径:"+Math.round(circle.getRadius()) +"米");
                    });
                },
                mouseenter: function() {
                    $("#circle_btn").attr("src","../../cus/gis/css/images/circled.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circled.png");
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wz.png");
                    }else{
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                    }
                }
            });
            $('#zdwx_btn').bind({//重大危险
                click: function() {
                    isClickBtn = true;
                    zdwxy();
                },
                mouseenter: function() {
                    $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwxed.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwxed.png");
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyd.png");
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wz.png");
                    }else{
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");
                    }
                }
            });
            $('#jyd_btn').bind({//救援队
                click: function() {
                    isClickBtn = true;
                    jydbz();
                },
                mouseenter: function() {
                    $("#jyd_btn").attr("src","../../cus/gis/css/images/jyded.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyded.png");
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wz.png");
                    }else{
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyd.png");
                    }
                }
            });
            $('#zj_btn').bind({//专家
                click: function() {
                    isClickBtn = true;
                    zjbz();
                },
                mouseenter: function() {
                    $("#zj_btn").attr("src","../../cus/gis/css/images/zjed.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zjed.png");
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                        $("#jyd_btn").attr("src","../../cus/gis/css/images/jyd.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wz.png");
                    }else{
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                    }
                }
            });
            $('#wz_btn').bind({//物资
                click: function() {
                    isClickBtn = true;
                    wzbj();
                },
                mouseenter: function() {
                    $("#wz_btn").attr("src","../../cus/gis/css/images/wzed.png");
                },
                mouseleave: function() {
                    if(isClickBtn){
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wzed.png");
                        $("#drag_btn").attr("src","../../cus/gis/css/images/drag.png");
                        $("#circle_btn").attr("src","../../cus/gis/css/images/circle.png");
                        $("#zj_btn").attr("src","../../cus/gis/css/images/zj.png");
                        $("#zdwx_btn").attr("src","../../cus/gis/css/images/zdwx.png");

                    }else{
                        $("#wz_btn").attr("src","../../cus/gis/css/images/wzed.png");
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
    function TagZoomControl(){
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
        this.defaultOffset = new BMap.Size(35, 10);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    TagZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    TagZoomControl.prototype.initialize = function(map){

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
                click: function() {
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
            text:'还原放大地图',
            callback:function(){
                map.zoomTo(15);
            }
        },
        {
            text:'清空全部标注',
            callback:function(){
                clearAll();
            }
        },
        {
            text:'在此添加标注',
            callback:function(p){
                var marker = new BMap.Marker(p), px = map.pointToPixel(p);
                var streamSvrIp ="222.163.103.198";
                var TextIP ="58.244.242.6";
                var TextName1 ="admin";
                var TextPwd1 ="12345";

                var _icon="dkred";
                var point = new BMap.Point(marker.getPosition().lng,marker.getPosition().lat);
                var jsonicon ={w:21,h:35,l:0,t:0,x:6,lb:5};
                var iconImg = createIcon(jsonicon,_icon);
                var marker = new BMap.Marker(point,{icon:iconImg});
                map.addOverlay(marker);
                markers.push(marker);
                current_danger_point_number++;
                marker.addEventListener("click",function(){
                    if( /msie/.test(navigator.userAgent.toLowerCase())){
                        map.centerAndZoom(point,seenView);
                        //信息窗口的内容定义
                        var content = '<div style="float:left; width:250px;margin:0;line-height:20px;padding:2px;">' +
                            '地址：北京市海淀区上地十街10号<br/>电话：(010)59928888<br/>简介：百度大厦位于北京市海淀区西二旗地铁站附近，为百度公司综合研发及办公总部。' +
                            '</div>'+
                            '<div style="float:left;width:290px;margin:0;line-height:20px;padding:2px;">' +
                            '<iframe align="center" width="550" height="360" src="js/map/PlayViewDemo.htm?streamSvrIp='+streamSvrIp+'&&TextIP='+TextIP+'&&TextName1='+TextName1+'&&TextPwd1='+TextPwd1+'" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>'+
                            '</div>';
                        //样式3
                        var infowindow = new BMapLib.SearchInfoWindow(map, content, {
                            title: "隐患详情", //标题
                            //width: 590, //宽度
                            //height: 250, //高度
                            width: 820, //宽度
                            height: 360, //高度
                            panel : "panel", //检索结果面板
                            enableAutoPan : true, //自动平移
                            searchTypes :[
                            ]
                        });
                        infowindow.open(new BMap.Point(marker.getPosition().lng,marker.getPosition().lat));
                        return;
                    }else{
                        alert("请选择IE浏览器!否则影响监控视频观看!");
                        return;
                    }
                });
                moveMapByBound();
            }
        }
    ];
    for(var i=0; i < txtMenuItem.length; i++){
        contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
        if(i==1 || i==3) {
            contextMenu.addSeparator();
        }
    }
    map.addContextMenu(contextMenu);
}