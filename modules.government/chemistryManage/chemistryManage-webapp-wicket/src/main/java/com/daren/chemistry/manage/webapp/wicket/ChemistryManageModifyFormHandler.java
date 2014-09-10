package com.daren.chemistry.manage.webapp.wicket;

import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageModifyFormPage;
import com.daren.core.web.api.workflow.IFormHandler;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：demo流程的修改form注册服务
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageModifyFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ChemistryManageModifyFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "modify.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return "chemistryManage";
    }
}
