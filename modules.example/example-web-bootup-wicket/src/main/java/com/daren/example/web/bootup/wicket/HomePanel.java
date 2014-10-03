package com.daren.example.web.bootup.wicket;

import com.daren.core.web.component.dashboard.Dashboard;
import com.daren.core.web.component.dashboard.Widget;
import com.daren.core.web.component.dashboard.WidgetFactory;
import com.daren.core.web.component.dashboard.WidgetLocation;
import com.daren.core.web.component.dashboard.web.DashboardContext;
import com.daren.core.web.component.dashboard.web.DashboardPanel;
import com.daren.core.web.component.dashboard.widget.NoticeWidgetDescriptor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */
@ShiroSecurityConstraint(constraint = ShiroConstraint.LoggedIn)
public class HomePanel extends Panel {
    DashboardContext dashboardContext;
    //ajax target container
    private Dashboard dashboard;
    public HomePanel(String id, WebMarkupContainer wmc) {
        /**
         * test forward link
         */
        super(id);

        String url = getApplication().getName();
        String css_url="<style type=\"text/css\">.darenBody {\n" +
                "    background-image: url(../../cus/img/welcome_"+url+".png);\n" +
                "    width: 825px;\n" +
                "    height: 448px;\n" +
                "    margin-top: -224px;\n" +
                "    margin-left: -412px;\n" +
                "    position: absolute;\n" +
                "    left: 50%;\n" +
                "    top: 50%;\n" +
                "}</style>";

        Label label = new Label("css", css_url);
        label.setEscapeModelStrings(false);
        label.setRenderBodyOnly(true);
        this.add(label);

//        add(new Include("footer", "cus/Template/FooterPage.html"));
        Dashboard dashboard = IrisShiroApplication.get().getDashboard();
        dashboard.setColumnCount(2);

        dashboardContext=IrisShiroApplication.get().getDashboardContext();
        WidgetFactory widgetFactory = dashboardContext.getWidgetFactory();

        Widget widget = widgetFactory.createWidget(new NoticeWidgetDescriptor());
//        widget.setTitle(getUniqueWidgetTitle(widget.getTitle(), count));
        // DashboardPanel is on other page
//					send(getPage(), Broadcast.BREADTH, new DashboardEvent(target, DashboardEvent.EventType.WIDGET_ADDED, widget));
//        Dashboard dashboard = getDashboard();
        //DashboardUtils.updateWidgetLocations(dashboard, new DashboardEvent(null, DashboardEvent.EventType.WIDGET_ADDED, widget));
        Widget widget1 = widgetFactory.createWidget(new NoticeWidgetDescriptor());
        WidgetLocation widgetLocation=new WidgetLocation(1,0);

        widget1.setLocation(widgetLocation);
        dashboard.addWidget(widget);
        dashboard.addWidget(widget1);
       // dashboardContext.getDashboardPersiter().save(dashboard);
        add(new DashboardPanel("dashboard", new Model<Dashboard>(dashboard)));
    }


}
