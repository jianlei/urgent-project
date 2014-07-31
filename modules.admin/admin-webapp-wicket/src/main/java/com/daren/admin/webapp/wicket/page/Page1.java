package com.daren.admin.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

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
public class Page1 extends BasePanel {
    public Page1(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));

        //构造数据
        List<String[]> rows = new ArrayList<String[]>();
        for (int i = 0; i < 50; i++)
        {
            rows.add(new String[] { "Foo" + i, "Bar" + i, "fizzbuzz" + i });
        }

        PageableListView<String[]> lv = new PageableListView <String[]>("rows", rows,10)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<String[]> item)
            {
                final String[] row = item.getModelObject();

                item.add(new Label("col1", row[0]));
                item.add(new Label("col2", row[1]));
                item.add(new Label("col3", row[2]));

                AjaxLink alink=  new AjaxLink("label") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
                    {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                        target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+row[0]
     /* you probably want to encode this first */+"')");
                        String asda = row[0];
//                        target.add(this); // update the link component
                    }
                };
//                    alink.add(new Label("linklabel", "Yes ajax works!"));
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomePagingNavigator pagingNavigator= new CustomePagingNavigator("navigator", lv){

        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
}
