package com.daren.core.web.wicket.security;


import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.Const;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;

import javax.validation.constraints.NotNull;


public class SignInPage extends WebPage {
    private Form<LoginBean> form;
    private LoginBean loginBean = new LoginBean();
    public SignInPage() {
        super();
        add(new Label("title", Const.map.get(getApplication().getName())));
        String css_url="<img src=\"../cus/img/login_"+getApplication().getName()+".png\" alt=\"\"></img>";
        Label label = new Label("img", css_url);
        label.setEscapeModelStrings(false);
        label.setRenderBodyOnly(true);
        this.add(label);

        form = new Form<LoginBean>("loginForm", new CompoundPropertyModel<LoginBean>(loginBean));

        final JQueryFeedbackPanel feedback = new JQueryFeedbackPanel("errors");
        form.add(feedback.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                loginBean = (LoginBean) form.getModelObject();
                if (login(loginBean.getUsername(), loginBean.getPassword(), true, SecurityUtils.getSubject().getSession().getHost(), loginBean.getValidateCode()))
                    onSignInSucceeded();
                else
                    target.add(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                if (target != null)
                    target.add(feedback);
            }
        };

        form.add(findButton);


        TextField txtUserName = new TextField("username");
        form.add(txtUserName.setOutputMarkupId(true).add(new ValidationStyleBehavior()));
//        txtUserName.setLabel(new Model("用户名"));

        PasswordTextField pwd = new PasswordTextField("password");
        pwd.setLabel(new Model("密码"));
        form.add(pwd.setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        TextField validateCode = new TextField("validateCode");
        form.add(validateCode.add(new ValidationStyleBehavior()));

        final CaptchaImage captchaImage = new CaptchaImage("captchaImage");
        captchaImage.setOutputMarkupId(true);

        form.add(new AjaxFallbackLink("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                captchaImage.detach();
                if (target != null) {
                    target.add(captchaImage);
                } else {
                    // javascript is disable
                }
            }
        }.add(captchaImage));
        form.add(new JSR303FormValidator());
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

    }

    /**
     * Sign in user if possible.
     *
     * @param username The username
     * @param password The password
     * @return True if signin was successful
     */
    public boolean login(final String username, final String password, final boolean rememberMe, String host, String captcha) {
        final Subject currentUser = SecurityUtils.getSubject();
        final UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(),
                rememberMe, host, captcha);
        try {
            currentUser.login(token);
            /*PaxWicketSerializer serializer = (PaxWicketSerializer) getApplication().getFrameworkSettings().getSerializer();
            serializer.serialize(currentUser);*/
            return true;

            // the following exceptions are just a few you can catch and handle accordingly. See the
            // AuthenticationException JavaDoc and its subclasses for more.
        } catch (final IncorrectCredentialsException ice) {
            error("密码不正确.");
        } catch (final UnknownAccountException uae) {
            error("无此用户.");
        } catch (final CaptchaException ae) {
            error("验证码错误.");
        } catch (final AuthenticationException ae) {
            error("无效用户名或密码.");
            ae.printStackTrace();
        } catch (final Exception ex) {
            error("登录失败");
        }
        return false;
    }

    protected void onSignInSucceeded() {
        // If login has been called because the user was not yet
        // logged in, than continue to the original destination,
        // otherwise to the Home page
        continueToOriginalDestination();

        // or
        setResponsePage(getApplication().getHomePage());
    }
}

class LoginBean implements IClusterable {
    @NotNull(message = "'用户名'是必填项")
//    @Size(min = 4, max = 10)
    private String username;

    @NotNull(message = "'密码'是必填项")
//    @Size(min = 4, max = 10)
    private String password;
    /**
     * True if the user should be remembered via form persistence (cookies)
     */

    private boolean rememberMe = true;

    @NotNull(message = "'校验码'是必填项")
    private String validateCode;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }


}
