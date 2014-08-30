package com.daren.enterprise.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.util.DateUtil;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.entities.OrganizationBean;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @类描述：监管机构
 * @创建人：xukexin
 * @创建时间：2014/8/26 14:55
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrganizationListPage extends BasePanel {

    OrganizationDataProvider provider = new OrganizationDataProvider();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    @Reference(id = "organizationBeanService", serviceInterface = IOrganizationBeanService.class)
    private IOrganizationBeanService organizationBeanService;



    public OrganizationListPage(final String id, final WebMarkupContainer wmc){
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");      //布局容器
        add(table.setOutputMarkupId(true));

        //机关部门标记
        final Map<String,String> jgbmbj_map =dictBeanService.getDictMap( IDictConstService.ORGANIZATION_JGBMBJ);
        final Map<String,String> zfbj_map =dictBeanService.getDictMap( IDictConstService.ORGANIZATION_ZFBJ);
        final Map<String,String> jglxbj_map =dictBeanService.getDictMap( IDictConstService.ORGANIZATION_JGLXBJ);

        //循环读取数据并赋值
        DataView<OrganizationBean> listView = new DataView<OrganizationBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<OrganizationBean> item) {
                {
                    final OrganizationBean organizationBean = item.getModelObject();
                    item.add(new Label("MC", organizationBean.getMc()));
                    item.add(new Label("MCJ", organizationBean.getMcj()));
                    item.add(new Label("JGDM", organizationBean.getJgdm()));
                    item.add(new Label("JGLXBJ", jglxbj_map.get(organizationBean.getJglxbj())));
                    item.add(new Label("JGBMBJ", jgbmbj_map.get(organizationBean.getJgbmbj())));
                    item.add(new Label("XZQH_DM", organizationBean.getXzqh_dm()));
                    item.add(new Label("CREATETIME",  new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.convertStringToDate(organizationBean.getCreatetime(), "yyyy-MM-dd"))));
                    item.add(new Label("ZFBJ", zfbj_map.get(organizationBean.getZfbj())));
                    item.add(getToCreatePageLink("check_MC", organizationBean));

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
                                organizationBeanService.deleteEntity(organizationBean.getId());
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

    /**
     * 创建按钮
     * @param wicketId
     * @param organizationBean
     * @return
     */
    private AjaxLink getToCreatePageAjaxButton(String wicketId, final OrganizationBean organizationBean) {
        AjaxLink ajaxButton = new IrisIndicatingAjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(organizationBean, target);
            }
        };
        return ajaxButton;
    }

    /**
     * 创建的ajax方法
     * @param wicketId
     * @param organizationBean
     * @return
     */
    private AjaxLink getToCreatePageLink(String wicketId, final OrganizationBean organizationBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(organizationBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(OrganizationBean organizationBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final OrganizationDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<OrganizationBean> organizationBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new OrganizationBean()));
        TextField textField = new TextField("mc");

        organizationBeanForm.add(textField.setOutputMarkupId(true));
        organizationBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(organizationBeanForm.setOutputMarkupId(true));


        AjaxButton findButton = new AjaxButton("find", organizationBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                OrganizationBean organizationBean = (OrganizationBean) form.getModelObject();
                provider.setOrganizationBean(organizationBean);
                target.add(table);
            }
        };
        organizationBeanForm.add(findButton);
    }
    /**
     * 查询list
     */
    class OrganizationDataProvider extends ListDataProvider<OrganizationBean> {
        private OrganizationBean organizationBean = null;

        public void setOrganizationBean(OrganizationBean organizationBean) {
            this.organizationBean = organizationBean;
        }

        @Override
        protected List<OrganizationBean> getData() {
            //判断名称查询条件是否为空
            if (organizationBean == null || null == organizationBean.getMc() || "".equals(organizationBean.getMc().trim()))
                return organizationBeanService.getAllEntity();
            else {
                return organizationBeanService.queryOrganization(organizationBean);
            }
        }
    }
}
