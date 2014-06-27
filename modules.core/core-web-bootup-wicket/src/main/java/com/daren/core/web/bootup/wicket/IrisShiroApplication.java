/**
 * Copyright OPS4J
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daren.core.web.bootup.wicket;


import com.daren.core.web.wicket.security.AccessDeniedPage;
import com.daren.core.web.wicket.security.SignInPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

public class IrisShiroApplication extends WebApplication {

    // --------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------

    public IrisShiroApplication() {
    }

    // --------------------------------------------------------------------------
    // Overrides
    // --------------------------------------------------------------------------

    @Override
    protected void init() {
        super.init();

        /*new BeanValidationConfiguration().configure(this);
        Parsley.register(this);*/
        //避免将Wicket 标签输出到客户端
        getMarkupSettings().setStripWicketTags(true);
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        // Sets Wicket Request Cycle settings for this application
        getRequestCycleSettings().setBufferResponse(true);
        getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        mountPage("/error404", ErrorPage404.class);
        setPageManagerProvider(new NoSerializationPageManagerProvider(this));
        //设置首页映射路径
        mountPage("/home/", HomePage.class);

        // Enable Shiro security
        AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
        getSecuritySettings().setAuthorizationStrategy(authz);
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(
                new ShiroUnauthorizedComponentListener(SignInPage.class, AccessDeniedPage.class, authz));
        // SecurityUtils.getSubject().getSession().setTimeout(-1000l);

        mountPage("login", SignInPage.class);
//    mountPage("account/logout", LogoutPage.class);
   /* mountPage( "public", PublicPage.class );
    mountPage( "admin", AdminPage.class );
    mountPage( "user", UserPage.class );*/
    }

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }


}
