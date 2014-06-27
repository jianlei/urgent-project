package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.AuthorityPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.Page;

/**
 * @类描述：权限子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午11:03
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AuthorityMenuItemsModule implements IMenuItemsModule {

    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 20;
    }

    @Override
    public Class<? extends Page> getPageClass() {
        return AuthorityPage.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return "权限管理";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "admin.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }
}