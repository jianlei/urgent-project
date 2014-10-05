package com.daren.reserveplan.webapp.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.IMenuModule;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/9 19:53
 * 修改人:    sunlf
 * 修改时间:  2014/7/9 19:53
 * 修改备注:  [说明本次修改内容]
 */
public class MainMenuModule implements IMenuModule {
    @Override
    public boolean isPermissionAvaliable() {
        return false;
    }

    @Override
    public String getPermissionName() {
        return null;
    }

    @Override
    public String getProjectName() {
        return IConst.URGENT_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getTargetTag() {
        return "planning.module.bundles";
    }

    @Override
    public String getName() {
        return "预案管理";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public int getIndex() {
        return 17;
    }
}
