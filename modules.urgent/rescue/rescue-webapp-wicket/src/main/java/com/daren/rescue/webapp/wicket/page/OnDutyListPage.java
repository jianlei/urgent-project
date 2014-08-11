package com.daren.rescue.webapp.wicket.page;


import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
import com.daren.rescue.api.biz.IOnDutyBeanService;
import com.daren.rescue.entities.OnDutyBean;
import com.daren.rescue.entities.RescueBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @类描述：救援队管理-上传值班
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OnDutyListPage extends IrisAbstractDialog<RescueBean> {
    //注入服务
    @Inject
    @Reference(id = "onDutyBeanService", serviceInterface = IOnDutyBeanService.class)
    private IOnDutyBeanService onDutyBeanService;

    public OnDutyListPage(String id, String title, IModel<RescueBean> model) {
        super(id, title, model);
        RescueBean rescueBean = (RescueBean) model.getObject();
        long entityId = rescueBean.getId();
        List<OnDutyBean> list = onDutyBeanService.getOnDutyBeanByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<OnDutyBean> lv = new PageableListView<OnDutyBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<OnDutyBean> item) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                final OnDutyBean onDutyBean = item.getModelObject();
                item.add(new Label("date", format.format(onDutyBean.getDate())));
                item.add(new Label("person", onDutyBean.getPerson()));
                item.add(new Label("tel", onDutyBean.getTel()));
                item.add(new Label("expertise", onDutyBean.getExpertise()));
                item.add(new Label("remarks", onDutyBean.getRemarks()));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
}
