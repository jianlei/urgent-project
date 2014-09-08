package com.daren.government.webapp.wicket;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.government.webapp.wicket.page.DemoTaskFormPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：demo流程的启动form注册服务
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DemoTaskFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new DemoTaskFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "Review.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return "demoprocess";
    }
}
