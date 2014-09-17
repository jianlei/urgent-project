package com.daren.operations.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.operations.webapp.wicket.Const;
import com.daren.operations.webapp.wicket.page.OperationsEndFormPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：特种作业人员操作资格证
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OperationsEndFormHandler implements IFormHandler {
    @Override
    public Panel getPanel(String id, IModel model) {
        return new OperationsEndFormPage(id, model);
    }

    @Override
    public String getFormKey() {
        return "end.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
