package com.daren.core.web.wicket.border;

import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.wicket.BasePage;
import com.daren.core.web.wicket.panel.HeaderPanel;
import com.daren.core.web.wicket.panel.MenuItemsPanel;
import com.daren.core.web.wicket.panel.MenuPanel;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * Created by dell on 14-3-23.
 */
public class NavomaticBorder extends Border {
    //RepeatingView breadcrumb;
    public NavomaticBorder(String id) {
        super(id);
        add(new HeaderPanel("header")).setRenderBodyOnly(true);
        add(new MenuPanel("menu").setRenderBodyOnly(true));
        //breadcrumb = new RepeatingView("breadcrumb");
        //add(breadcrumb.setRenderBodyOnly(true));
    }

   /* public void setBreadCrumb(final Class cls,final boolean isLast){
        breadcrumb.add(new BreadCrumb(breadcrumb.newChildId(),cls,isLast));
    }*/
}
