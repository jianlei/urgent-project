package com.daren.chemistry.manage.webapp.wicket.impl;

import com.daren.chemistry.manage.webapp.wicket.Const;
import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageEndFormPage;
import com.daren.core.web.api.workflow.IFormHandler;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：危险化学品经营许可证
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageEndFormHandler implements IFormHandler {
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ChemistryManageEndFormPage(id, model);
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
