package com.daren.core.web.wicket;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
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
public class HomePanel extends BasePanel {
    //ajax target container
    public HomePanel(String id, WebMarkupContainer wmc) {
        /**
         * test forward link
         */
        super(id, wmc);
        Link link = new Link("link") {
            @Override
            public void onClick() {
                setResponsePage(new RedirectPage("http://www.google.com"));
            }
        };
        add(link);

//        add(new Include("footer", "cus/Template/FooterPage.html"));
    }
}
