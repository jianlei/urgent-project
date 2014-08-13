/**
 * Created by Administrator on 2014/8/12.
 */
jQuery(function ($) {
    /** 百度地图API功能 **/
    var map = new BMap.Map("allmap");            // 创建Map实例
    var point = new BMap.Point(116.404, 39.915); // 创建点坐标
    map.centerAndZoom(point, 15);                 // 初始化地图,设置中心点坐标和地图级别。
    map.enableScrollWheelZoom();                 //启用滚轮放大缩小
});