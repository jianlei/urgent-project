package com.daren.core.web.bootup.wicket;

import com.daren.core.web.wicket.security.AccessDeniedPage;
import com.daren.core.web.wicket.security.SignInPage;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.pages.ExceptionErrorPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

public class IrisShiroApplication extends WebApplication {

    // --------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------

    public IrisShiroApplication() {
    }

    // --------------------------------------------------------------------------
    // Overrides
    // --------------------------------------------------------------------------

    @Override
    protected void init() {
        super.init();
        //避免将Wicket 标签输出到客户端
        getMarkupSettings().setStripWicketTags(true);
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        // Sets Wicket Request Cycle settings for this application
        getRequestCycleSettings().setBufferResponse(true);
        getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        mountPage("/error404", ErrorPage404.class);
        setPageManagerProvider(new NoSerializationPageManagerProvider(this));
        //设置首页映射路径
        mountPage("/home/", HomePage.class);

        // Enable Shiro security
        AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
        getSecuritySettings().setAuthorizationStrategy(authz);
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(
                new ShiroUnauthorizedComponentListener(SignInPage.class, AccessDeniedPage.class, authz));
        // SecurityUtils.getSubject().getSession().setTimeout(-1000l);

        mountPage("login", SignInPage.class);

        /* In case of unhandled exception redirect it to a custom page */
        getRequestCycleListeners().add(new AbstractRequestCycleListener() {
            @Override
            public IRequestHandler onException(RequestCycle cycle,
                                               Exception e) {
                return new RenderPageRequestHandler(new
                        PageProvider(new ExceptionErrorPage(e, new ErrorPage404())));

            }
        });
    }

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }


}
