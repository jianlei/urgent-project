package com.daren.admin.webapp.wicket.page;


import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.FragmentDialog;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButton;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.ToolbarAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.admin.entities.UserBeanImpl;
import com.daren.admin.webapp.wicket.data.UserProvider;
import com.daren.core.web.wicket.BasePage;
import com.daren.core.web.wicket.PermissionConstant;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @类描述：用户管理维护页面类
 * @创建人：sunlf
 * @创建时间：2014-04-26 上午11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UserPage extends BasePage {
    @Named
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)
    private static IUserBeanService userBeanService;

    Label label1 = new Label("pageName1", "添加用户");
    Label label2 = new Label("pageName2", "编辑用户");
    DataTable<UserBeanImpl> table = null;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        label1.setVisible(true);
        label2.setVisible(false);
        add(label1);
        add(label2);
        final WebMarkupContainer wmc = new WebMarkupContainer("dataContainer");
        wmc.setOutputMarkupId(true);
        add(wmc);


        final UserProvider userProvider = new UserProvider();


        final Form<UserBeanImpl> form = new Form<UserBeanImpl>("add_user_form", new CompoundPropertyModel<UserBeanImpl>(new UserBeanImpl()));
        add(form);
        // AjaxFormValidatingBehavior.addToAllFormComponents(form, "keydown", Duration.ONE_SECOND);

        TextField formUserName = new TextField("name");
        TextField formLoginName = new TextField("loginName");
        PasswordTextField formPassword = new PasswordTextField("password");
        TextField formEmail = new TextField("email");
        TextField formPhone = new TextField("phone");
        TextField formMobile = new TextField("mobile");

        form.add(formUserName);
        form.add(formPassword);
        form.add(formEmail);
        form.add(formLoginName);
        form.add(formPhone);
        form.add(formMobile);
//        form.add(new ParsleyFormBehavior());

        /*ComponentFeedbackMessageFilter filter = new
                ComponentFeedbackMessageFilter(form);
//        final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");*/
//        FeedbackPanel feedback = new FeedbackPanel("feedback");
        final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
//        feedback.setEscapeModelStrings(false);
        add(feedback.setOutputMarkupId(true));


        /*final FeedbackPanel feedbackTable = new JQueryFeedbackPanel("feedbackTable");
        wmc.add(feedbackTable.setOutputMarkupId(true));*/

        final FragmentDialog<String> dialog = new FragmentDialog<String>("dialog", "Delete Item?", Model.of("")) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                if (button.match(LBL_YES)) {
                    String value = this.getModelObject();
                    userBeanService.deleteEntity(Long.parseLong(value));
                    info("删除成功.");
                    userProvider.updateData();
                    // target.add(wmc);
                    table.refresh(target);
                    this.info("deleting #" + this.getModelObject());
                    // TODO: delete the object
//                    target.add(feedbackTable);
                }
            }

            @Override
            protected List<DialogButton> getButtons() {
                return DialogButtons.YES_NO.toList();
            }

            @Override
            protected Fragment newFragment(String id) {
                return new Fragment(id, "dialog-delete", wmc);

            }


        };

        IDataProvider<UserBeanImpl> provider = newDataProvider();
        List<IColumn> columns = newColumnList();

        Options options = new Options();
        options.set("height", 430);
        options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
        options.set("toolbar", "[ { text: '新增', name:'OK'} ]"); // edit is a built-in command so it already has an icon.
        options.set("selectable", Options.asString("multiple"));
        options.set("columnMenu", true);
        options.set("groupable", true);
        /*options.set("sortable", true);
        options.set("groupable", true);
        options.set("columnMenu", true);*/

        table = new DataTable<UserBeanImpl>("datatable", columns, userProvider, 5, options) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target, ColumnButton button, String value) {

                if (button.match("删除")) {
                    dialog.setModelObject(value);
                    dialog.open(target);

                    // target.appendJavaScript(String.format("var grid = jQuery('%s').data('kendoGrid'); grid.dataSource.read(); grid.refresh();", JQueryWidget.getSelector(this)));

//                    target.add(wmc);
                }
                if (button.match("修改")) {

                    UserBeanImpl userBean = (UserBeanImpl) userBeanService.getEntity(Long.parseLong(value));
                    form.setModelObject(userBean);

                    label1.setVisible(false);
                    label2.setVisible(true);
                    info("修改成功.");
                }
                //target.add(wmc);
            }


            @Override
            protected JQueryAjaxBehavior newToolbarAjaxBehavior(IJQueryAjaxAware source) {
                return new ToolbarAjaxBehavior(source, "id"); // 'id' stand for your pk field
            }

           /* @Override
            protected ColumnButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button) {
                return super.newButtonAjaxBehavior(source, button);
            }*/


        };

        //table.setOutputMarkupId(true);
        wmc.add(table);
        /*final Form<?> formTable = new Form<Void>("formTable");
        wmc.add(formTable);
        formTable.add(table);*/
        wmc.add(dialog.setOutputMarkupId(true));
        /*formTable.add(new ConfirmButton("button", "Submit", "Please confirm", "Do you confirm?") {

            private static final long serialVersionUID = 1L;

            @Override
            public void onError()
            {
                this.error("Validation failed!");
            }

            @Override
            public void onSubmit()
            {
                this.info("Model object: ");
            }
        });
       */ // Button //
        /*final AjaxButton refreshBtn = new com.googlecode.wicket.kendo.ui.form.button.AjaxButton("refresh") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                table.refresh(target);
            }
        };*/
//        formTable.add(refreshBtn);

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                UserBeanImpl userBean = (UserBeanImpl) getParent().getDefaultModelObject();
                userBeanService.saveEntity(userBean);
//                setResponsePage(UserPage.class);
                userProvider.updateData();

                label1.setVisible(true);
                label2.setVisible(false);
                target.appendJavaScript("$('#form_modal2').css({'display':'none'});" +
                        "$('div.modal-backdrop.fade.in').removeClass().addClass('div.modal-backdrop.fade.out')");
                //table.refresh(target);
//                refreshBtn.onSubmit();
//                target.add(wmc);

            }
        });

        add(new Link("reset_form_1") {
            @Override
            public void onClick() {
                form.setModelObject(new UserBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_form_2") {
            @Override
            public void onClick() {
                form.setModelObject(new UserBeanImpl());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        //填充数据列表
//        List<UserBeanImpl> persons = userBeanService.getAllEntity();
//        ListDataProvider<UserBeanImpl> listDataProvider = new ListDataProvider<UserBeanImpl>(persons);


        /*DataView dataView =
                new DataView<UserBeanImpl>("userRows", userProvider) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void populateItem(final Item<UserBeanImpl> item) {
                        final UserBeanImpl person;
                        person = item.getModelObject();
                        setDefaultModel(new CompoundPropertyModel(person));

                        item.add(new Label("userName", person.getName()));
                        item.add(new Label("createDate", DateUtil.convertDateToString(person.getCreationDate(), DateUtil.shortSdf)));
                        item.add(new Label("email", person.getEmail()));
                        item.add(new Label("loginName", person.getLoginName()));
                        item.add(new Label("phone", person.getPhone()));
                        item.add(new Label("mobile", person.getMobile()));
                        List<RoleBeanImpl> roleBeanList = person.getRoleList();
                        String roleList = getRoleList(roleBeanList);
                        item.add(new Label("role", roleList));
                        item.add(new Link("edit") {
                            @Override
                            public void onClick() {
                                UserBeanImpl userBean = (UserBeanImpl) getDefaultModelObject();
                                form.setModelObject(userBean);
                                label1.setVisible(false);
                                label2.setVisible(true);
                            }

                    *//*@Override
                    public void onClick(AjaxRequestTarget target) {
                        UserBeanImpl userBean = (UserBeanImpl) getDefaultModelObject();
                        form.setModelObject(userBean);
                        label1.setVisible(false);
                        label2.setVisible(true);
                    }*//*

                            @Override
                            public boolean isVisible() {
                                return SecurityUtils.getSubject().isPermitted(PermissionConstant.SYS_USER_EDIT);
                            }
                        }).setOutputMarkupId(true);

                        AjaxLink delUser = new AjaxLink<UserBeanImpl>("del") {
                            @Override
                            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                                super.updateAjaxAttributes(attributes);
                                attributes.getAjaxCallListeners().add(new AjaxCallListener() {
                                    @Override
                                    public CharSequence getBeforeHandler(Component component) {
                                        return "if(!confirm('确认删除该用户？')) e.preventDefault(); return false;";
                                    }
                                });
                            }

                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                userBeanService.deleteEntity(person.getId());
                                userProvider.updateData();
//                        persons = userBeanService.getAllEntity();
                                //UserPage.this.persons.remove(person);
//                        persons.remove(person);
                                target.add(wmc);

                            }

                   *//*@Override
                   public void onClick() {
                       userBeanService.deleteEntity(person.getId());
                       userProvider.updateData();
                   }*//*

                            @Override
                            public boolean isVisible() {
                                return SecurityUtils.getSubject().isPermitted(PermissionConstant.SYS_USER_DELETE);
                            }
                        };
                        delUser.setOutputMarkupId(true);
                        item.add(delUser);

                        item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public String getObject() {
                                return (item.getIndex() % 2 == 1) ? "even" : "odd";
                            }
                        }));
                    }
                };
        dataView.setOutputMarkupId(true);
        wmc.add(dataView);*/

        //test for jquery table


        //add button
        MarkupContainer addUser = new WebMarkupContainer("addUser") {
            @Override
            public boolean isVisible() {
                return SecurityUtils.getSubject().isPermitted(PermissionConstant.SYS_USER_ADD);
            }
        };
        addUser.setOutputMarkupId(true);
        add(addUser);
    }

    /**
     * 生成roleList列表
     *
     * @param roleBeanList
     * @return
     */
    private String getRoleList(List<RoleBeanImpl> roleBeanList) {
        String value = "";
        for (RoleBeanImpl roleBean : roleBeanList) {
            value = value + roleBean.getName() + ",";
        }
        //截掉最后一个“，”
        if (value.length() > 1)
            value = value.substring(0, value.length() - 1);
        return value;
    }

    private static IDataProvider<UserBeanImpl> newDataProvider() {
        return new ListDataProvider<UserBeanImpl>(userBeanService.getAllUser());
    }

    private List<IColumn> newColumnList() {
        List<IColumn> columns = new ArrayList<IColumn>();

        /*List<RoleBeanImpl> roleBeanList = person.getRoleList();
        String roleList = getRoleList(roleBeanList);*/


        columns.add(new PropertyColumn("ID", "id") {
            @Override
            public boolean isVisible() {
                return false;
            }
        });
        columns.add(new PropertyColumn("用户名", "name"));
        columns.add(new PropertyColumn("登录名", "loginName"));
        columns.add(new PropertyColumn("邮箱", "email"));
        columns.add(new PropertyColumn("电话", "phone"));
        columns.add(new PropertyColumn("手机", "mobile"));
        columns.add(new PropertyColumn("角色") {
            @Override
            public Object getValue(Object object) {
                UserBeanImpl userBean = (UserBeanImpl) object;
                List<RoleBeanImpl> roleBeanList = userBean.getRoleList();
                String roleList = getRoleList(roleBeanList);
                return roleList;
            }
        });

        /*columns.add(new PropertyColumn("Price", "price", 70) {

            private static final long serialVersionUID = 1L;

            @Override
            public String getFormat() {
                return "{0:c2}";
            }
        });*/

        columns.add(new CommandsColumn("操作", 160) {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ColumnButton> newButtons() {
                ColumnButton deleteButton = new ColumnButton("删除", "id") {

                };

                ColumnButton editButton = new ColumnButton("修改", "id") {
                    @Override
                    public String getProperty() {
                        return super.getProperty();
                    }
                };
                return Arrays.asList(editButton, deleteButton);
            }


        });

        return columns;
    }


}


