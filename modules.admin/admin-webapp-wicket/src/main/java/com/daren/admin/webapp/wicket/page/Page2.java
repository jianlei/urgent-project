package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.admin.webapp.wicket.data.Product;
import com.daren.admin.webapp.wicket.data.ProductDataProvider;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 20:36
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 20:36
 * 修改备注:  [说明本次修改内容]
 */
public class Page2 extends BasePanel {
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)
    private static IUserBeanService userBeanService;

    public void setUserBeanService(IUserBeanService userBeanService) {
        this.userBeanService = userBeanService;
    }

    public Page2(String id, WebMarkupContainer wmc) {
        super(id, wmc);

        // Form //
        final Form<?> form = new Form<Void>("form");
        this.add(form);

        // DataTable //
        IDataProvider<UserBean> provider = newDataProvider();
        List<IColumn> columns = newColumnList();

        Options options = new Options();
        options.set("height", 430);
        options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
        //options.set("sortable", true); // already set, as provider IS-A ISortStateLocator
        options.set("groupable", true);
        options.set("columnMenu", true);

        final DataTable<UserBean> table = new DataTable<UserBean>("datatable", columns, provider, 25, options);
        form.add(table);

        // Button //
        form.add(new AjaxButton("refresh") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                table.refresh(target);
            }
        });
    }

    private static IDataProvider<UserBean> newDataProvider()
    {
        return new ListDataProvider<UserBean>(){
            @Override
            protected List<UserBean> getData() {
                return userBeanService.getAllUser();
            }
        };
    }

    private static List<IColumn> newColumnList()
    {
        List<IColumn> columns = new ArrayList<IColumn>();

        columns.add(new PropertyColumn("ID", "id", 50));
        columns.add(new PropertyColumn("姓名", "name"));
        columns.add(new PropertyColumn("邮箱", "email"));
//        columns.add(new CurrencyPropertyColumn("Price", "price", 70));
//		columns.add(new DatePropertyColumn("Date", "date"));
        columns.add(new PropertyColumn("登录名", "loginName"));

        return columns;
    }

}
