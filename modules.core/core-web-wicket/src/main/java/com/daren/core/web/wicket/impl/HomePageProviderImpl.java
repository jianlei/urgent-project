package com.daren.core.web.wicket.impl;

import com.daren.core.web.api.provider.IHomePageProvider;
import com.daren.core.web.wicket.HomePage;
import org.apache.wicket.Page;

/**
 * 项目名称:  urgent-project
 * 类描述: 首页实现类
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 12:23
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 12:23
 * 修改备注:  [说明本次修改内容]
 */
public class HomePageProviderImpl implements IHomePageProvider {
    @Override
    public Class<? extends Page> getPageClass() {
        return HomePage.class;
    }
}
