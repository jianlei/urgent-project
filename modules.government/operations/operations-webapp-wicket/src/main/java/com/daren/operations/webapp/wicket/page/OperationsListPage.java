package com.daren.operations.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.operations.api.biz.IOperationsService;
import com.daren.operations.entities.OperationsBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

public class OperationsListPage extends BasePanel {

    OperationsDataProvider provider = new OperationsDataProvider();

    @Inject
    private IOperationsService operationsService;

    public OperationsListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<OperationsBean> listView = new DataView<OperationsBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<OperationsBean> item) {
                final OperationsBean operationsBean = item.getModelObject();
                item.add(new Label("receiveDate", operationsBean.getReceiveDate()));
                item.add(new Label("startDate", operationsBean.getStartDate()));
                item.add(new Label("endDate", operationsBean.getEndDate()));
                item.add(new Label("reviewDate", operationsBean.getReviewDate()));
                item.add(new Label("code", operationsBean.getCode()));
                item.add(new Label("name", operationsBean.getName()));
                item.add(new Label("workType", operationsBean.getWorkType()));
                item.add(new Label("operationProject", operationsBean.getOperationProject()));
                item.add(new Label("enterpriseId", operationsBean.getEnterpriseId()));
                item.add(getToCreatePageLink("check_name", operationsBean));

            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final OperationsBean operationsBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(operationsBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final OperationsBean operationsBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(operationsBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(OperationsBean operationsBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final OperationsDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<OperationsBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new OperationsBean()));
        TextField textField = new TextField("name");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                OperationsBean operationsBean = (OperationsBean) form.getModelObject();
                provider.setOperationsBean(operationsBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class OperationsDataProvider extends ListDataProvider<OperationsBean> {
        private OperationsBean operationsBean = null;

        public void setOperationsBean(OperationsBean operationsBean) {
            this.operationsBean = operationsBean;
        }

        @Override
        protected List<OperationsBean> getData() {
            if (operationsBean == null || null == operationsBean.getName() || "".equals(operationsBean.getName().trim()))
                return operationsService.getAllEntity();
            else {
                return operationsService.getAllEntity();
            }
        }
    }
}
