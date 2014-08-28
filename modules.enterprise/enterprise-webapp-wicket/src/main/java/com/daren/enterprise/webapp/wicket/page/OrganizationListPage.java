package com.daren.enterprise.webapp.wicket.page;

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
import java.util.List;

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
    @Reference(id = "areaBeanService", serviceInterface = IOrganizationBeanService.class)
    private IOrganizationBeanService organizationBeanService;

    public OrganizationListPage(final String id, final WebMarkupContainer wmc){
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");      //布局容器
        add(table.setOutputMarkupId(true));
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
                    item.add(new Label("JGLXBJ", returnJglx(organizationBean.getJglxbj())));
                    String jgbmbj = organizationBean.getJgbmbj();
                    if("0".equals(jgbmbj)){
                        jgbmbj = "机关";
                    }else if("1".equals(jgbmbj)){
                        jgbmbj = "部门";
                    }else{
                        jgbmbj = "";
                    }
                    item.add(new Label("JGBMBJ", jgbmbj));
                    item.add(new Label("XZQH_DM", organizationBean.getXzqh_dm()));
                    item.add(new Label("CREATETIME", organizationBean.getCreatetime()));
                    String zfbj = organizationBean.getZfbj();
                    if("0".equals(zfbj)){
                        zfbj = "有效";
                    }else if("1".equals(zfbj)){
                        zfbj = "无效";
                    }else{
                        zfbj = "";
                    }
                    item.add(new Label("ZFBJ", zfbj));
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

    /**
     * 把监管类型的值转换成对应的文字描述
     * @param jglxbj
     * @return
     */
    private String returnJglx(String jglxbj){
        String retStr = "";
        if(jglxbj!=null&&!"".equals(jglxbj)){
            try{
                Integer inte = Integer.parseInt(jglxbj);
                switch (inte){
                    case 1:
                        retStr = "综合";
                        break;
                    case 2:
                        retStr = "行业";
                        break;
                    case 5:
                        retStr = "消防";
                        break;
                    case 6:
                        retStr = "质检";
                        break;
                    case 7:
                        retStr = "公安";
                        break;
                    default:
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return retStr;
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
    private AjaxButton getToCreatePageAjaxButton(String wicketId, final OrganizationBean organizationBean) {
        AjaxButton ajaxButton = new AjaxButton(wicketId) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
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
