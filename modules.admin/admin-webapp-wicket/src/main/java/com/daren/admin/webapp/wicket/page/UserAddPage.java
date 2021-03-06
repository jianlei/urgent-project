package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.entities.OrganizationBean;
import com.daren.enterprise.webapp.component.form.OrgnizationSelect2Choice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    用户新增或修改页面
 * 创建人:    sunlf
 * 创建时间:  2014/8/5 13:44
 * 修改人:    sunlf
 * 修改时间:  2014/8/5 13:44
 * 修改备注:  [说明本次修改内容]
 */
public class UserAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    OrgnizationSelect2Choice orgnizationSelect2Choice;
    PasswordTextField pwd;
    PasswordTextField pwdConfirm;
    Form<UserBean> userForm;
    //EnterpriseBean enterpriseBean = new EnterpriseBean();
    //EnterpriseSelect2Choice enterpriseSelect2Choice;
    private String confirPwd;
    private boolean isAdd;
    private String temp_password;
    @Inject
    @Reference(id = "userBeanService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;
    @Inject
    @Reference(id = "roleBeanService", serviceInterface = IRoleBeanService.class)
    private IRoleBeanService roleBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    /*@Inject
    private IEnterpriseBeanService enterpriseBeanService;*/
    //存储已有的角色列表
    private ArrayList<String> roleSelect = new ArrayList<String>();
    private OrganizationBean organizationBean = new OrganizationBean();
    @Inject
    private IOrganizationBeanService organizationBeanService;

    public UserAddPage(String id, String type, IModel<UserBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null) {
            isAdd = true;//new model
            initForm(Model.of(new UserBean()));
        } else {
            isAdd = false;//edit model
            initForm(model);
            temp_password=model.getObject().getPassword();
            UserBean userBean = model.getObject();
            Long jgdm=new Long(userBean.getJgdm());
            organizationBean= (OrganizationBean) organizationBeanService.getEntity(jgdm);
            /*long enterpriseId = new Long(userBean.getQyid());
            enterpriseBean = (EnterpriseBean) enterpriseBeanService.getEntity(enterpriseId);*/
        }

    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<UserBean> model) {
         userForm = new Form("userForm", new CompoundPropertyModel(model));

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        userForm.add(feedbackPanel.setOutputMarkupId(true));

        /*enterpriseSelect2Choice = new EnterpriseSelect2Choice ("qyid", Model.of(enterpriseBean));
        enterpriseSelect2Choice.getSettings().setMinimumInputLength(2);
        userForm.add(enterpriseSelect2Choice);*/
        orgnizationSelect2Choice = new OrgnizationSelect2Choice ("jgdm",Model.of(organizationBean));
        orgnizationSelect2Choice.getSettings().setMinimumInputLength(2);
        userForm.add(orgnizationSelect2Choice);

        userForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        userForm.add(new TextField("loginName").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        TextField email = new TextField("email");
        email.add(EmailAddressValidator.getInstance());
        userForm.add(email.setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        userForm.add(new TextField("phone"));
        userForm.add(new TextField("mobile").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        if(isAdd){
            userForm.add(new PwdFragment("pwdContainer","pwdFragment",UserAddPage.this));
            //密码相同的校验
            EqualPasswordInputValidator equalPasswordInputValidator = new EqualPasswordInputValidator(pwd, pwdConfirm);
            userForm.add(equalPasswordInputValidator);
        }
        else{
            userForm.add(new HIddenFragment("pwdContainer","hiddenFragment",UserAddPage.this));
        }


//      userForm.add(new TextField("loginDate").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        //获得全部的角色列表
        final List<String> RoleNameList = roleBeanService.getRoleNameList();

        if (!isAdd) {
            List<String> roles = (roleBeanService.getRoleNameList((UserBean) model.getObject()));
            roleSelect.addAll(roles);
        }

        final IModel<ArrayList<String>> checkModel = new Model<ArrayList<String>>(roleSelect);
        final CheckBoxMultipleChoice<String> roleChoice = new CheckBoxMultipleChoice<String>("roleList", checkModel, RoleNameList);
        userForm.add(roleChoice);


        userForm.add(new AjaxButton("save", userForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    UserBean userBean = (UserBean) form.getModelObject();
                    organizationBean=orgnizationSelect2Choice.getModelObject();
                    if(organizationBean!=null){
                        userBean.setJgdm(organizationBean.getJgdm());
                    }
                    /*enterpriseBean=enterpriseSelect2Choice.getModelObject();
                    if(enterpriseBean!=null){
                        userBean.setQyid(enterpriseBean.getQyid());
                    }*/
                    userBeanService.saveUserRole(userBean, (List<String>) roleChoice.getModelObject());
                    if (isAdd) {
                        userForm.setModelObject(new UserBean());
                        roleChoice.setChoices(RoleNameList);
                    }
                    feedbackPanel.info(type + userBean.getName() + "成功！");
                    target.add(userForm);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        userForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
        userForm.setOutputMarkupId(true);
        //userForm.add(new JSR303FormValidator());
        add(userForm);
    }

    public String getConfirPwd() {
        return confirPwd;
    }

    public void setConfirPwd(String confirPwd) {
        this.confirPwd = confirPwd;
    }

    private class HIddenFragment extends Fragment {

        public HIddenFragment(String id, String markupId, MarkupContainer markupProvider) {
            super(id, markupId, markupProvider);
            HiddenField pwd = new HiddenField<String>("password");
            add(pwd);
        }
    }

    private class PwdFragment extends Fragment {

        public PwdFragment(String id, String markupId, MarkupContainer markupProvider) {
            super(id, markupId, markupProvider);

                pwd = new PasswordTextField("password");
                pwdConfirm = new PasswordTextField("confirPwd", new PropertyModel(UserAddPage.this, "confirPwd"));
                pwd.setLabel(Model.of("'密码'"));
                add(pwd.setOutputMarkupId(true).add(new ValidationStyleBehavior()));

                pwdConfirm.setLabel(Model.of("'确认密码'"));
                add(pwdConfirm).setOutputMarkupId(true);

        }
    }
}
