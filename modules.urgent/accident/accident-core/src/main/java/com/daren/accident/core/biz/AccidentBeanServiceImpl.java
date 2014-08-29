package com.daren.accident.core.biz;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.core.model.AccidentJson;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @类描述：品牌服务实现类
 * @创建人：wangkr
 * @创建时间：2014-04-04 下午2:42
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AccidentBeanServiceImpl extends GenericBizServiceImpl implements IAccidentBeanService {
    private IAccidentBeanDao accidentBeanDao;

    public void setAccidentBeanDao(IAccidentBeanDao accidentBeanDao) {
        this.accidentBeanDao = accidentBeanDao;
        super.init(accidentBeanDao, AccidentBean.class.getName());
    }

    @Override
    public List<AccidentBean> queryAccidentByAccidentLevel() {
        return accidentBeanDao.find("select a from AccidentBean a where a.accidentLevel ='1' ");
    }

    @Override
    @POST
    @Path("/add")
    @Consumes("application/json;charset=utf-8")
    public Response addAccident(AccidentBean bean) {
        accidentBeanDao.save(bean);
        return Response.ok().build();
    }

    /**
     * 获得未处理的事故列表.status=1
     * @return
     */
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/")
    public List<AccidentBean> getAllAccident() {
        return accidentBeanDao.find("select a from AccidentBean a where a.status =0");
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/all")
    public List<AccidentJson> getAccidentList() {
        return accidentBeanDao.findByNativeSql("select b.qymc,a.accidentUnit,a.place,a.jd,a.wd,a.accidentTitle,a.place,a.detailsPlace,a.accidentType,a.accidentLevel,a.operator,a.operatorPhone,a.videoLink from urg_accident a,urg_ent_enterprise b where a.status =0 and a.accidentUnit=b.id", AccidentJson.class);
    }

    /**
     * 更新事故状态
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Response updateAccidentStatus(@PathParam("id") String id) {
        AccidentBean accidentBean= accidentBeanDao.get(AccidentBean.class.getName(),new Long(id));
        accidentBean.setStatus(1);
        accidentBeanDao.save(accidentBean);
        return Response.ok().build();
    }
}


