package com.daren.admin.webapp.wicket.table;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.tree.ISortableTreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.TableTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;
import java.util.Set;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 3:27
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 3:27
 * 修改备注:  [说明本次修改内容]
 */
public class IrisTableTree<T, S> extends TableTree<T, S> {

    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     *
     * @param id          component id
     * @param columns     columns for the {@link org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable}
     * @param provider    the provider of the tree
     * @param rowsPerPage rows to show on each page
     */
    public IrisTableTree(String id, List<? extends IColumn<T, S>> columns,
                         ISortableTreeProvider<T, S> provider, int rowsPerPage) {
        this(id, columns, provider, rowsPerPage, null);
    }

    /**
     * Construct.
     *
     * @param id          component id
     * @param columns     columns for the {@link org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable}
     * @param provider    the provider of the tree
     * @param rowsPerPage rows to show on each page
     * @param state       expansion state
     */
    public IrisTableTree(String id, List<? extends IColumn<T, S>> columns,
                         ISortableTreeProvider<T, S> provider, int rowsPerPage, IModel<Set<T>> state) {
        super(id, columns, provider, rowsPerPage, state);

        getTable().addBottomToolbar(new IrisAjaxNavigationToolbar(getTable()));
        getTable().addTopToolbar(new IrisHeadersToolbar<S>(getTable(), provider));
        getTable().addBottomToolbar(new NoRecordsToolbar(getTable(), Model.of("未找到记录")));

        add(new WindowsTheme());
    }

    /**
     * Creates {@link org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder} for each node.
     *
     * @param id    component id
     * @param model the node model
     */
    @Override
    protected Component newContentComponent(String id, IModel<T> model) {
        return new Folder<T>(id, this, model);
    }

    /**
     * Creates an {@link org.apache.wicket.markup.repeater.OddEvenItem}.
     *
     * @param id   component id
     * @param node the node model
     */
    @Override
    protected Item<T> newRowItem(String id, int index, IModel<T> node) {
        return new OddEvenItem<T>(id, index, node);
    }
}
