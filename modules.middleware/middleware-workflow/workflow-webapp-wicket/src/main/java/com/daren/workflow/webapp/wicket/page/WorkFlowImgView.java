package com.daren.workflow.webapp.wicket.page;

import com.daren.workflow.webapp.wicket.component.ActiveActivityImage;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.markup.html.panel.Panel;

public class WorkFlowImgView extends Panel {
    public WorkFlowImgView(String id,Task task) {
        super(id);
        this.add(new ActiveActivityImage("image",task));//向页面标签输出图片
    }
    public WorkFlowImgView(String id,ProcessDefinition processDefinition) {
        super(id);
        this.add(new ActiveActivityImage("image",processDefinition));//向页面标签输出图片
    }
/*    @Override
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
    }*/
}