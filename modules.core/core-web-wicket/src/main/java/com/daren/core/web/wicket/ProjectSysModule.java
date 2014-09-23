package com.daren.core.web.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.ISystemModule;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/23 8:22
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProjectSysModule implements ISystemModule{


    @Override
    public String getName() {
        return "切换系统";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTag() {
        return "";
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String getTargetTag() {
        return IConst.PROJECT_WICKET_APPLICATION_NAME;
    }
}
