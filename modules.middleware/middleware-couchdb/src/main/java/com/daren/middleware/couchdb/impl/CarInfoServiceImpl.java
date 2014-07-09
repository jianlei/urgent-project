package com.daren.middleware.couchdb.impl;

import com.daren.middleware.couchdb.api.ICarInfoService;
import com.daren.middleware.couchdb.model.CarInfo;
import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import java.util.List;

/**
 * @类描述：service impl
 * @创建人：sunlf
 * @创建时间：2014-03-30 上午11:40
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CarInfoServiceImpl implements ICarInfoService {
    private static CouchDbClient dbClient;

    public void init() {
        dbClient = new CouchDbClient("carinfo", true, "http", "127.0.0.1", 5984, "admin", "secret");
//        dbClient = new CouchDbClient("carinfo", true, "http", "10.133.77.42", 5984, "admin", "secret");


    }

    @Override
    public CarInfo addCarInfo(CarInfo message) {
        Response response = dbClient.save(message);
        CarInfo foo = dbClient.find(CarInfo.class, response.getId());
        return foo;
    }

    public CarInfo updateCarInfo(CarInfo message) {
        Response response = dbClient.update(message);
        CarInfo foo = dbClient.find(CarInfo.class, response.getId());
        return foo;
    }

    public CarInfo findCarInfo(String id) {
        CarInfo foo = dbClient.find(CarInfo.class, id);
        return foo;
    }

    public void deleteCarInfo(String id) {
        CarInfo foo = dbClient.find(CarInfo.class, id);
        dbClient.remove(foo);
    }

    public List<CarInfo> getAllCarInfo() {
        List<CarInfo> carInfoList = dbClient.view("carInfo/findAllCarInfo").includeDocs(true).query(CarInfo.class);
        return carInfoList;
    }

    public void destroy() {
        dbClient.shutdown();
    }
}
