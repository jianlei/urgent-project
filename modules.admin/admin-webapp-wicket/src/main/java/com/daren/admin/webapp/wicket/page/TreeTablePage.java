package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IOfficeBeanService;
import com.daren.admin.entities.OfficeBean;
import com.daren.core.util.JNDIHelper;
import com.daren.core.web.wicket.BasePage;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.util.string.StringValue;

import java.io.IOException;
import java.util.List;

/**
 * @类描述：树表测试页面类
 * @创建人：sunlf
 * @创建时间：2014-05-17 上午12:31
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class TreeTablePage extends BasePage {
    private IOfficeBeanService officeBeanService;

    public TreeTablePage() {
        try {
            officeBeanService = JNDIHelper.getJNDIServiceForName(IOfficeBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<OfficeBean> officeBeanList = officeBeanService.getAllEntity();
        ListDataProvider<OfficeBean> listDataProvider = new ListDataProvider<OfficeBean>(officeBeanList);
        DataView<OfficeBean> dataView = new DataView<OfficeBean>("officeRows", listDataProvider) {
            @Override
            protected void populateItem(Item<OfficeBean> item) {
                final OfficeBean OfficeBean;
                OfficeBean = item.getModelObject();
                item.add(new Label("code", OfficeBean.getCode()));
                item.add(new Label("name", OfficeBean.getName()));
                item.add(new Label("type", OfficeBean.getType()));
                item.add(new Behavior() {
                    @Override
                    public void onComponentTag(Component component, ComponentTag tag) {
//                        tag.put("style", "background-color:red");
                        tag.put("data-tt-id", StringValue.valueOf(OfficeBean.getId()));
                        if (OfficeBean.getParent() != null) {
                            tag.put("data-tt-parent-id", StringValue.valueOf(OfficeBean.getParent().getId()));
                        }
                    }
                });

            }

        };


        add(dataView);


    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

//        response.render(OnDomReadyHeaderItem.forScript("jQuery(\"#treeTable\").treetable({ expandable: true });")) ;

        //  response.render(OnDomReadyHeaderItem.forScript(" $('ul.tree').checkTree();")) ;
        /*response.render(CssHeaderItem.forReference(new CssResourceReference(TreeTablePage.class,
                "styles.css")));*/
        /*response.render(CssHeaderItem.forReference(new CssResourceReference(TreeTablePage.class,
                "jquery.treetable.css")));*/

        /*response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
                TreeTablePage.class, "jquery.js")));*/
        /*response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
                TreeTablePage.class, "jquery-ui.js")));
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
                TreeTablePage.class, "jquery.treetable.js")));*/
        /*response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(
                TreeTablePage.class, "jquery.checktree.js")));*/
    }
}
