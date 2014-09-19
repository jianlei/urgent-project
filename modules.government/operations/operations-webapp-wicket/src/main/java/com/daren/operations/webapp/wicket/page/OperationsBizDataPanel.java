package com.daren.operations.webapp.wicket.page;

import com.daren.operations.api.biz.IOperationsService;
import com.daren.operations.entities.OperationsBean;
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
public class OperationsBizDataPanel extends Panel {
    @Inject
    IOperationsService operationsService;
    OperationsBean bean;
    public OperationsBizDataPanel(String id, String bizId) {
        super(id);
        bean= (OperationsBean) operationsService.getEntity(new Long(bizId));
        this.setDefaultModel(new CompoundPropertyModel(bean));
        addBizData();
        
    }

    private void addBizData() {
        //add data to form
        add(new Label("name",new PropertyModel<String>(bean, "name")));
        add(new Label("phone",new PropertyModel<String>(bean, "phone")));
        add(new Label("workType",new PropertyModel<String>(bean, "workType")));
        add(new Label("operationProject",new PropertyModel<String>(bean, "operationProject")));
        add(new Label("enterpriseName",new PropertyModel<String>(bean, "enterpriseName")));
        add(new Label("code",new PropertyModel<String>(bean, "code")));
        add(new Label("receiveDate",new PropertyModel<String>(bean, "receiveDate")));
        add(new Label("startDate",new PropertyModel<String>(bean, "startDate")));
        add(new Label("endDate",new PropertyModel<String>(bean, "endDate")));
        add(new Label("reviewDate",new PropertyModel<String>(bean, "reviewDate")));
        add(new Label("linkHandle",new PropertyModel<String>(bean, "linkHandle")));
    }
}
