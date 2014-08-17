package com.daren.core.web.wicket;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-3-23.
 */
//@AuthorizeInstantiation({"user","admin"})
@ShiroSecurityConstraint(constraint = ShiroConstraint.LoggedIn)
//@ShiroSecurityConstraints({@ShiroSecurityConstraint(constraint= ShiroConstraint.HasRole, value="user"),@ShiroSecurityConstraint(constraint= ShiroConstraint.HasRole, value="admin")})
public class HomePage extends WebPage {


    public HomePage() {
        /*final WebMarkupContainer wmc=new WebMarkupContainer("container");
        add(wmc.setOutputMarkupId(true));*/
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));


        List<String[]> rows = new ArrayList<String[]>();
        for (int i = 0; i < 50; i++) {
            rows.add(new String[]{"Foo" + i, "Bar" + i, "fizzbuzz" + i});
        }

        PageableListView<String[]> lv = new PageableListView<String[]>("rows", rows, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<String[]> item) {
                final String[] row = item.getModelObject();

                item.add(new Label("col1", row[0]));
                item.add(new Label("col2", row[1]));
                item.add(new Label("col3", row[2]));

                AjaxLink alink = new AjaxLink("label") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('È·¶¨ÒªÉ¾³ýÂð')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                        target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt=" + row[0]
     /* you probably want to encode this first */ + "')");
                        String asda = row[0];
//                        target.add(this); // update the link component
                    }
                };
//                    alink.add(new Label("linklabel", "Yes ajax works!"));
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", lv) {

        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
        /*// FeedbackPanel //
        final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
        this.add(feedback);

        // DataTable //
        IDataProvider<Product> provider = newDataProvider();
        List<IColumn> columns = newColumnList();

        Options options = new Options();
        options.set("height", 430);
        options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
        options.set("columnMenu", true);
//        options.set("editable", "popup");
        options.set("selectable", Options.asString("multiple"));
        options.set("toolbar", "[ { name: 'view', text: 'View' }, { name: 'save', text: 'Save' } ]");

        final DataTable<Product> table = new DataTable<Product>("datatable", columns, provider, 20, options) {

            private static final long serialVersionUID = 1L;

            *//**
     * Triggered when a toolbar button is clicked.
     *//*
            @Override
            public void onClick(AjaxRequestTarget target, String button, List<String> values)
            {
                this.info(button + " " + values);
                target.add(feedback);
            }

            *//**
     * Triggered when a column button is clicked.
     *//*
            @Override
            public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
            {
                this.info(button.getName() + " #" + value);
                target.add(feedback);
            }

            @Override
            protected JQueryAjaxBehavior newToolbarAjaxBehavior(IJQueryAjaxAware source)
            {
                return new ToolbarAjaxBehavior(source, "id");
            }
        };

        this.add(table);

        // form & button//
        final Form<?> form = new Form<Void>("form");
        this.add(form);

        form.add(new Button("export") {

            private static final long serialVersionUID = 1L;

            @Override
            protected String getIcon()
            {
                return KendoIcon.TICK;
            }

            @Override
            public void onSubmit()
            {
                CSVDataExporter.export(this.getRequestCycle(), table, "export.csv");
            }
        });
    }

    private static IDataProvider<Product> newDataProvider()
    {
        return new ProductDataProvider();
    }

    private static List<IColumn> newColumnList()
    {
        List<IColumn> columns = new ArrayList<IColumn>();

        columns.add(new PropertyColumn("ID", "id", 50));
        columns.add(new PropertyColumn("Name", "name"));
        columns.add(new PropertyColumn("Description", "description"));
        columns.add(new CurrencyPropertyColumn("Price", "price", 70));

        columns.add(new CommandsColumn("", 100) {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ColumnButton> newButtons()
            {
                return Arrays.asList(new ColumnButton("edit", Model.of("Edit"), "id"));
            }
        });

        return columns;

    }

    @Override
    protected void onConfigure() {
       *//* AuthenticatedWebApplication app = (AuthenticatedWebApplication) AuthenticatedWebApplication.get();
        if (!AuthenticatedWebSession.get().isSignedIn())
            app.restartResponseAtSignInPage();*//*
    }*/
}

