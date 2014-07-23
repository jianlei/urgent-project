package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IOfficeBeanService;
import com.daren.admin.entities.OfficeBeanImpl;
import com.daren.core.util.JNDIHelper;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.tree.table.*;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：机构列表页面
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:46
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ListOfficePage extends BasePanel {
    //注入机构业务服务
    /*@Named
    @Inject
    @Reference(id = "roleBeanService", serviceInterface = IRoleBeanService.class)*/
    private IOfficeBeanService officeBeanService;
    private final TreeTable tree;


    Label label1 = new Label("pageName1", "添加机构");
    Label label2 = new Label("pageName2", "编辑机构");

    public ListOfficePage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        try {
            officeBeanService = JNDIHelper.getJNDIServiceForName(IOfficeBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        label1.setVisible(true);
        label2.setVisible(false);
        add(label1);
        add(label2);

        final Form<OfficeBeanImpl> form = new Form<OfficeBeanImpl>("add_office_form", new CompoundPropertyModel<OfficeBeanImpl>(new OfficeBeanImpl()));
        add(form);
        AjaxFormValidatingBehavior.addToAllFormComponents(form, "keydown", Duration.ONE_SECOND);

        TextField formCode = new TextField("code");
        TextField formName = new TextField("name");
        TextField formType = new TextField("type");
        TextField formGrade = new TextField("grade");
        TextField formAddress = new TextField("address");
        TextField formZipCode = new TextField("zipCode");
        TextField formMaster = new TextField("master");
        TextField formPhone = new TextField("phone");
        TextField formFax = new TextField("fax");
        TextField formEmail = new TextField("email");

        form.add(formCode);
        form.add(formName);
        form.add(formType);
        form.add(formGrade);
        form.add(formAddress);
        form.add(formZipCode);
        form.add(formMaster);
        form.add(formPhone);
        form.add(formFax);
        form.add(formEmail);
        formAddress.setRequired(true);
        formCode.setRequired(true);
        formEmail.setRequired(true);
        formGrade.setRequired(true);
        formMaster.setRequired(true);
        formName.setRequired(true);
        formPhone.setRequired(true);
        formZipCode.setRequired(true);
        formType.setRequired(true);
        formFax.setRequired(true);
        formEmail.add(EmailAddressValidator.getInstance());
        formZipCode.add(StringValidator.minimumLength(6));
        formPhone.add(new PatternValidator("[0-9]"));

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                OfficeBeanImpl OfficeBean = (OfficeBeanImpl) getParent().getDefaultModelObject();
                officeBeanService.saveEntity(OfficeBean);
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
                form.setModelObject(new OfficeBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_form_2") {
            @Override
            public void onClick() {
                form.setModelObject(new OfficeBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });


        List<OfficeBeanImpl> roleCodeList = officeBeanService.getAllEntity();
        ListDataProvider<OfficeBeanImpl> listDataProvider = new ListDataProvider<OfficeBeanImpl>(roleCodeList);
        add(new DataView<OfficeBeanImpl>("roleRows", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<OfficeBeanImpl> item) {
                final OfficeBeanImpl OfficeBean;
                OfficeBean = item.getModelObject();

                item.add(new Label("code", OfficeBean.getCode()));
                item.add(new Label("name", OfficeBean.getName()));
                item.add(new Label("type", OfficeBean.getType()));
                item.add(new Label("grade", OfficeBean.getGrade()));
                item.add(new Label("address", OfficeBean.getAddress()));
                item.add(new Label("zipCode", OfficeBean.getZipCode()));
                item.add(new Label("master", OfficeBean.getMaster()));
                item.add(new Label("phone", OfficeBean.getPhone()));
                item.add(new Label("fax", OfficeBean.getFax()));
                item.add(new Label("email", OfficeBean.getEmail()));

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
                        form.setModelObject((OfficeBeanImpl) getParent().getDefaultModelObject());
                        label1.setVisible(false);
                        label2.setVisible(true);
                    }
                });
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
                        officeBeanService.deleteEntity(OfficeBean.getId());
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

        //创建treetable

        IColumn columns[] = new IColumn[]{
                new PropertyTreeColumn<String>(new ColumnLocation(ColumnLocation.Alignment.MIDDLE, 8,
                        ColumnLocation.Unit.PROPORTIONAL), "Tree Column (middle)", "userObject.property1"),
                new PropertyRenderableColumn<String>(
                        new ColumnLocation(ColumnLocation.Alignment.LEFT, 7, ColumnLocation.Unit.EM), "L2", "userObject.property2"),
                new PropertyRenderableColumn<String>(new ColumnLocation(ColumnLocation.Alignment.MIDDLE, 2,
                        ColumnLocation.Unit.PROPORTIONAL), "M1", "userObject.property3"),
                new PropertyRenderableColumn<String>(new ColumnLocation(ColumnLocation.Alignment.MIDDLE, 2,
                        ColumnLocation.Unit.PROPORTIONAL), "M2", "userObject.property4"),
                new PropertyRenderableColumn<String>(new ColumnLocation(ColumnLocation.Alignment.MIDDLE, 3,
                        ColumnLocation.Unit.PROPORTIONAL), "M3", "userObject.property5"),
                new PropertyRenderableColumn<String>(
                        new ColumnLocation(ColumnLocation.Alignment.RIGHT, 8, ColumnLocation.Unit.EM), "R1", "userObject.property6")
        };

        tree = new TreeTable("treeTable", createTreeModel(), columns);
        tree.getTreeState().setAllowSelectMultiple(true);
        add(tree);
        tree.getTreeState().collapseAll();
    }


    protected TreeModel createTreeModel() {
        List<Object> l1 = new ArrayList<Object>();
        l1.add("test 1.1");
        l1.add("test 1.2");
        l1.add("test 1.3");
        List<Object> l2 = new ArrayList<Object>();
        l2.add("test 2.1");
        l2.add("test 2.2");
        l2.add("test 2.3");
        List<Object> l3 = new ArrayList<Object>();
        l3.add("test 3.1");
        l3.add("test 3.2");
        l3.add("test 3.3");

        l2.add(l3);

        l2.add("test 2.4");
        l2.add("test 2.5");
        l2.add("test 2.6");

        l3 = new ArrayList<Object>();
        l3.add("test 3.1");
        l3.add("test 3.2");
        l3.add("test 3.3");
        l2.add(l3);

        l1.add(l2);

        l2 = new ArrayList<Object>();
        l2.add("test 2.1");
        l2.add("test 2.2");
        l2.add("test 2.3");

        l1.add(l2);

        l1.add("test 1.3");
        l1.add("test 1.4");
        l1.add("test 1.5");

        return convertToTreeModel(l1);
    }

    private TreeModel convertToTreeModel(List<Object> list) {
        TreeModel model = null;
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new ModelBean("ROOT"));
        add(rootNode, list);
        model = new DefaultTreeModel(rootNode);
        return model;
    }

    private void add(DefaultMutableTreeNode parent, List<Object> sub) {
        for (Object obj : sub) {
            if (obj instanceof List) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new ModelBean(
                        "subtree..."));
                parent.add(child);
                add(child, (List<Object>) obj);
            } else {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new ModelBean(
                        obj.toString()));
                parent.add(child);
            }
        }
    }


}
