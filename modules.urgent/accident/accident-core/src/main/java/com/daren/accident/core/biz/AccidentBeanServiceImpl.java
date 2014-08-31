package com.daren.accident.core.biz;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.core.model.AccidentJson;
import com.daren.accident.core.model.ResourceJson;
import com.daren.accident.core.util.ExportExcel;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.equipment.api.dao.IEquipmentBeanDao;
import com.daren.equipment.entities.EquipmentBean;
import com.daren.expert.api.dao.IEnterpriseExpertBeanDao;
import com.daren.expert.entities.EnterpriseExpertBean;
import com.daren.rescue.api.dao.IRescueBeanDao;
import com.daren.rescue.entities.RescueBean;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
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
    private IEquipmentBeanDao equipmentBeanDao;
    private IEnterpriseExpertBeanDao enterpriseExpertBeanDao;
    private IRescueBeanDao rescueBeanDao;
    private IDictBeanService dictBeanService;
    //private


    public void setEquipmentBeanDao(IEquipmentBeanDao equipmentBeanDao) {
        this.equipmentBeanDao = equipmentBeanDao;
    }

    public void setEnterpriseExpertBeanDao(IEnterpriseExpertBeanDao enterpriseExpertBeanDao) {
        this.enterpriseExpertBeanDao = enterpriseExpertBeanDao;
    }

    public void setRescueBeanDao(IRescueBeanDao rescueBeanDao) {
        this.rescueBeanDao = rescueBeanDao;
    }

    public IAccidentBeanDao getAccidentBeanDao() {
        return accidentBeanDao;
    }

    public void setAccidentBeanDao(IAccidentBeanDao accidentBeanDao) {
        this.accidentBeanDao = accidentBeanDao;
        super.init(accidentBeanDao, AccidentBean.class.getName());
    }

    public void setDictBeanService(IDictBeanService dictBeanService) {
        this.dictBeanService = dictBeanService;
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


        List<AccidentJson> list=accidentBeanDao.findByNativeSql("select b.qymc,b.id,a.place,a.jd,a.wd,a.accidentTitle,a.place,a.detailsPlace,a.accidentType,a.accidentLevel,a.operator,a.operatorPhone,a.videoLink " +
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

    /**
     * 打印物资
     * @param resourceJson
     * @return
     */
    @POST
    @Path("/print")
    @Consumes("application/json;charset=utf-8")
    public Response printAccidentResource(ResourceJson resourceJson) {

        String[] equipment = resourceJson.getEquipment();
        HSSFWorkbook wb = new HSSFWorkbook();
        ExportExcel exportExcel = new ExportExcel(wb);
        if(equipment!=null && equipment.length>0){
            String[][] strArr = {{"物资装备名称","name",""},{"属性","property",""},{"登记类型","registrationType",""},{"所属救援队","rescueId",""},{"所属单位","unitName",""},
                    {"装备来源","equipmentSources",""},{"物资类型","equipmentType",""},{"参数规格","parametersSpecifications",""},{"计量单位","measuringUnit",""},{"数量","amount",""},
                    {"定期保修间隔","regularMaintenanceInterval",""},{"使用年限","durableYears",""},{"上一次保养日期","lastMaintenanceDate",""},{"生产厂家","manufacturer",""},
                    {"生产日期","manufactureDate",""},{"购买日期","purchaseDate",""},{"单位传真","unitFax",""},{"主要负责人","principal",""} ,{"办公电话","officePhone",""} ,
                    {"家庭电话","homePhone",""} ,{"移动电话","mobilePhone",""} ,{"装备描述或装备用途","describeOrPurposes",""} ,{"存放的仓库名","warehouse",""} ,{"存放场所","storagePlace",""},
                    {"备注","remark",""} ,{"经度","jd",""} ,{"维度","wd",""}};
            geneExportExcel(wb,exportExcel,equipment,EquipmentBean.class.getName(),"物资",strArr,"物资统计清单");
        }
        String[] expert = resourceJson.getExpert();
        if(expert!=null && expert.length>0){
            String[][] strArr = {{"专家姓名","name",""},{"出生日期","date",""},{"性别","sex",""},{"技术职称","skillTitle",""},{"学位","degree",""},{"民族","nation",""},
                    {"专家类别","type",""},{"所在城市","city",""},{"通信地址","address",""},{"单位电话","tel",""},{"手机","phone",""},{"邮箱","eMail",""},{"外语语种","language",""},
                    {"技术领域","domain",""},{"研究方向","direction",""},{"经度","jd",""},{"纬度","wd",""}};
            geneExportExcel(wb,exportExcel,expert,EnterpriseExpertBean.class.getName(),"专家",strArr,"专家统计清单");
        }
        String[] rescue = resourceJson.getRescue();
        if(rescue!=null && rescue.length>0){
            String[][] strArr = {{"救援队名称","name"},{"负责人","head"},{"负责人电话","headPhone"},{"值班电话","telephone"},{"总人数","totalNumber"},{"地址","address"},{"经度","jd"},{"纬度","wd"},
                    {"主要装备描述","equipment"},{"专长描述","expertise"},{"备注","remarks"}};
            geneExportExcel(wb,exportExcel,expert,RescueBean.class.getName(),"救援队",strArr,"救援队统计清单");
        }
        genetateFile(exportExcel.outputExcel(),"D:/a.xls");
        return Response.ok().build();
    }

    /**
     * 添加excel各项属性值
     * @param wb
     * @param exportExcel
     * @param idArr
     * @param className
     * @param sheetName
     * @param columHeaderArr
     * @return
     */
    private ExportExcel geneExportExcel(final HSSFWorkbook wb,final ExportExcel exportExcel,String[] idArr,String className,String sheetName,
                                        String[][] columHeaderArr,String headStr){
        HSSFSheet sheet = wb.createSheet(sheetName);
        exportExcel.createNormalHead(headStr,columHeaderArr.length-1,sheet);
        String[] headArr = new String[columHeaderArr.length];
            try {
                //循环获取列头，并在第三行创建
                for(int i=0;i<columHeaderArr.length;i++) {
                    headArr[i] = columHeaderArr[i][0];
                    exportExcel.createColumHeader(headArr, sheet);
                }
                //根据id，循环获取所有的对象，把值写入单元
                for (int j = 0; j < idArr.length; j++) {
                    Object beanEntity = Class.forName(className).newInstance();
                    beanEntity = equipmentBeanDao.get(EquipmentBean.class.getName(), Long.parseLong(idArr[j]));
                    //循环列
                    String[] val = new String[headArr.length];
                    for(int m=0;m<headArr.length;m++){
                        val[m] = getFieldValue(beanEntity,columHeaderArr[m][1]);
                    }
                    exportExcel.cteateCell(wb, sheet.createRow(j + 3), headArr.length, (short) 1, val);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        return exportExcel;
    }
    /**
     * 根据类和属性名获取到属性的值
     * @param obj
     * @param filed
     * @return
     */
    private String getFieldValue(Object obj, String filed) {
        Object o;
        String s = "";
        try {
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();//获得get方法
            if (pd != null) {
                o = getMethod.invoke(obj);//执行get方法返回一个Object
                if(o!=null){
                    s = o.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    /**
     * 将输入流生成文件
     * @param is  输入流
     * @param fileName  要生成的路径
     * @return
     */
    private boolean genetateFile(InputStream is,String fileName){
        boolean flag = false;
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] b = new byte[1024 * 1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                fos.write(b, 0, len);
                fos.flush();
            }
            fos.close();
            is.close();
            flag =true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}


