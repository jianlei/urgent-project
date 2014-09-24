package com.daren.company.web.bootup.wicket;


import com.daren.core.web.api.provider.IHomePageProvider;
import com.daren.core.web.validation.JSR303ValidationListener;
import com.daren.core.web.wicket.BaseWebApplication;
import com.daren.core.web.wicket.TemplatePage;
import com.daren.core.web.wicket.security.AccessDeniedPage;
import com.daren.core.web.wicket.security.SignInPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.pages.ExceptionErrorPage;
import org.apache.wicket.mock.MockPageManager;
import org.apache.wicket.mock.MockSessionStore;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.util.IProvider;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;
import org.wicketstuff.shiro.wicket.page.store.PageCacheManager;
import org.wicketstuff.shiro.wicket.page.store.SerializedPageWrapper;

import java.io.Serializable;

public class IrisShiroApplication extends BaseWebApplication {
    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return TemplatePage.class;
    }

    @Override
    public void mountHomePage() {
        mountPage("/home/", TemplatePage.class);
    }

    @Override
    public void mountLoginPage() {
        mountPage("login", SignInPage.class);
    }
}

