package com.daren.core.web.component.table;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 0:23
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 0:23
 * 修改备注:  [说明本次修改内容]
 */
public class IrisAjaxNavigationToolbar extends AjaxNavigationToolbar {
    /**
     * Constructor.
     *
     * @param table data table this toolbar will be attached to
     */
    public IrisAjaxNavigationToolbar(DataTable<?, ?> table) {
        super(table);
        table.setOutputMarkupId(true);

    }

    @Override
    protected PagingNavigator newPagingNavigator(String navigatorId, final DataTable<?, ?> table) {
        return new CustomerPagingNavigator(navigatorId, table) {
            @Override
            protected void onAjaxEvent(final AjaxRequestTarget target) {
                target.add(table);
            }
        };
    }

    @Override
    protected WebComponent newNavigatorLabel(final String navigatorId, final DataTable<?, ?> table) {
        return new IrisNavigatorLabel(navigatorId, table);
    }
}
