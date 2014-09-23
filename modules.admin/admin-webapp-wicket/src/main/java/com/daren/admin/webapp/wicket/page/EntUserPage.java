package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.admin.webapp.wicket.data.UserProvider;
import com.daren.core.web.wicket.BasePage;
import com.daren.core.web.wicket.PermissionConstant;
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
 * @类描述：行管用户
 * @创建人：xukexin
 * @创建时间：2014/9/23 15:03
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EntUserPage extends BasePage {

    @Named
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)
    private static IUserBeanService userBeanService;

    Label label1 = new Label("pageName1", "添加用户");
    Label label2 = new Label("pageName2", "编辑用户");
    DataTable<UserBean> table = null;

    private static IDataProvider<UserBean> newDataProvider() {
        return new ListDataProvider<UserBean>(userBeanService.getAllUser());
    }

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


        final Form<UserBean> form = new Form<UserBean>("add_user_form", new CompoundPropertyModel<UserBean>(new UserBean()));
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

        final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
//        feedback.setEscapeModelStrings(false);
        add(feedback.setOutputMarkupId(true));

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

        IDataProvider<UserBean> provider = newDataProvider();
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

        table = new DataTable<UserBean>("datatable", columns, userProvider, 5, options) {

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
                    UserBean userBean = (UserBean) userBeanService.getEntity(Long.parseLong(value));
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
        };

        //table.setOutputMarkupId(true);
        wmc.add(table);
        wmc.add(dialog.setOutputMarkupId(true));
        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                UserBean userBean = (UserBean) getParent().getDefaultModelObject();
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
                form.setModelObject(new UserBean());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_form_2") {
            @Override
            public void onClick() {
                form.setModelObject(new UserBean());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

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
    private String getRoleList(List<RoleBean> roleBeanList) {
        String value = "";
        for (RoleBean roleBean : roleBeanList) {
            value = value + roleBean.getName() + ",";
        }
        //截掉最后一个“，”
        if (value.length() > 1)
            value = value.substring(0, value.length() - 1);
        return value;
    }

    private List<IColumn> newColumnList() {
        List<IColumn> columns = new ArrayList<IColumn>();
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
                UserBean userBean = (UserBean) object;
                List<RoleBean> roleBeanList = userBean.getRoleList();
                String roleList = getRoleList(roleBeanList);
                return roleList;
            }
        });

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
