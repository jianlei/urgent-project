package com.daren.equipment.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.equipment.entities.EquipmentBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by dell on 14-1-17.
 @修改人：dlw
 * @修改时间：8-26
 * @修改备注：创建getEquipmentList()方法
 */
public interface IEquipmentBeanService extends IBizService {

    /**
     *物资列表
     */
    @GET
    @Produces("application/json;charset=utf-8")
    List<EquipmentBean> getEquipmentList();

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/scope/{lng}/{lat}")
    List<EquipmentBean> getRescueBeanByScope(String lng, String lat);
}
