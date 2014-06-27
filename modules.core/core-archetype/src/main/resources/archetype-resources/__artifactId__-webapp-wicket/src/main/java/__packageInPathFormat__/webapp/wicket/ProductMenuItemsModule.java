package ${package}.webapp.wicket;

import ${package}.webapp.wicket.page.ProductPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.Page;

/**
 * @类描述：产品维护子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午4:03
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ProductMenuItemsModule implements IMenuItemsModule {
    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 10;
    }

    @Override
    public Class<? extends Page> getPageClass() {
        return ProductPage.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return "产品管理";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "cartype.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
