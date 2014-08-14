package com.daren.core.web.component.table;


import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

public class IrisDataTable<T, S> extends DataTable {
    private static final long serialVersionUID = 1L;


    /**
     * Constructor
     *
     * @param id           component id
     * @param list         list of IColumn objects
     * @param dataProvider imodel for data provider
     * @param rowsPerPage
     */
    public IrisDataTable(String id, List list, IDataProvider dataProvider, long rowsPerPage) {
        super(id, list, dataProvider, rowsPerPage);
    }


}
