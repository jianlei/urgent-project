package com.daren.core.web.component.util;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

/**
 * @类描述：wicket常用工具类
 * @创建人： sunlingfeng
 * @创建时间：2014/8/30
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UtilPage {

    /**
     * 获得请求的url
     * @return
     */
   static public String getRequestUrl(){
        Url url = ((WebRequest) RequestCycle.get().getRequest()).getUrl();
        String fullUrl = RequestCycle.get().getUrlRenderer().renderFullUrl(url);
        return fullUrl;

    }
}
