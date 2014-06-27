package com.daren.core.web.wicket.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * Created by dell on 14-3-24.
 */
public class SignOutPage extends WebPage {
    public static final String REDIRECTPAGE_PARAM = "redirectpage";

    @SuppressWarnings("unchecked")
    public SignOutPage(final PageParameters parameters) {
        StringValue page = parameters.get(REDIRECTPAGE_PARAM);
        Class<? extends Page> pageClass;
        /*if (page != null) {
            try {
                pageClass = (Class<? extends Page>) Class.forName(String.valueOf(page));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {*/

        pageClass = getApplication().getHomePage();
       /* }*/
//        getSession().invalidate();
        // this should remove the cookie and invalidate session
        setStatelessHint(true);
        setResponsePage(pageClass);
        final Subject subject = SecurityUtils.getSubject();
//        LOG.info("logout: " + subject);
        subject.logout();

    }

    public SignOutPage() {
        this(new PageParameters());
    }
}