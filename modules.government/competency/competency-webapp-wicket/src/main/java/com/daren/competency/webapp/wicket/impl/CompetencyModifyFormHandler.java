package com.daren.competency.webapp.wicket.impl;

import com.daren.competency.webapp.wicket.Const;
import com.daren.competency.webapp.wicket.page.CompetencyModifyFormPage;
import com.daren.core.web.api.workflow.IFormHandler;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：安全资格证书（培训）
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class CompetencyModifyFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new CompetencyModifyFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "modify.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
