package com.daren.enterprise.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
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
import java.util.Map;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseListPage extends BasePanel {
    EnterpriseDataProvider provider = new EnterpriseDataProvider();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    @Reference(id = "enterpriseBeanService", serviceInterface = IEnterpriseBeanService.class)
    private IEnterpriseBeanService enterpriseBeanService;

    //注入字典业务服务
   /* @Inject
    @Reference(id = "areaBeanService", serviceInterface = IOrganizationBeanService.class)
    private IOrganizationBeanService organizationBeanService;*/
    /*@Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;*/

 //   final Map<String,String> map;


    public EnterpriseListPage(final String id, final WebMarkupContainer wmc) {

        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));

         final Map<String,String> map =dictBeanService.getDictMap(IDictConstService.ENTERPRISE_JGFL);


        DataView<EnterpriseBean> listView = new DataView<EnterpriseBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<EnterpriseBean> item) {
                {

                    final EnterpriseBean enterpriseBean = item.getModelObject();
                    item.add(new Label("QYMC", enterpriseBean.getQymc()));
                    item.add(new Label("JGFL", map.get(enterpriseBean.getJgfl())));
                    item.add(new Label("QYLXFS", enterpriseBean.getQylxfs()));
                    item.add(new Label("MAILADDRESS", enterpriseBean.getMailaddress()));
                    item.add(new Label("ADDRESS_JY", enterpriseBean.getAddressjy()));

                    item.add(getToCreatePageLink("check_QYMC", enterpriseBean));

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
                            try {
                                enterpriseBeanService.deleteEntity(enterpriseBean.getId());
                                target.add(table);
                                feedbackPanel.info("删除成功！");
                                target.add(feedbackPanel);
                            } catch (Exception e) {
                                feedbackPanel.info("删除失败！");
                                target.add(feedbackPanel);
                            }

                        }
                    };
                    item.add(alink.setOutputMarkupId(true));
                }
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
        initFeedBack();
    }


    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }


    private AjaxLink getToCreatePageAjaxButton(String wicketId, final EnterpriseBean enterpriseBean) {
        IrisIndicatingAjaxLink ajaxButton = new IrisIndicatingAjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(enterpriseBean, target);
            }
        };
        return ajaxButton;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final EnterpriseBean enterpriseBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(enterpriseBean, target);
            }
        };
        return ajaxLink;
    }


    protected void createButtonOnClick(EnterpriseBean enterpriseBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final EnterpriseDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<EnterpriseBean> enterpriseBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new EnterpriseBean()));
        TextField textField = new TextField("qymc");

        enterpriseBeanForm.add(textField.setOutputMarkupId(true));
        enterpriseBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(enterpriseBeanForm.setOutputMarkupId(true));


        AjaxButton findButton = new AjaxButton("find", enterpriseBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EnterpriseBean enterpriseBean = (EnterpriseBean) form.getModelObject();
                provider.setEnterpriseBean(enterpriseBean);
                target.add(table);
            }
        };
        enterpriseBeanForm.add(findButton);
    }


    class EnterpriseDataProvider extends ListDataProvider<EnterpriseBean> {
        private EnterpriseBean enterpriseBean = null;

        public void setEnterpriseBean(EnterpriseBean enterpriseBean) {
            this.enterpriseBean = enterpriseBean;
        }

        @Override
        protected List<EnterpriseBean> getData() {
            if (enterpriseBean == null || null == enterpriseBean.getQymc() || "".equals(enterpriseBean.getQymc().trim()))
                return enterpriseBeanService.getAllEntity();
            else {
                return enterpriseBeanService.queryEnterprise(enterpriseBean);
            }
        }
    }
}
