package com.daren.government.webapp.wicket.util;

import com.daren.core.util.JNDIHelper;

import java.io.IOException;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/4
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OsgiService {
    public static Object getService(String serviceName){
        try {
            return JNDIHelper.getJNDIServiceForName(serviceName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
