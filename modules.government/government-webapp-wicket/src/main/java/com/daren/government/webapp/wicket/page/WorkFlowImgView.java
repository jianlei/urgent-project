package com.daren.government.webapp.wicket.page;

import com.daren.government.webapp.wicket.css.JScriptReferences;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

public class WorkFlowImgView extends Panel {
    public WorkFlowImgView(String id,String flwid) {
        super(id);
    }

    @Override
    public void renderHead(HtmlHeaderContainer container) {
       container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/jstools.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/raphael.js")));

        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/jquery/jquery.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/jquery/jquery.progressbar.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/jquery/jquery.asyncqueue.js")));

        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/Color.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/Polyline.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/ActivityImpl.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/ActivitiRest.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/LineBreakMeasurer.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/ProcessDiagramGenerator.js")));
        container.getHeaderResponse().render(JavaScriptHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/ProcessDiagramCanvas.js")));


        //container.getHeaderResponse().render(CssHeaderItem.forReference(new PackageResourceReference(JScriptReferences.class, "js/bootstrap-1.0.0.css")));

        super.renderHead(container);
    }
}