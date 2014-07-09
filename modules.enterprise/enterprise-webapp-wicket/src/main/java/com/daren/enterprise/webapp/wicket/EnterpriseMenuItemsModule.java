package com.daren.enterprise.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.enterprise.webapp.wicket.page.EnterprisePage;
import org.apache.wicket.Page;

/**
 * @类描述：品牌子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseMenuItemsModule implements IMenuItemsModule {


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
        return EnterprisePage.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return "企业信息管理";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "enterprise.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }
}