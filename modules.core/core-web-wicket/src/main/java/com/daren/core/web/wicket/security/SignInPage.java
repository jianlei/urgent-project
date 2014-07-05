/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.daren.core.web.wicket.security;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.string.Strings;
import org.ops4j.pax.wicket.util.serialization.PaxWicketSerializer;


public class SignInPage extends WebPage {
    private String username = "";
    private String password = "";
    private Form<LoginBean> form;
    private LoginBean loginBean = new LoginBean();
    private String validateCode = "";

    public SignInPage(PageParameters parameters) {
        super(parameters);
        final FeedbackPanel feedback = new FeedbackPanel(
                "errors");
        form = new Form<LoginBean>("loginForm", new CompoundPropertyModel<LoginBean>(loginBean)) {
            @Override
            protected void onSubmit() {

                if (Strings.isEmpty(username) || Strings.isEmpty(password))
                    return;
                if (login(username, password, true, SecurityUtils.getSubject().getSession().getHost(), validateCode))
                    onSignInSucceeded();


            }
        };


        feedback.setOutputMarkupId(true);

        form.add(feedback);

        form.setDefaultModel(new CompoundPropertyModel(this));

        TextField username1 = new TextField("username");
        username1.setRequired(true);
        form.add(username1);
        TextField validateCode = new TextField("validateCode");
        validateCode.setRequired(true);
//        validateCode.add(new CaptchaValidator());

        form.add(validateCode);

        form.add(new PasswordTextField("password"));

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
            PaxWicketSerializer serializer = (PaxWicketSerializer) getApplication().getFrameworkSettings().getSerializer();
            serializer.serialize(currentUser);
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
    private String username;

    private String password;
    /**
     * True if the user should be remembered via form persistence (cookies)
     */
    private boolean rememberMe = true;

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
