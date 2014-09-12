package com.daren.chemistry.manage.webapp.wicket.impl;

import com.daren.chemistry.manage.webapp.wicket.Const;
import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageStartFormPage;
import com.daren.core.web.api.workflow.IFormHandler;
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
public class ChemistryManageStartFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ChemistryManageStartFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "start.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
