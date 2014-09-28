package com.daren.danger.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.danger.api.biz.IDangerBeanService;
import com.daren.danger.entities.DangerBean;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
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
 * @类描述：危化品列表
 * @创建人：xukexin
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DangerListPage extends BasePanel {

    MajorDangerSourceDataProvider provider = new MajorDangerSourceDataProvider();


    @Inject
    private IDangerBeanService majorDangerSourceService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public DangerListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<DangerBean> listView = new DataView<DangerBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<DangerBean> item) {
                final DangerBean dangerBean = item.getModelObject();
                item.add(new Label("chinese_name", dangerBean.getChinese_name()));
                item.add(new Label("english_name", dangerBean.getEnglish_name()));
                item.add(new Label("formula", dangerBean.getFormula()));
                item.add(new Label("CAS_num", dangerBean.getCAS_num()));
                AjaxLink check_name = getToCreatePageLink("check_name", dangerBean);
                item.add(check_name);
                String oper_name =isEnt ?"查看": "修改";
                check_name.add(new Label("oper_name", oper_name));
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
                        majorDangerSourceService.deleteEntity(dangerBean.getId());
                        target.add(table);
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        this.setVisible(!isEnt);
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



    private AjaxButton getToCreatePageAjaxButton(String wicketId, final DangerBean dangerBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(dangerBean, target);
            }
            /**
             * 如果为企业用户则隐藏
             * @param behavior
             */
            @Override
            public void onConfigure(JQueryBehavior behavior) {
                super.onConfigure(behavior);
                this.setVisible(!isEnt);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final DangerBean dangerBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(dangerBean, target);
            }
            /**
             * 如果为企业用户则隐藏
             */
            @Override
            public void onConfigure() {
                super.onConfigure();
//                this.setVisible(!isEnt);

            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(DangerBean dangerBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorDangerSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<DangerBean> majorDangerSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new DangerBean()));
        TextField textField = new TextField("chinese_name");

        majorDangerSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorDangerSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorDangerSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorDangerSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DangerBean dangerBean = (DangerBean) form.getModelObject();
                provider.setDangerBean(dangerBean);
                target.add(table);
            }
        };
        majorDangerSourceBeanForm.add(findButton);
    }


    class MajorDangerSourceDataProvider extends ListDataProvider<DangerBean> {
        private DangerBean dangerBean = null;

        public void setDangerBean(DangerBean dangerBean) {
            this.dangerBean = dangerBean;
        }

        @Override
        protected List<DangerBean> getData() {
            if (dangerBean == null || null == dangerBean.getChinese_name() || "".equals(dangerBean.getChinese_name().trim()))
                return majorDangerSourceService.getAllEntity();
            else {
                return majorDangerSourceService.queryDangerSource(dangerBean);
            }
        }
    }
}
