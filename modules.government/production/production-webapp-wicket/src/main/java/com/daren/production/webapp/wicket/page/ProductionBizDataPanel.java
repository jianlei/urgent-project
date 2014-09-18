package com.daren.production.webapp.wicket.page;

import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
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
public class ProductionBizDataPanel extends Panel {
    @Inject
    IProductionService productionService;
    ProductionBean bean;
    public ProductionBizDataPanel(String id, String bizId) {
        super(id);
        bean= (ProductionBean) productionService.getEntity(new Long(bizId));
        this.setDefaultModel(new CompoundPropertyModel(bean));
        addBizData();
        
    }

    private void addBizData() {
        //add data to form
        add(new Label("name",new PropertyModel<String>(bean, "name")));
        add(new Label("head",new PropertyModel<String>(bean, "head")));
        add(new Label("phone",new PropertyModel<String>(bean, "phone")));
        add(new Label("address",new PropertyModel<String>(bean, "address")));
        add(new Label("economicsType",new PropertyModel<String>(bean, "economicsType")));
        add(new Label("scope",new PropertyModel<String>(bean, "scope")));
        add(new Label("code",new PropertyModel<String>(bean, "code")));
        add(new Label("card",new PropertyModel<String>(bean, "card")));
        add(new Label("unitsDate",new PropertyModel<String>(bean, "unitsDate")));
        add(new Label("awardDate",new PropertyModel<String>(bean, "awardDate")));
        add(new Label("effectiveDate",new PropertyModel<String>(bean, "effectiveDate")));
        add(new Label("linkHandle",new PropertyModel<String>(bean, "linkHandle")));
    }
}
