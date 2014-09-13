package com.daren.fireworks.webapp.wicket.page;

import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import org.activiti.engine.task.Task;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksAuditFormPage extends BaseFormPanel {
    public FireworksAuditFormPage(String id, final IModel<Task> model) {
        super(id, model);
    }
}
