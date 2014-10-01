package com.daren.core.web.component.table;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 3:32
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 3:32
 * 修改备注:  [说明本次修改内容]
 */
public class IrisHeadersToolbar<S> extends HeadersToolbar {
    /**
     * Constructor
     *
     * @param table        data table this toolbar will be attached to
     * @param stateLocator
     */
    public <T> IrisHeadersToolbar(final DataTable<T, S> table, final ISortStateLocator<S> stateLocator) {
        super(table, stateLocator);
    }
}
