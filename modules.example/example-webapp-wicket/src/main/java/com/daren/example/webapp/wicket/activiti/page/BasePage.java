package com.daren.example.webapp.wicket.activiti.page;


import com.daren.example.webapp.wicket.activiti.css.BootsrapReferences;
import com.daren.example.webapp.wicket.activiti.models.UserIdModel;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

@SuppressWarnings("serial")

public abstract class BasePage extends Panel {

    public BasePage(String id) {
        super(id);
		add(new Label("userId", new UserIdModel()));
    }

    @Override
    public void renderHead(HtmlHeaderContainer container) {
        container.getHeaderResponse().render(CssHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class, "bootstrap-1.0.0.css")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"less-1.1.3.min.js")));
//      container.getHeaderResponse().render(CssHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class, "bootstrap.less")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"forms.less")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"patterns.less")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"preboot.less")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"reset.less")));
        container.getHeaderResponse().render(JavaScriptContentHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"scaffolding.less")));
        container.getHeaderResponse().render(JavaScriptContentHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"tables.less")));
        container.getHeaderResponse().render(JavaScriptContentHeaderItem.forReference(new PackageResourceReference(BootsrapReferences.class,"type.less")));

        super.renderHead(container);
    }
}
