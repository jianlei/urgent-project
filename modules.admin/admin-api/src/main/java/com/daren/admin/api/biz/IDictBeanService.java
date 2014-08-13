package com.daren.admin.api.biz;

import com.daren.admin.entities.DictBean;
import com.daren.core.api.biz.IBizService;

import java.util.List;
import java.util.Map;

/**
 * @类描述：字典服务接口类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:56
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IDictBeanService extends IBizService {
    //机构类型
    public static final String SYS_OFFICE_TYPE = "sys_office_type";
    //机构等级
    public static final String SYS_OFFICE_GRADE = "sys_office_grade";

    /**
     * 根据类型常量获得字典项
     *
     * @param type
     * @return
     */
    List<DictBean> getDictList(String type);

    /**
     * 根据类型常量获得字典项Map
     *
     * @param type
     * @return
     */
    Map<String, String> getDictMap(String type);

    /**
     * 模糊查询
     *
     * @param dictBean
     * @return
     */
    List<DictBean> query(DictBean dictBean);
}
