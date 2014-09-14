package com.daren.cooperate.core.util;

import com.daren.admin.core.util.UtilTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/13 18:55
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class CookieUtil {

    private final static String cookiename = "jldaren.cooperate";

    public static Map getCookieByName(HttpServletRequest request){
        Cookie cookie=  UtilTools.getCookie(request, cookiename);
        Map cookieMap = UtilTools.getCookieValue(cookie);
        HashMap cond =  new HashMap();
        cond.put("userId", cookieMap.get("userId"));
        return cookieMap;
    }

}
