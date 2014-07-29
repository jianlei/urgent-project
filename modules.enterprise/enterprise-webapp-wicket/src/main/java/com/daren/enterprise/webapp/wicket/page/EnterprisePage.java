package com.daren.enterprise.webapp.wicket.page;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButton;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.ToolbarAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.export.CSVDataExporter;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

import javax.inject.Inject;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterprisePage extends BasePanel {

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public EnterprisePage(String id, WebMarkupContainer wmc) {


        super(id, wmc);
// FeedbackPanel //
        final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
        this.add(feedback);

        // DataTable //
        IDataProvider<EnterpriseBean> provider = newDataProvider();
        List<IColumn> columns = newColumnList();

        Options options = new Options();
        options.set("height", 430);
        options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
        options.set("columnMenu", true);
        options.set("selectable", Options.asString("multiple"));
        options.set("toolbar", "[ { name: 'view', text: 'View' }, { name: 'save', text: 'Save' } ]");

        final DataTable<EnterpriseBean> table = new DataTable<EnterpriseBean>("datatable", columns, provider, 20, options) {

            private static final long serialVersionUID = 1L;

            /**
             * Triggered when a toolbar button is clicked.
             */
            @Override
            public void onClick(AjaxRequestTarget target, String button, List<String> values) {
                this.info(button + " " + values);
                target.add(feedback);
            }

            /**
             * Triggered when a column button is clicked.
             */
            @Override
            public void onClick(AjaxRequestTarget target, ColumnButton button, String value) {
                this.info(button.getName() + " #" + value);
                target.add(feedback);
            }

            @Override
            protected JQueryAjaxBehavior newToolbarAjaxBehavior(IJQueryAjaxAware source) {
                return new ToolbarAjaxBehavior(source, "name");
            }
        };

        this.add(table);

        // form & button//
        final Form<?> form = new Form<Void>("form");
        this.add(form);

        form.add(new Button("export") {

            private static final long serialVersionUID = 1L;

            @Override
            protected String getIcon() {
                return KendoIcon.TICK;
            }

            @Override
            public void onSubmit() {
                CSVDataExporter.export(this.getRequestCycle(), table, "export.csv");
            }
        });
    }

    private IDataProvider<EnterpriseBean> newDataProvider() {
        ListDataProvider listDataProvider = new ListDataProvider(enterpriseBeanService.getAllEntity());
        return listDataProvider;
    }

    private List<IColumn> newColumnList() {
        List<IColumn> columns = new ArrayList<IColumn>();
        columns.add(new PropertyColumn("企业名称", "name"));
        columns.add(new PropertyColumn("企业邮箱", "email"));
        columns.add(new PropertyColumn("登录帐号", "account"));
        columns.add(new CurrencyPropertyColumn("登录密码", "password"));

        columns.add(new CommandsColumn("操作 ", 85) {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ColumnButton> newButtons() {
                return Arrays.asList(new ColumnButton("edit", Model.of("Edit"), "name"));
            }
        });

        return columns;
    }
}