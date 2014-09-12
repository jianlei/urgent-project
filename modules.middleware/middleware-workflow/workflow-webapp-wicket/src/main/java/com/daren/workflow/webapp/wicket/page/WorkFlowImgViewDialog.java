package com.daren.workflow.webapp.wicket.page;

import com.daren.workflow.webapp.wicket.component.WorkFlowDialog;
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
public class WorkFlowImgViewDialog extends WorkFlowDialog {
    WebMarkupContainer workFlowImgViewDialog_div = new WebMarkupContainer("workFlowImgViewDialog_div");
    RepeatingView workFlowImgViewDialog_img = new RepeatingView("workFlowImgViewDialog_img");
    //构造函数
    public WorkFlowImgViewDialog(String id, String title,final Task processDefinition) {
        super(id, title);
        workFlowImgViewDialog_img.setOutputMarkupId(true);
        workFlowImgViewDialog_div.setOutputMarkupId(true);
        workFlowImgViewDialog_div.add(workFlowImgViewDialog_img);
        this.add(workFlowImgViewDialog_div);
        WorkFlowImgView workFlowImgView = new WorkFlowImgView(workFlowImgViewDialog_img.newChildId(), processDefinition);
        workFlowImgViewDialog_img.add(workFlowImgView);

//        //调用OnDomReadyHeader方法
//        workFlowImgView.add(new Behavior() {
//            @Override
//            public void renderHead(Component component, IHeaderResponse response) {
//                response.render(OnDomReadyHeaderItem.forScript("loadScript("+image+");"));
//            }
//        });
    }

    public WorkFlowImgViewDialog(String id, String title,final ProcessDefinition processDefinition) {
        super(id, title);
        workFlowImgViewDialog_img.setOutputMarkupId(true);
        workFlowImgViewDialog_div.setOutputMarkupId(true);
        workFlowImgViewDialog_div.add(workFlowImgViewDialog_img);
        this.add(workFlowImgViewDialog_div);
        WorkFlowImgView workFlowImgView = new WorkFlowImgView(workFlowImgViewDialog_img.newChildId(), processDefinition);
        workFlowImgViewDialog_img.add(workFlowImgView);

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
        workFlowImgViewDialog_img.removeAll();
        target.add(workFlowImgViewDialog_div);
    }

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("width", 800);
        behavior.setOption("height", 650);
    }
}
