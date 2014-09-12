package com.daren.workflow.webapp.wicket.page;

import com.daren.core.web.component.map.MapDialog;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * @类描述：初始化该对话框
 * @创建人： dlw
 * @创建时间：13:26
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class WorkFlowImgViewPage extends MapDialog {
    WebMarkupContainer wmc = new WebMarkupContainer("allmapwmc");
    RepeatingView allmap = new RepeatingView("allmap");
    //构造函数
    public WorkFlowImgViewPage(String id, String title,final Task processDefinition) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        WorkFlowImgView workFlowImgView = new WorkFlowImgView(allmap.newChildId(), processDefinition);
        allmap.add(workFlowImgView);

//        //调用OnDomReadyHeader方法
//        workFlowImgView.add(new Behavior() {
//            @Override
//            public void renderHead(Component component, IHeaderResponse response) {
//                response.render(OnDomReadyHeaderItem.forScript("loadScript("+image+");"));
//            }
//        });
    }

    public WorkFlowImgViewPage(String id, String title,final ProcessDefinition processDefinition) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        WorkFlowImgView workFlowImgView = new WorkFlowImgView(allmap.newChildId(), processDefinition);
        allmap.add(workFlowImgView);

//        //调用OnDomReadyHeader方法
//        workFlowImgView.add(new Behavior() {
//            @Override
//            public void renderHead(Component component, IHeaderResponse response) {
//                response.render(OnDomReadyHeaderItem.forScript("loadScript("+image+");"));
//            }
//        });
    }
    @Override
    public void onClose(AjaxRequestTarget target, DialogButton button) {
        allmap.removeAll();
        target.add(wmc);
    }

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("width", 800);
        behavior.setOption("height", 650);
    }
}
