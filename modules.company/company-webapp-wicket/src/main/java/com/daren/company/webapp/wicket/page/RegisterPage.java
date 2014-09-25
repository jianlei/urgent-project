package com.daren.company.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.company.webapp.wicket.model.UserBean;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * Created by Administrator on 2014/9/24.
 */
public class RegisterPage extends WebPage {
    @Inject
    private IUserBeanService userBeanService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    private UserBean userBean = new UserBean();
    public RegisterPage() {
        super();

        final JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.info("请认真填写以下字段，下列内容均为必填字段！");
        add(feedbackPanel);
        Form<UserBean> userform = new Form<>("form", new CompoundPropertyModel<UserBean>(userBean));
        this.add(userform);
        userform.setOutputMarkupId(true);

        final TextField qymc = new TextField("qymc");
        userform.add(qymc);
        final DatePicker clsj = new DatePicker("clsj", "yyyy-MM-dd", new Options("dateFormat", Options.asString("yy-mm-dd")));
        userform.add(clsj);
        final TextField frdb = new TextField("frdb");
        userform.add(frdb);
        final TextField cyrs = new TextField("cyrs");
        userform.add(cyrs);
        final TextField addresszc = new TextField("addresszc");
        userform.add(addresszc);
        final TextField addressjy = new TextField("addressjy");
        userform.add(addressjy);
        final TextField postcode = new TextField("postcode");
        userform.add(postcode);
        final TextField qylxfs = new TextField("qylxfs");
        userform.add(qylxfs);
        final TextField mailaddress = new TextField("mailaddress");
        userform.add(mailaddress);

        final TextField loginName = new TextField("loginName");
        userform.add(loginName);
        final PasswordTextField password = new PasswordTextField("password");
        userform.add(password);
        final PasswordTextField confirmPassored = new PasswordTextField("confirmPassored");
        userform.add(confirmPassored);
        final TextField name = new TextField("name");
        userform.add(name);
        final TextField mobile = new TextField("mobile");
        userform.add(mobile);
        //验证两个属性是否相同
        EqualPasswordInputValidator equalPasswordInputValidator = new EqualPasswordInputValidator(password, confirmPassored);
        userform.add(equalPasswordInputValidator);
        password.setLabel(Model.of("'密码'"));
        confirmPassored.setLabel(Model.of("'确认密码'"));

        AjaxButton ajaxSubmitLink = new AjaxButton("save", userform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                userBean = (UserBean)form.getDefaultModelObject();
                EnterpriseBean enterpriseBean = new EnterpriseBean();
                enterpriseBean.setQymc(userBean.getQymc());
                enterpriseBean.setClsj(userBean.getClsj());
                enterpriseBean.setFrdb(userBean.getFrdb());
                enterpriseBean.setCyrs(userBean.getCyrs());
                enterpriseBean.setAddresszc(userBean.getAddresszc());
                enterpriseBean.setAddressjy(userBean.getAddressjy());
                enterpriseBean.setPostcode(userBean.getPostcode());
                enterpriseBean.setQylxfs(userBean.getQylxfs());
                enterpriseBean.setMailaddress(userBean.getMailaddress());
                enterpriseBeanService.saveEntity(enterpriseBean);

                com.daren.admin.entities.UserBean uBean = new com.daren.admin.entities.UserBean();
                uBean.setQyid(String.valueOf(enterpriseBean.getId()));
                uBean.setName(userBean.getName());
                uBean.setLoginName(userBean.getLoginName());
                uBean.setPassword(userBean.getPassword());
                uBean.setMobile(userBean.getMobile());
                uBean.setIs_ent_user(1);
                userBeanService.saveEntity(uBean);

                feedbackPanel.info("注册成功，请返回登录页面！");
                target.add(feedbackPanel);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(feedbackPanel);
            }
        };
        userform.add(ajaxSubmitLink);

        Link back=new Link("back") {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
            }
        };
        userform.add(back);
    }
}
