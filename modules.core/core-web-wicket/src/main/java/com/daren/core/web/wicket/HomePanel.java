package com.daren.core.web.wicket;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
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
    //ajax target container
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
    }
}
