package com.daren.enterprise.webapp.wicket.page;

import java.util.List;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterprisePage extends BasePanel {

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    EnterpriseDataProvider provider = new EnterpriseDataProvider();


    public EnterprisePage(final String id, final WebMarkupContainer wmc) {

        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));


        DataView<EnterpriseBean> listView = new DataView<EnterpriseBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<EnterpriseBean> item) {
                {
                    final EnterpriseBean enterpriseBean = item.getModelObject();
                    item.add(getToCreatePageLink(id, wmc, "check_QYMC", enterpriseBean.getId()).add(new Label("QYMC", enterpriseBean.getQymc()))
                    );
                    item.add(new Label("JGFL", enterpriseBean.getJgfl()));
                    item.add(new Label("QYLXFS", enterpriseBean.getQylxfs()));
                    item.add(new Label("MAILADDRESS", enterpriseBean.getMailaddress()));
                    item.add(new Label("ADDRESS_JY", enterpriseBean.getAddress_jy()));


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
                        /*target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+enterpriseBean.getId()+"')");*/
                            enterpriseBeanService.deleteEntity(enterpriseBean.getId());
                            target.add(table);
                        }
                    };
                    item.add(alink.setOutputMarkupId(true));
                }
            }
        };
        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);

        createQuery(table, provider, id, wmc);
    }

    private AjaxLink getToCreatePageLink(final String id, final WebMarkupContainer wmc, String wicketId, final long enterpriseBeanId) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                wmc.removeAll();
                wmc.addOrReplace(new EnterpriseCreatePage(id, wmc, enterpriseBeanId));
                target.add(wmc);
            }
        };
        return ajaxLink;
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final EnterpriseDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<EnterpriseBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new EnterpriseBean()));
        TextField textField = new TextField("qymc");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageLink(id, wmc, "create", -1));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));


        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EnterpriseBean enterpriseBean = (EnterpriseBean) form.getModelObject();
                provider.setEnterpriseBean(enterpriseBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
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
