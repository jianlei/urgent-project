package com.daren.production.webapp.wicket.page;

import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
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

public class ProductionListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    ProductionDataProvider provider = new ProductionDataProvider();
    @Inject
    private IProductionService productionService;
    public ProductionListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        //初始化dialogWrapper
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog", ""));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        DataView<ProductionBean> listView = new DataView<ProductionBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<ProductionBean> item) {
                final ProductionBean productionBean = item.getModelObject();
                item.add(new Label("name", productionBean.getName()));
                item.add(new Label("address", productionBean.getAddress()));
                item.add(new Label("head", productionBean.getHead()));
                item.add(new Label("economicsType", productionBean.getEconomicsType()));
                item.add(new Label("scope", productionBean.getScope()));
                item.add(getToCreatePageLink("check_name", productionBean));
                item.add(getToUploadPageLink("upload", productionBean));
                item.add(getDuplicateLink("duplicate", productionBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final ProductionBean productionBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(productionBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final ProductionBean productionBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(productionBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final ProductionBean productionBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", productionBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final ProductionBean productionBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", productionBean, "upload");
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(ProductionBean productionBean, AjaxRequestTarget target) {
    }

    private void createDialog(AjaxRequestTarget target, final String title, ProductionBean productionBean, String type) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", title, productionBean.getId(), type, "production") {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final ProductionDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<ProductionBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new ProductionBean()));
        TextField textField = new TextField("name");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ProductionBean productionBean = (ProductionBean) form.getModelObject();
                provider.setProductionBean(productionBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class ProductionDataProvider extends ListDataProvider<ProductionBean> {
        private ProductionBean productionBean = null;
        public void setProductionBean(ProductionBean productionBean) {
            this.productionBean = productionBean;
        }
        @Override
        protected List<ProductionBean> getData() {
            if (productionBean == null || null == productionBean.getName() || "".equals(productionBean.getName().trim()))
                return productionService.getAllEntity();
            else {
                return productionService.getAllEntity();
            }
        }
    }
}
