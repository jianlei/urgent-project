package com.daren.core.web.wicket.security;

import com.daren.admin.entities.UserBeanImpl;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * @类描述：用户登录session管理
 * @创建人：sunlf
 * @创建时间：2014-04-04 上午11:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UserSession extends WebSession {
    private UserBeanImpl user;

    /**
     * @param request The current request
     */
    public UserSession(Request request) {
        super(request);
    }


    public UserBeanImpl getUser() {

        return this.user;
    }

    public void setUser(UserBeanImpl user) {
        this.user = user;
    }
}
