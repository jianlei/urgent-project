package com.daren.example.web.bootup.wicket;


import com.daren.core.web.component.dashboard.Dashboard;
import com.daren.core.web.component.dashboard.DefaultDashboard;
import com.daren.core.web.component.dashboard.web.DashboardContext;
import com.daren.core.web.component.dashboard.web.DashboardContextInjector;
import com.daren.core.web.component.dashboard.widget.NoticeWidgetDescriptor;
import com.daren.core.web.wicket.BaseWebApplication;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceBundles;
import org.apache.wicket.protocol.http.WebApplication;

public class IrisShiroApplication extends BaseWebApplication {

    public static MetaDataKey<DashboardContext> DASHBOARD_CONTEXT_KEY = new MetaDataKey<DashboardContext>() {};
    private Dashboard dashboard;
    private DashboardContext dashboardContext;

    public static IrisShiroApplication get() {
        return (IrisShiroApplication) WebApplication.get();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    @Override
    protected void init() {
        super.init();
        //init dashboard
        init(this);
        DashboardContext dashboardContext = getDashboardContext();
        dashboardContext.getWidgetRegistry()
                .registerWidget(new NoticeWidgetDescriptor());
        initDashboard();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return TemplatePage.class;
    }

    @Override
    public void mountHomePage() {
        mountPage("/home/", TemplatePage.class);
    }

    public DashboardContext getDashboardContext() {
        return dashboardContext;
    }

    private void initDashboard() {
//        dashboard = getDashboardContext().getDashboardPersiter().load();
        if (dashboard == null) {
            dashboard = new DefaultDashboard("default", "Default");
        }
    }

    @Override
    public ResourceBundles getResourceBundles() {
        return super.getResourceBundles();
    }

    public void init(Application application) {
        // create dashboard context
        dashboardContext = new DashboardContext();

        // store dashboard context in application
        application.setMetaData(DASHBOARD_CONTEXT_KEY, dashboardContext);

        // add dashboard context injector
        DashboardContextInjector dashboardContextInjector = new DashboardContextInjector(dashboardContext);
        application.getComponentInstantiationListeners().add(dashboardContextInjector);
    }
}
