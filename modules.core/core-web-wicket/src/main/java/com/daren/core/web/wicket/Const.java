package com.daren.core.web.wicket;

import java.util.HashMap;
import java.util.Map;

/**
 * @类描述：系统常量
 * @创建人： sunlingfeng
 * @创建时间：2014/9/18
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class Const {
    public  static Map<String,String> map=new HashMap<>();
    static {
        map.put("daren.project.urgent","安全生产应急管理平台");
        map.put("daren.project.gis","安全生产应急管理平台");
        map.put("daren.project.system","安全生产系统管理平台");
        map.put("daren.project.government","安全生产政务管理平台");
        map.put("daren.project.example","Wicket示例");
    }
}
