package com.daren.core.web.bootup.wicket;


import com.daren.core.web.api.provider.IHomePageProvider;
import com.daren.core.web.validation.JSR303ValidationListener;
import com.daren.core.web.wicket.HomePage;
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
import org.apache.wicket.util.IProvider;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;
import org.wicketstuff.shiro.wicket.page.store.PageCacheManager;
import org.wicketstuff.shiro.wicket.page.store.SerializedPageWrapper;

import java.io.Serializable;

public class IrisShiroApplication extends WebApplication {
    private IHomePageProvider homePageProvider;

    public void setHomePageProvider(IHomePageProvider homePageProvider) {
        this.homePageProvider = homePageProvider;

    }

    // --------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------

    public IrisShiroApplication() {
        /*try {
            homePageProvider = JNDIHelper.getJNDIServiceForName(IHomePageProvider.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public Class<? extends Page> getHomePage() {
        //return homePageProvider.getPageClass();
        return HomePage.class;
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

        setSessionStoreProvider(new PlatSessionStoreProver());
        setPageManagerProvider(new NoSerializationPageManagerProvider(this));
        //设置首页映射路径
        //mountPage("/home/", homePageProvider.getPageClass());
        mountPage("/home/", HomePage.class);
        // Enable Shiro security
        AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
        getSecuritySettings().setAuthorizationStrategy(authz);
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(
                new ShiroUnauthorizedComponentListener(SignInPage.class, AccessDeniedPage.class, authz));
        // SecurityUtils.getSubject().getSession().setTimeout(-1000l);
        //增加JSR303校验监听器
        getComponentPostOnBeforeRenderListeners().add(new JSR303ValidationListener());
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


    class PlatSessionStoreProver implements IProvider<ISessionStore> {


        @Override
        public ISessionStore get() {
            return new MockSessionStore();
        }
    }

    class NoSerializationPageManagerProvider extends DefaultPageManagerProvider {

        /**
         * Construct.
         *
         * @param application
         */
        public NoSerializationPageManagerProvider(Application application) {
            super(application);
        }

        @Override
        public IPageManager get(IPageManagerContext pageManagerContext) {

            return new MockPageManager();
        }

        @Override
        protected IPageStore newPageStore(IDataStore dataStore) {
            return new SessionPageStore();

        }
    }


    class SessionPageStore implements IPageStore {

        private final Logger LOG = LoggerFactory.getLogger(SessionPageStore.class);
        private final String PAGE_MAP_SESSION_KEY = SessionPageStore.class.getName() +
                "_PAGE_CACHE_MANAGER_SESSION_KEY";

        protected static final int DEFAULT_MAX_PAGES = -1;

        private int MAX_PAGE_MAP_SIZE;

        public SessionPageStore() {
            this(DEFAULT_MAX_PAGES);
        }

        public SessionPageStore(final int maxPageMapSize) {
            if (maxPageMapSize <= -1) {
                MAX_PAGE_MAP_SIZE = DEFAULT_MAX_PAGES;
                LOG.info("Created SessionPageStore: unlimited number of pages allowed.");
            } else {
                MAX_PAGE_MAP_SIZE = maxPageMapSize;
                LOG.info("Created SessionPageStore: [{}] maximum number of pages allowed.",
                        maxPageMapSize);
            }
        }


        public boolean containsPage(final String sessionId, final int pageId) {
            return getPageCacheManager(sessionId).getPageCache().containsPage(pageId);
        }

        /**
         * {@inheritDoc}
         */
        public IManageablePage convertToPage(final Object page) {
            if (page == null)
                return null;

            if (page instanceof IManageablePage)
                return (IManageablePage) page;

            if (page instanceof byte[])
                return deserializePage((byte[]) page);

            throw new IllegalArgumentException("Unknown object type " + page.getClass().getName());

        }

        protected IManageablePage deserializePage(final byte data[]) {
            // TODO: test this, Serializer replacing old
            // WicketObjects.byteArrayToObject(data);
            // call
            return (IManageablePage) Application.get()
                    .getFrameworkSettings()
                    .getSerializer()
                    .deserialize(data);
        }

        /**
         * {@inheritDoc}
         */
        public void destroy() {
            // do nothing - session timeout will cleanup automatically
        }

        public int getMaxPageMapSize() {
            return MAX_PAGE_MAP_SIZE;
        }

        /**
         * {@inheritDoc}
         */
        public Page getPage(final String sessionId, final int pageId) {
            final SerializedPageWrapper wrapper = getPageCacheManager(sessionId).getPageCache()
                    .getPage(pageId);
            final byte[] sPage = wrapper != null ? (byte[]) wrapper.getPage() : null;
            return (Page) (sPage != null ? deserializePage(sPage) : null);
        }

        protected PageCacheManager getPageCacheManager(final String sessionId) {
            final Session session = getSessionForUpdate(sessionId);
            PageCacheManager pcc = (PageCacheManager) session.getAttribute(PAGE_MAP_SESSION_KEY);
            if (pcc == null) {
                pcc = new PageCacheManager(getMaxPageMapSize());
                session.setAttribute(PAGE_MAP_SESSION_KEY, pcc);
            }
            return pcc;
        }

        protected Session getSession(final String sessionId) {
            Session session = null;
            final Subject currentSubject = SecurityUtils.getSubject();
            if (currentSubject != null) {
                session = currentSubject.getSession(false);
            /*
             * guarantee we pulled the same session that Wicket expects us to pull. Because Shiro's
			 * Subject acquisition in web apps is based on the incoming request, and so is Wicket's,
			 * this should _always_ be the same. If not, something is seriously wrong:
			 */
                if (session != null && sessionId != null && !sessionId.equals(session.getId()))
                    throw new WicketRuntimeException(
                            "The specified Wicket sessionId [" +
                                    sessionId +
                                    "] is not the same as Shiro's current Subject Session with id [" +
                                    session.getId() +
                                    "], indicating the Wicket request's session is not the same as Shiro's current Subject Session. " +
                                    "The two must always be equal when using the " +
                                    getClass().getName() +
                                    " implementation. " +
                                    "If you're seeing this exception, ensure you have configured Shiro to use Enterprise Sessions and not (the default) HTTP-only Sessions.");
            }
            return session;
        }

        protected Session getSessionForUpdate(final String sessionId) {
            Session session = getSession(sessionId);
            if (session == null)
                session = SecurityUtils.getSubject().getSession();
            return session;
        }

        public void pageAccessed(final String sessionId, final Page page) {
            // nothing to do
        }

        /**
         * {@inheritDoc}
         */
        public Serializable prepareForSerialization(final String sessionId, final Object page) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public void removePage(final String sessionId, final int pageId) {
            if (pageId != -1) {
                LOG.debug("Removing page with id [{}]", pageId);
                getPageCacheManager(sessionId).getPageCache().removePage(pageId);
            }
        }

        /**
         * {@inheritDoc}
         */
        public Object restoreAfterSerialization(final Serializable serializable) {
            return null;
        }

        protected SerializedPageWrapper serialize(final String sessionId, final IManageablePage page) {
            final byte[] serializedPage = serializePage(sessionId, page);
            return wrap(serializedPage, page.getPageId());
        }

        protected byte[] serializePage(final String sessionId, final IManageablePage page) {
            Args.notNull(sessionId, "sessionId");
            Args.notNull(page, "page");

            // TODO: test this, Serializer replacing old
            // WicketObjects.objectToByteArray(page, applicationName);
            // call
            final byte data[] = Application.get()
                    .getFrameworkSettings()
                    .getSerializer()
                    .serialize(page);
            return data;
        }

        /**
         * {@inheritDoc}
         */
        public void storePage(final String sessionId, final IManageablePage page) {
            final SerializedPageWrapper wrapper = serialize(sessionId, page);
            getPageCacheManager(sessionId).getPageCache().storePages(wrapper);
            if (LOG.isDebugEnabled())
                LOG.debug("storePage {}", page.toString());
        }

        /**
         * {@inheritDoc}
         */
        public void unbind(final String sessionId) {
            final Session active = getSession(sessionId);
            if (active != null) {
                final Object existing = active.removeAttribute(PAGE_MAP_SESSION_KEY);
                if (existing != null)
                    LOG.debug("Removed PageMap [{}] from the Session (destroying)", existing);
            }
        }

        SerializedPageWrapper wrap(final byte[] serializedPages, final int pageId) {
            final SerializedPageWrapper wrapper = new SerializedPageWrapper(serializedPages, pageId);
            return wrapper;
        }
    }
}
