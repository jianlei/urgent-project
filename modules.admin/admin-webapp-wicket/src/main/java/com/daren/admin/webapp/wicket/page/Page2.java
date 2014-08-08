package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.entities.DictBean;
import com.daren.admin.webapp.wicket.data.Foo;
import com.daren.admin.webapp.wicket.data.FooTreeProvider;
import com.daren.admin.webapp.wicket.table.IrisAjaxNavigationToolbar;
import com.daren.admin.webapp.wicket.table.IrisTableTree;
import com.daren.admin.webapp.wicket.theme.BasicTheme;
import com.daren.core.web.wicket.BasePanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.table.TreeColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    功能测试页
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 20:36
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 20:36
 * 修改备注:  [说明本次修改内容]
 */
public class Page2 extends BasePanel {
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;

    public Page2(String id, WebMarkupContainer wmc) {
        super(id, wmc);

        IrisTableTree<Foo, Void> tree = new IrisTableTree<Foo, Void>(
                "tree", treeColumns(), new FooTreeProvider(), 5) {
        };
        /*tree.add(new WindowsTheme());
        tree.getTable().addTopToolbar(new HeadersToolbar<Void>(tree.getTable(), null));
        tree.getTable().addBottomToolbar(
                new IrisAjaxNavigationToolbar(tree.getTable()));*/
        tree.getTable().setOutputMarkupId(true);
        tree.add(new BasicTheme());
        add(tree);

        DataTablePage<DictBean, Void> table = new DataTablePage<DictBean, Void>("table11", columns(),
                new DictDataProvider(), 10);
        table.addTopToolbar(new HeadersToolbar<Void>(table, null));
        table.addBottomToolbar(new IrisAjaxNavigationToolbar(table));
        table.setOutputMarkupId(true);

        table.add(new BasicTheme());
        add(table);


    }

    protected Item<Foo> mynewRowItem(String id, int index, IModel<Foo> model) {
        Item<Foo> item = new Item<Foo>(id, index, model);

        return item;
    }

    private List<? extends IColumn<Foo, Void>> treeColumns() {
        List<IColumn<Foo, Void>> columns = new ArrayList<IColumn<Foo, Void>>();
        columns.add(new TreeColumn<Foo, Void>(Model.of("name"), null));
//        columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));
//        columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));
        columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));

        return columns;
    }

    private List<? extends IColumn<DictBean, Void>> columns() {
        List<IColumn<DictBean, Void>> columns = new ArrayList<IColumn<DictBean, Void>>();
//        columns.add(new TreeColumn<Foo, Void>(Model.of("name"), null));
        columns.add(new PropertyColumn<DictBean, Void>(Model.of("标签名"), "label"));
        columns.add(new PropertyColumn<DictBean, Void>(Model.of("数据值"), "value"));
        columns.add(new PropertyColumn<DictBean, Void>(Model.of("类型"), "type"));
        columns.add(new AbstractColumn<DictBean, Void>(Model.of("操作")) {

            @Override
            public void populateItem(Item<ICellPopulator<DictBean>> cellItem, String componentId, IModel<DictBean> rowModel) {
                cellItem.add(new AttributeModifier("style", new
                        Model<String>("width:20px;")));
                cellItem.add(new MyFragment(componentId, "action", Page2.this, rowModel));
            }
        });
        return columns;
    }

    private class DictDataProvider implements IDataProvider<DictBean> {
        private List<DictBean> dictBeans;

        private List<DictBean> refresh() {
            dictBeans = dictBeanService.getAllEntity();
            return dictBeans;
        }

        @Override
        public Iterator iterator(long first, long count) {
            return dictBeans.subList((int) first, (int) (first + count)).iterator();
        }

        @Override
        public long size() {
            return refresh().size();
        }


        @Override
        public IModel<DictBean> model(DictBean dictBean) {
            return Model.of(dictBean);
        }

        @Override
        public void detach() {

        }
    }


    private class MyFragment extends Fragment {
        public MyFragment(String componentId, IModel<DictBean> rowModel) {
            super("do", componentId, Page2.this, rowModel);

        }

        public MyFragment(String aDo, String componentId, Page2 components, IModel<DictBean> rowModel) {
            super(aDo, componentId, components, rowModel);
            add(new AjaxLink<DictBean>("edit") {
                @Override
                public void onClick(AjaxRequestTarget target) {

                }
            });

            add(new AjaxLink<DictBean>("delete") {
                @Override
                public void onClick(AjaxRequestTarget target) {

                }
            });
        }


    }

}
