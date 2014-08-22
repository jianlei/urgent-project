package com.daren.example.webapp.wicket;

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
    public String getProjectName() {
        return IConst.EXAMPLE_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getTargetTag() {
        return "example.module.bundles";
    }

    @Override
    public String getName() {
        return "例子管理";
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
        return 10;
    }
}
