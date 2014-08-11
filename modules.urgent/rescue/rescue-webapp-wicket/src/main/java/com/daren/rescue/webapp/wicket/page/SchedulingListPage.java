package com.daren.rescue.webapp.wicket.page;


import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.rescue.api.biz.ISchedulingBeanService;
import com.daren.rescue.entities.RescueBean;
import com.daren.rescue.entities.SchedulingBean;
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
public class SchedulingListPage extends IrisAbstractDialog<RescueBean> {
    //注入服务
    @Inject
    @Reference(id = "schedulingBeanService", serviceInterface = ISchedulingBeanService.class)
    private ISchedulingBeanService schedulingBeanService;

    public SchedulingListPage(String id, String title, IModel<RescueBean> model) {
        super(id, title, model);
        RescueBean rescueBean = (RescueBean) model.getObject();
        long entityId = rescueBean.getId();
        List<SchedulingBean> list = schedulingBeanService.getOnSchedulingBeanByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<SchedulingBean> lv = new PageableListView<SchedulingBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<SchedulingBean> item) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                final SchedulingBean onDutyBean = item.getModelObject();
                item.add(new Label("date", format.format(onDutyBean.getDate())));
                item.add(new Label("person", onDutyBean.getPerson()));
                item.add(new Label("tel", onDutyBean.getTel()));
                item.add(new Label("expertise", onDutyBean.getExpertise()));
                item.add(new Label("remarks", onDutyBean.getRemarks()));
            }
        };
        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
}
