package com.daren.core.impl.test;

import com.daren.platform.couchdb.api.ICarInfoService;
import com.daren.platform.couchdb.model.CarInfo;

import java.util.List;

/**
 * @类描述：test
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午11:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class TestMessage {
    private ICarInfoService carInfoService;

    public void setCarInfoService(ICarInfoService carInfoService) {
        this.carInfoService = carInfoService;
    }

    public void init() {
       /* CarInfo carInfo=carInfoService.findCarInfo("aaa61fbcb928494188c9d5481a117b96") ;
        carInfo.setName("红旗H7 2.0T");
        carInfo.setPrice("29.981(万元)");
        carInfoService.updateCarInfo(carInfo) ;*/
        List<CarInfo> carInfoList = carInfoService.getAllCarInfo();


    }
}
