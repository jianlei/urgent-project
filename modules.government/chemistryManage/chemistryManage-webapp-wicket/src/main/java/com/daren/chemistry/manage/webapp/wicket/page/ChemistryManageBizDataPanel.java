package com.daren.chemistry.manage.webapp.wicket.page;


import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;

/**
 * @类描述：显示业务数据的页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageBizDataPanel extends Panel {
    @Inject
    IChemistryManageBeanService chemistryManageBeanService;
    ChemistryManageBean bean;
    public ChemistryManageBizDataPanel(String id,String bizId) {
        super(id);
        bean= (ChemistryManageBean) chemistryManageBeanService.getEntity(new Long(bizId));
        this.setDefaultModel(new CompoundPropertyModel(bean));
        addBizData();
        
    }

    private void addBizData() {
        //add data to form
        add(new Label("code",new PropertyModel<String>(bean, "code")));
        add(new Label("name",new PropertyModel<String>(bean, "name")));
        add(new Label("header",new PropertyModel<String>(bean, "header")));
        add(new Label("address",new PropertyModel<String>(bean, "address")));
        add(new Label("unitType",new PropertyModel<String>(bean, "unitType")));
        add(new Label("scope",new PropertyModel<String>(bean, "scope")));
        add(new Label("mode",new PropertyModel<String>(bean, "mode")));
        add(new Label("startDate",new PropertyModel<String>(bean, "startDate")));
        add(new Label("endDate",new PropertyModel<String>(bean, "endDate")));
        add(new Label("unitsDate",new PropertyModel<String>(bean, "unitsDate")));
        add(new Label("proposerId",new PropertyModel<String>(bean, "proposerId")));
        add(new Label("qyid",new PropertyModel<String>(bean, "qyid")));
    }
}
