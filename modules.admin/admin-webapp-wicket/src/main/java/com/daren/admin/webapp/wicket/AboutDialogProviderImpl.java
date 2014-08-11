package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.AboutPage;
import com.daren.core.web.api.provider.IAboutDialogProvider;
import org.apache.wicket.Component;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/11 14:11
 * 修改人:    sunlf
 * 修改时间:  2014/8/11 14:11
 * 修改备注:  [说明本次修改内容]
 */
public class AboutDialogProviderImpl implements IAboutDialogProvider {
    @Override
    public Component getComponent(String id) {
        return getComponent(id, "");
    }

    @Override
    public Component getComponent(String id, String title) {
        return new AboutPage(id, title);
    }
}
