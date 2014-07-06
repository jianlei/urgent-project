package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.Page1;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.Page;

/**
 * @类描述：系统用户子菜单服务定义
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:37
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UserMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Class<? extends Page> getPageClass() {
//        return UserPage.class;
        return Page1.class;
    }

    @Override
    public String getName() {
        return "用户管理";  //To change body of implemented methods use File | Settings | File Templates.
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
