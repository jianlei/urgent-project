package com.daren.production.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
import com.daren.production.webapp.wicket.Const;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
 * @类描述：安全生产许可证(非煤矿矿山企业)
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
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private TaskService taskService;
    @Inject
    private IUserBeanService userBeanService;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    private String phoneNumber = null;
    final WebMarkupContainer wmc;
    public ProductionListPage(final String id, final WebMarkupContainer wmc, String phone) {
        super(id, wmc);
        this.wmc = wmc;
        phoneNumber = phone;
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
        this.add(feedbackPanel);
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        DataView<ProductionBean> listView = new DataView<ProductionBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<ProductionBean> item) {
                final ProductionBean productionBean = item.getModelObject();
                item.add(new Label("name", productionBean.getName()));
                item.add(new Label("head", productionBean.getHead()));
                item.add(new Label("phone", productionBean.getPhone()));
                item.add(new Label("address", productionBean.getAddress()));
                item.add(new Label("economicsType", productionBean.getEconomicsType()));
                item.add(new Label("scope", productionBean.getScope()));
                item.add(new Label("linkHandle", productionBean.getLinkHandle()));
                item.add(getToCreatePageLink("check_name", productionBean));
                item.add(getToUploadPageLink("upload", productionBean));
                item.add(getDuplicateLink("duplicate", productionBean));
                item.add(getSubmitLink("submit", productionBean));
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
        if(productionBean.getLinkHandle()!=null&&!"".equals(productionBean.getLinkHandle())){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final ProductionBean productionBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传附件", productionBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getSubmitLink(String wicketId, final ProductionBean productionBean){
        AjaxLink alinkSubmit = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String bizKey= Const.PROCESS_KEY + ":" + userBeanService.getCurrentUserLoginName() + ":" + productionBean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(userBeanService.getCurrentUserLoginName());
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(Const.PROCESS_KEY, bizKey);
                    productionBean.setProcessInstanceId(instance.getProcessInstanceId());
                    Task task=taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
                    productionBean.setLinkHandle(task.getName());
                    productionService.saveEntity(productionBean);
                    feedbackPanel.info("安全生产许可证(非煤矿矿山企业),启动成功！");
                }catch (Exception e){
                    feedbackPanel.info("安全生产许可证(非煤矿矿山企业),启动失败！");
                }finally {
                    identityService.setAuthenticatedUserId(null);
                }
                target.add(feedbackPanel);
                target.add(wmc);
            }
        };
        if(productionBean.getLinkHandle()!=null&&!"".equals(productionBean.getLinkHandle())){
            alinkSubmit.setVisible(false);
        }
        return alinkSubmit;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final ProductionBean productionBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传附件", productionBean, "upload");
            }
        };
        if(productionBean.getLinkHandle()!=null&&!"".equals(productionBean.getLinkHandle())){
            ajaxLink.setVisible(false);
        }
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
            return productionService.getProductionByLoginName(userBeanService.getCurrentUserLoginName());
        }
    }
}
