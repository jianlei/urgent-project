/**
 * 
 */
package com.daren.admin.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @author tianzhiwei
 *
 */
public class AnnotationMoblieProc {

	/**
	 * 
	 */
	public AnnotationMoblieProc() {
		// TODO Auto-generated constructor stub
	}
	
	public static HashMap parseMoblie(Object obj) throws ClassNotFoundException
	{   HashMap copymap= new HashMap();
		Class clazz = obj.getClass();
		List<Field> fieldList = new ArrayList<Field>(); 
		  for(Field f : clazz.getDeclaredFields()){//访问所有字段 
		      Mobile moblie = f.getAnnotation(Mobile.class); 
		      String propertyName = f.getName();
		      if(moblie!= null) {
		    	  String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		    	  String MethodName = "get"+methodEnd;
		    	  Object value =null ;
		    	  try {
		    		  Method getMethod = clazz.getDeclaredMethod(MethodName, new Class[]{  });
		              value = getMethod.invoke(obj, new Object[]{});//调用方法获取方法的返回值
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		    	  copymap.put(f.getName(), value);
		      }
		   
		} 
		return copymap;
	}

}
