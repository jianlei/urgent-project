package com.daren.enterprise.webapp.wicket.page;


import com.daren.core.web.wicket.BasePage;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBeanImpl;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterprisePage extends BasePage {
    @Named
    @Inject
    @Reference(id = "enterpriseService", serviceInterface = IEnterpriseBeanService.class)
    private IEnterpriseBeanService enterpriseBeanService;

    Label label1 = new Label("pageName1", "添加产品");
    Label label2 = new Label("pageName2", "编辑产品");
    final DataView<EnterpriseBeanImpl> brandBeanDataView;

    public EnterprisePage() {
        label1.setVisible(true);
        label2.setVisible(false);
        add(label1);
        add(label2);

        final Form<EnterpriseBeanImpl> form = new Form<EnterpriseBeanImpl>("add_brand_form", new CompoundPropertyModel<EnterpriseBeanImpl>(new EnterpriseBeanImpl()));
        add(form);
        AjaxFormValidatingBehavior.addToAllFormComponents(form, "keydown", Duration.ONE_SECOND);

        TextField formName = new TextField("name");
        TextField formSummary = new TextField("summary");

        form.add(formName);
        form.add(formSummary);

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                EnterpriseBeanImpl brandBean = (EnterpriseBeanImpl) getParent().getDefaultModelObject();

                setResponsePage(EnterprisePage.class);
                label1.setVisible(true);
                label2.setVisible(false);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                System.out.println("onError");
            }
        });

        add(new Link("reset_form_1") {
            @Override
            public void onClick() {
                form.setModelObject(new EnterpriseBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_form_2") {
            @Override
            public void onClick() {
                form.setModelObject(new EnterpriseBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });


        //填充列表
        List<EnterpriseBeanImpl> brandBeanList = enterpriseBeanService.getAllEntity();
        ListDataProvider<EnterpriseBeanImpl> listDataProvider = new ListDataProvider<EnterpriseBeanImpl>(brandBeanList);
        brandBeanDataView = new DataView<EnterpriseBeanImpl>("brandRow", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<EnterpriseBeanImpl> item) {
                final EnterpriseBeanImpl brandBean;
                brandBean = item.getModelObject();
                item.setOutputMarkupId(true);
                item.add(new Label("name", brandBean.getName()));


                final Label state = new Label("state", new PropertyModel<Integer>(brandBean, "state"));
                state.setOutputMarkupId(true);
                item.add(state);

                item.add(new Link("edit") {
                    @Override
                    public void onClick() {
                        EnterpriseBeanImpl releaseBrand = (EnterpriseBeanImpl) getParent().getDefaultModelObject();
                        form.setModelObject(releaseBrand);
                    }
                });
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
                        EnterpriseBeanImpl releaseBrand = (EnterpriseBeanImpl) getParent().getDefaultModelObject();
                        enterpriseBeanService.deleteEntity(releaseBrand.getId());
                        setResponsePage(EnterprisePage.class);
                    }
                });

                item.add(new AjaxLink("editResource") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        EnterpriseBeanImpl bean = (EnterpriseBeanImpl) getParent().getDefaultModelObject();
                        PageParameters pageParameters = new PageParameters();
                        pageParameters.add("brandId", bean.getId());
//                        setResponsePage(EnterpriseBeanImpl.class,pageParameters);
                    }
                });
                item.add(new AjaxLink("release") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        EnterpriseBeanImpl releaseBrand = item.getModelObject();
//                        releaseBrand.setState("待审核");
                        /*EnterpriseBeanImpl releaseBran2= enterpriseBeanService.getEntity(releaseBrand);
                        item.getModelObject().setRevision(releaseBran2.getRevision());*/
                        target.add(item);
                    }


                });


                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        };
        brandBeanDataView.setOutputMarkupId(true);
        add(brandBeanDataView);
    }
}