package com.daren.regulation.webapp.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.IMenuModule;

/**
 * @类描述：企业法律法规主菜单定义
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午4:37
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EntMainMenuModule implements IMenuModule {
    @Override
    public String getProjectName() {
        return IConst.COMPANY_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getTargetTag() {
        return new String("ent.regulation.module.bundles");
    }

    @Override
    public int getIndex() {
        return 150;
    }

    @Override
    public String getName() {
        return "法律法规";
    }

    @Override
    public String getIcon() {
        return "icon-cogs";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "8plat";  //To change body of implemented methods use File | Settings | File Templates.
    }
}

