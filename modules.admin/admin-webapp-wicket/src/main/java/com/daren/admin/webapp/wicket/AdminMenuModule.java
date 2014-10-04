package com.daren.admin.webapp.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.IMenuModule;

/**
 * @类描述：系统主菜单定义
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:37
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AdminMenuModule implements IMenuModule {
    @Override
    public String getProjectName() {
        return IConst.SYSTEM_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getTargetTag() {
        return new String("admin.module.bundles");
    }

    @Override
    public int getIndex() {
        return 100;
    }

    @Override
    public String getName() {
        return Const.MENU_ADMIN;
    }

    @Override
    public String getIcon() {
        return "icon-cogs";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "8plat";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPermissionAvaliable() {
        return true;
    }

    @Override
    public String getPermissionName() {
        return Const.PERMISSION_ADMIN;
    }
}
