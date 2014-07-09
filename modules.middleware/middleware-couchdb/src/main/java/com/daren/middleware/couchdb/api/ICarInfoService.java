package com.daren.middleware.couchdb.api;

import com.daren.middleware.couchdb.model.CarInfo;

import java.util.List;

/**
 * @类描述：car info service
 * @创建人：sunlf
 * @创建时间：2014-03-30 上午11:39
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface ICarInfoService {
    CarInfo addCarInfo(CarInfo message);

    public CarInfo updateCarInfo(CarInfo message);

    public CarInfo findCarInfo(String id);

    public void deleteCarInfo(String id);

    public List<CarInfo> getAllCarInfo();
}
