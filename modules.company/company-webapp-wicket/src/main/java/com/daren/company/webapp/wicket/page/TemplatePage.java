package com.daren.company.webapp.wicket.page;

import com.daren.core.web.component.jgrowl.JGrowlFeedbackPanel;
import com.daren.core.web.component.jgrowl.Options;
import com.daren.core.web.wicket.Const;
import com.daren.core.web.wicket.FooterPage;
import com.daren.core.web.wicket.HomePanel;
import com.daren.core.web.wicket.custome.CustomeHeaderPanel;
import com.daren.core.web.wicket.custome.CustomeMenuPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
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
public class TemplatePage extends WebPage {

//    String url = "daren.project.urgent";
    final JGrowlFeedbackPanel feedback;

    public TemplatePage() {
        //系统消息
        feedback = new JGrowlFeedbackPanel("jgrowlFeedback");
        add(feedback);

        sendInfo();

        add(new Label("title", Const.map.get(getApplication().getName())));

        Link home=new Link("home") {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
//                getRequestUrl();
            }
        };
        this.add(home);
        final WebMarkupContainer wmc = new WebMarkupContainer("container");

        String name = getApplication().getName();
        String css_url="<style type=\"text/css\">.cus {\n" +
                "    background: url(../../cus/img/"+name+".png) no-repeat;\n" +
                "    height: 97px;\n" +
                "    position: relative;\n" +
                "}</style>";

        Label label = new Label("css", css_url);
        label.setEscapeModelStrings(false);
        label.setRenderBodyOnly(true);
        this.add(label);

        wmc.setOutputMarkupPlaceholderTag(true); //Set the flag placeholder so it can be updated via AJAX

        add(wmc.setOutputMarkupId(true));

        //添加系统菜单

        add(new CustomeHeaderPanel("headerPanel"));
        add(new CustomeMenuPanel("menuPanel", wmc));
        wmc.add(new HomePanel("panel", wmc));
        add(new FooterPage("footer"));
//        add(new Include("footer", "cus/Template/FooterPage.html"));
    }

    private void sendInfo() {
        final Options errorOptions = new Options();
        errorOptions.set("header", "我们都知道了");
        errorOptions.set("theme", "info");
        errorOptions.set("glue", "after");
        errorOptions.set("position", "bottom-right");
        feedback.setInfoMessageOptions(errorOptions);

        feedback.info("测试消息1");


        feedback.info("测试消息2");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
       /* IJavaScriptLibrarySettings settings =Application.get().getJavaScriptLibrarySettings();
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getJQueryReference())));
*/
    }


}
