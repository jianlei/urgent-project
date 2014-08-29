package com.daren.accident.core.biz;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.core.model.AccidentJson;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private IDictBeanService dictBeanService;

    public void setDictBeanService(IDictBeanService dictBeanService) {
        this.dictBeanService = dictBeanService;
    }

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
        //统一事故发生时间
        bean.setAccidentTime(new Date());
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
        //事故类别
        Map<String,String> map_type=dictBeanService.getDictMap(IDictConstService.ACCIDENT_TYPE);
        //事故级别
        Map<String,String> map_level=dictBeanService.getDictMap(IDictConstService.ACCIDENT_LEVEL);


        List<AccidentJson> list=accidentBeanDao.findByNativeSql("select b.qymc,a.place,a.jd,a.wd,a.accidentTitle,a.place,a.detailsPlace," +
                "a.accidentType,a.accidentLevel,a.operator,a.operatorPhone,a.videoLink " +
                "from urg_accident a,urg_ent_enterprise b where a.status =0 and a.accidentUnit=b.qyid", AccidentJson.class);

        for(AccidentJson json:list){
            if(map_level.containsKey(json.getAccidentLevel()))
                json.setAccidentLevel(map_level.get(json.getAccidentLevel()));
            else
                json.setAccidentLevel("未知");
            if(map_type.containsKey(json.getAccidentType()))
                json.setAccidentType(map_type.get(json.getAccidentType()));
            else
                json.setAccidentType("未知");

        }
        return list;
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


