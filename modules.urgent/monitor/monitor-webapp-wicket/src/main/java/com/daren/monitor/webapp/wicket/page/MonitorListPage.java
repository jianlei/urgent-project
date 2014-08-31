package com.daren.monitor.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.monitor.api.biz.IMonitorBeanService;
import com.daren.monitor.entities.MonitorBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MonitorListPage extends BasePanel {

    MajorMonitorSourceDataProvider provider = new MajorMonitorSourceDataProvider();

    @Inject
    private IMonitorBeanService monitorBeanService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public MonitorListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<MonitorBean> listView = new DataView<MonitorBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<MonitorBean> item) {
                final MonitorBean monitorBean = item.getModelObject();
                item.add(new Label("name", monitorBean.getName()));
                item.add(new Label("ipAddress", monitorBean.getIpAddress()));
                item.add(new Label("port", monitorBean.getPort()));
                item.add(new Label("channel", monitorBean.getChannel()));
                EnterpriseBean enterpriseBean = enterpriseBeanService.getByQyid(monitorBean.getAffiliation());
                item.add(new Label("affiliation", enterpriseBean.getQymc()));//企业名称
                /*item.add(new Label("admin", monitorBean.getAdmin()));//企业名称
                item.add(new Label("password", monitorBean.getPassword()));//企业名称*/
                item.add(getToCreatePageLink("check_name", monitorBean));

                AjaxLink alink = new AjaxLink("del") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('确定要删除么?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        monitorBeanService.deleteEntity(monitorBean.getId());
                        target.add(table);
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final MonitorBean monitorBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(monitorBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final MonitorBean monitorBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(monitorBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(MonitorBean monitorBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorMonitorSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<MonitorBean> majorMonitorSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new MonitorBean()));
        TextField textField = new TextField("name");

        majorMonitorSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorMonitorSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorMonitorSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorMonitorSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                MonitorBean monitorBean = (MonitorBean) form.getModelObject();
                provider.setMonitorBean(monitorBean);
                target.add(table);
            }
        };
        majorMonitorSourceBeanForm.add(findButton);
    }


    class MajorMonitorSourceDataProvider extends ListDataProvider<MonitorBean> {
        private MonitorBean monitorBean = null;

        public void setMonitorBean(MonitorBean monitorBean) {
            this.monitorBean = monitorBean;
        }

        @Override
        protected List<MonitorBean> getData() {
            if (monitorBean == null || null == monitorBean.getName() || "".equals(monitorBean.getName().trim()))
                return monitorBeanService.getAllEntity();
            else {
                return monitorBeanService.queryMonitor(monitorBean);
            }
        }
    }
}
