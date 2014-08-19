package com.daren.core.web.component.table;


import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

public class IrisDataTable<T, S> extends DataTable {
    private static final long serialVersionUID = 1L;


    /**
     * Constructor
     *
     * @param id           component id
     * @param columns         list of IColumn objects
     * @param dataProvider imodel for data provider
     * @param rowsPerPage
     */
    public IrisDataTable(String id, final List<? extends IColumn<T, S>> columns, ISortableDataProvider dataProvider, long rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);
        this.addTopToolbar(new IrisHeadersToolbar<S>(this, dataProvider));
        this.addBottomToolbar(new IrisAjaxNavigationToolbar(this));
        this.addBottomToolbar(new NoRecordsToolbar(this, Model.of("未找到记录")));
    }

    @Override
    protected Item newRowItem(String id, int index, IModel model) {
        return new OddEvenItem<T>(id, index, model);
    }
}
