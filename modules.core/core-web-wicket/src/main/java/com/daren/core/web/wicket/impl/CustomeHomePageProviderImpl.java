package com.daren.core.web.wicket.impl;

import com.daren.core.web.api.provider.IHomePageProvider;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.markup.html.WebPage;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */
public class CustomeHomePageProviderImpl implements IHomePageProvider {
    @Override
    public Class<? extends WebPage> getPageClass() {
        return TemplatePage.class;
    }
}

