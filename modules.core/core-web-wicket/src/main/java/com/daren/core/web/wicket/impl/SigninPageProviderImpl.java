package com.daren.core.web.wicket.impl;

import com.daren.core.web.api.provider.ISigninPageProvider;
import com.daren.core.web.wicket.security.SignInPage;
import org.apache.wicket.Page;

/**
 * 项目名称:  urgent-project
 * 类描述: 登陆页面实现类
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:16
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:16
 * 修改备注:  [说明本次修改内容]
 */
public class SigninPageProviderImpl implements ISigninPageProvider {
    @Override
    public Class<? extends Page> getPageClass() {
        return SignInPage.class;
    }
}
