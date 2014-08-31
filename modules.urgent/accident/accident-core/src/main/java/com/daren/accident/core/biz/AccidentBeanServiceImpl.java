package com.daren.accident.core.biz;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.core.model.AccidentJson;
import com.daren.accident.core.model.ResourceJson;
import com.daren.accident.core.util.ColumnStaticValue;
import com.daren.accident.core.util.ExportExcel;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.api.persistence.IGenericDao;
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
import java.util.ArrayList;
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


        List<AccidentJson> list=accidentBeanDao.findByNativeSql("select b.qymc,b.id,b.qylxfs,a.id as accident_id,a.place,a.jd,a.wd,a.accidentTitle,a.place,a.detailsPlace,a.accidentType,a.accidentLevel,a.operator,a.operatorPhone,a.videoLink " +
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
        HSSFWorkbook wb = new HSSFWorkbook();
        ExportExcel exportExcel = new ExportExcel(wb);
        String[] equipment = resourceJson.getEquipment();   //物资
        if(equipment!=null && equipment.length>0){
            geneExportExcel(wb,exportExcel,equipment,EquipmentBean.class.getName(),ColumnStaticValue.EQU_SHEET_NAME,
                    ColumnStaticValue.equStrArr,ColumnStaticValue.EQU_HDADER_NAME,equipmentBeanDao);
        }
        String[] expert = resourceJson.getExpert();         //专家
        if(expert!=null && expert.length>0){
            geneExportExcel(wb,exportExcel,expert,EnterpriseExpertBean.class.getName(),ColumnStaticValue.EXP_SHEET_NAME,
                    ColumnStaticValue.expStrArr,ColumnStaticValue.EXP_HEADER_NAME,enterpriseExpertBeanDao);
        }
        String[] rescue = resourceJson.getRescue();         //救援队
        if(rescue!=null && rescue.length>0){
            geneExportExcel(wb,exportExcel,expert,RescueBean.class.getName(),ColumnStaticValue.RES_SHEET_NAME,
                    ColumnStaticValue.resStrArr,ColumnStaticValue.RES_HEADER_NAME,rescueBeanDao);
        }
        genetateFile(exportExcel.outputExcel(),ColumnStaticValue.FILE_URI);
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
                                        String[][] columHeaderArr,String headStr,IGenericDao daoObj){
        String[] headArr = new String[columHeaderArr.length];
            try {
                List<String[]> list = new ArrayList<String[]>();
                //根据id，循环获取所有的对象，把值写入单元
                for (int j = 0; j < idArr.length; j++) {
                    Object beanEntity = Class.forName(className).newInstance();
                    beanEntity = daoObj.get(className, Long.parseLong(idArr[j]));
                    //循环列
                    String[] val = new String[headArr.length];
                    if(beanEntity!=null){
                        for(int m=0;m<headArr.length;m++){
                            String valStr = getFieldValue(beanEntity,columHeaderArr[m][1]);
                            if("".equals(columHeaderArr[m][2])){                        //如果这个字段没有字典值
                                val[m] = valStr;
                            }else{                                                      //如果是字段值，取出对应的值
                                Map<String,String> map = dictBeanService.getDictMap(columHeaderArr[m][2]);
                                val[m] = map.get(valStr);
                            }
                        }
                    list.add(val);
                    }
                }
                if(list!=null&&!list.isEmpty()){
                    HSSFSheet sheet = wb.createSheet(sheetName);
                    exportExcel.createNormalHead(headStr,columHeaderArr.length-1,sheet);
                    //循环获取列头，并在第三行创建
                    for(int i=0;i<columHeaderArr.length;i++) {
                        headArr[i] = columHeaderArr[i][0];
                        exportExcel.createColumHeader(headArr, sheet);
                    }
                    for(int p=0;p<list.size();p++){
                        String[] valStr = list.get(p);
                        exportExcel.cteateCell(wb, sheet.createRow(p + 3), headArr.length, (short) 1, valStr);
                    }
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
            Method getMethod = pd.getReadMethod();    //获得get方法
            if (pd != null) {
                o = getMethod.invoke(obj);            //执行get方法返回一个Object
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


