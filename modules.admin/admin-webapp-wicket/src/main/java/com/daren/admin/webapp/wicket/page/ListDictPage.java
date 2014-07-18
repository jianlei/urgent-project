package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.entities.DictBeanImpl;
import com.daren.core.util.JNDIHelper;
import com.daren.core.web.wicket.BasePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
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
import org.apache.wicket.util.time.Duration;

import java.io.IOException;
import java.util.List;

/**
 * @类描述：字典列表页面
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:46
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ListDictPage extends BasePage {
    //注入字典业务服务
    /*@Named
    @Inject
    @Reference(id = "roleBeanService", serviceInterface = IRoleBeanService.class)*/
    private IDictBeanService dictBeanService;


    Label label1 = new Label("pageName1", "添加字典");
    Label label2 = new Label("pageName2", "编辑字典");

    public ListDictPage() {
        try {
            dictBeanService = JNDIHelper.getJNDIServiceForName(IDictBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        label1.setVisible(true);
        label2.setVisible(false);
        add(label1);
        add(label2);

        final Form<DictBeanImpl> form = new Form<DictBeanImpl>("add_role_form", new CompoundPropertyModel<DictBeanImpl>(new DictBeanImpl()));
        add(form);
        AjaxFormValidatingBehavior.addToAllFormComponents(form, "keydown", Duration.ONE_SECOND);

        TextField formLabel = new TextField("label");
        TextField formValue = new TextField("value");
        TextField formType = new TextField("type");
        TextField formDescription = new TextField("description");
        TextField formSort = new TextField("sort");


        form.add(formLabel);
        form.add(formValue);
        form.add(formType);
        form.add(formDescription);
        form.add(formSort);
        formLabel.setRequired(true);
        formValue.setRequired(true);
        formType.setRequired(true);
        formDescription.setRequired(true);
        formSort.setRequired(true);

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                DictBeanImpl dictBean = (DictBeanImpl) getParent().getDefaultModelObject();
                dictBeanService.saveEntity(dictBean);
                setResponsePage(ListDictPage.class);
                label1.setVisible(true);
                label2.setVisible(false);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                System.out.println("role onError");
            }
        });

        add(new Link("reset_form_1") {
            @Override
            public void onClick() {
                form.setModelObject(new DictBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_form_2") {
            @Override
            public void onClick() {
                form.setModelObject(new DictBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });


        List<DictBeanImpl> roleCodeList = dictBeanService.getAllEntity();
        ListDataProvider<DictBeanImpl> listDataProvider = new ListDataProvider<DictBeanImpl>(roleCodeList);
        add(new DataView<DictBeanImpl>("roleRows", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<DictBeanImpl> item) {
                final DictBeanImpl dictBean;
                dictBean = item.getModelObject();

                item.add(new Label("label", dictBean.getLabel()));
                item.add(new Label("value", dictBean.getValue()));
                item.add(new Label("type", dictBean.getType()));
                item.add(new Label("description", dictBean.getDescription()));
                item.add(new Label("sort", dictBean.getSort()));

                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
                item.add(new Link("edit") {
                    @Override
                    public void onClick() {
                        form.setModelObject((DictBeanImpl) getParent().getDefaultModelObject());
                        label1.setVisible(false);
                        label2.setVisible(true);
                    }
                });
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
                        dictBeanService.deleteEntity(dictBean.getId());
                        setResponsePage(ListDictPage.class);
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
        });
    }


}
