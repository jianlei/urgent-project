package com.daren.core.web.bootup.wicket;

import com.daren.core.web.wicket.security.BasicAuthenticationSession;
import com.daren.core.web.wicket.security.SignInPage;
import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;

import java.io.Serializable;

/**
 * Created by dell on 14-3-23.
 */
public class IrisApplication extends AuthenticatedWebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        /*AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
        getSecuritySettings().setAuthorizationStrategy(authz);
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(
                new ShiroUnauthorizedComponentListener(ErrorPage404.class, AccessDeniedPage.class, authz));*/

        // Sets Wicket Markup settings for this application
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
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return BasicAuthenticationSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }


}

class NoSerializationPageManagerProvider extends DefaultPageManagerProvider {

    public NoSerializationPageManagerProvider(Application application) {
        super(application);
    }

    @Override
    protected IPageStore newPageStore(IDataStore dataStore) {
        return new IPageStore() {
            @Override
            public void destroy() {
            }

            @Override
            public IManageablePage getPage(String sessionId, int pageId) {
                return null;
            }

            @Override
            public void removePage(String sessionId, int pageId) {
            }

            @Override
            public void storePage(String sessionId, IManageablePage page) {
            }

            @Override
            public void unbind(String sessionId) {
            }

            @Override
            public Serializable prepareForSerialization(String sessionId, Object page) {
                return null;
            }

            @Override
            public Object restoreAfterSerialization(Serializable serializable) {
                return null;
            }

            @Override
            public IManageablePage convertToPage(Object page) {
                return null;
            }
        };
    }
}
