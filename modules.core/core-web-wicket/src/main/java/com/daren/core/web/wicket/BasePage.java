package com.daren.core.web.wicket;

import com.daren.core.web.wicket.border.NavomaticBorder;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by dell on 14-3-23.
 */
public class BasePage extends WebPage {
    private NavomaticBorder navomaticBorder;

    public BasePage() {
        this(new PageParameters());
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        navomaticBorder = new NavomaticBorder("iris");
        super.add(navomaticBorder);
    }

    /*public void setBreadCrumb(final Class cls,final boolean isLast){
        navomaticBorder.setBreadCrumb(cls,isLast);
    }*/

    public MarkupContainer add(org.apache.wicket.Component component) {
        navomaticBorder.add(component);
        return navomaticBorder;
    }

    /**
     * Construct.
     *
     * @param model
     */
    public BasePage(IModel<?> model) {
        super(model);
    }
}
