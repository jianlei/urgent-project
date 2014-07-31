package com.daren.enterprise.webapp.wicket.page;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
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

    public EnterprisePage(final String id,final WebMarkupContainer wmc) {

        super(id, wmc);
        WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        List<EnterpriseBean> enterpriseBeanList = enterpriseBeanService.getAllEntity();

        PageableListView<EnterpriseBean> lv = new PageableListView <EnterpriseBean>("rows", enterpriseBeanList,10)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<EnterpriseBean> item)
            {
                final EnterpriseBean enterpriseBean = item.getModelObject();
                item.add(getToCreatePageLink(id, wmc, "check_QYMC", enterpriseBean.getId()).add(new Label("QYMC", enterpriseBean.getQymc()))
                );
                item.add(new Label("JGFL", enterpriseBean.getJgfl()));
                item.add(new Label("QYLXFS", enterpriseBean.getQylxfs()));
                item.add(new Label("MAILADDRESS", enterpriseBean.getMailaddress()));
                item.add(new Label("ADDRESS_JY", enterpriseBean.getAddress_jy()));


                AjaxLink alink=  new AjaxLink("del") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
                    {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('确定要删除么?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        /*target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+enterpriseBean.getId()+"')");*/
                        enterpriseBeanService.deleteEntity(enterpriseBean.getId());
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomePagingNavigator pagingNavigator= new CustomePagingNavigator("navigator", lv);
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);

        add(getToCreatePageLink(id, wmc, "create", -1));
    }

    private AjaxLink getToCreatePageLink(final String id,final WebMarkupContainer wmc,String wicketId,final long enterpriseBeanId){
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                wmc.removeAll();
                wmc.addOrReplace(new EnterpriseCreatePage(id,wmc,enterpriseBeanId));
                target.add(wmc);
            }
        };
        return ajaxLink;
    }
}