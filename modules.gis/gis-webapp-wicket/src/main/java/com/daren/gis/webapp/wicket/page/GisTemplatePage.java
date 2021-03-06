package com.daren.gis.webapp.wicket.page;

import com.daren.core.web.wicket.FooterPage;
import com.daren.core.web.wicket.custome.CustomeHeaderPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

/**
 * 项目名称:  urgent-project
 * 类描述:    Gis子系统首页
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */
@ShiroSecurityConstraint(constraint = ShiroConstraint.LoggedIn)
public class GisTemplatePage extends WebPage {
    public GisTemplatePage() {
        final WebMarkupContainer wmc = new WebMarkupContainer("container");
        wmc.setOutputMarkupPlaceholderTag(true); //Set the flag placeholder so it can be updated via AJAX

        add(wmc.setOutputMarkupId(true));
        add(new CustomeHeaderPanel("headerPanel"));
//         add(new CustomeMenuPanel("menuPanel", wmc));
        wmc.add(new GisPanel("panel", wmc));
        add(new FooterPage("footer"));
//        add(new Include("footer", "cus/Template/FooterPage.html"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
       /* IJavaScriptLibrarySettings settings =Application.get().getJavaScriptLibrarySettings();
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getJQueryReference())));
*/


    }
}
