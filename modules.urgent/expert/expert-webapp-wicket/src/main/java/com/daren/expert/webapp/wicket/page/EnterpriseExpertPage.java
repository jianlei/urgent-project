package com.daren.expert.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.entities.EnterpriseExpertBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：企业专家
 * @创建人：张清欣
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EnterpriseExpertPage extends BasePanel {
    @Inject
    private IEnterpriseExpertBeanService enterpriseExpertBeanService;

    public EnterpriseExpertPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        List<EnterpriseExpertBean> list = enterpriseExpertBeanService.getAllEntity();
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<EnterpriseExpertBean> lv = new PageableListView<EnterpriseExpertBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<EnterpriseExpertBean> item) {
                final EnterpriseExpertBean enterpriseExpertBean = item.getModelObject();
                item.add(new Label("col1", enterpriseExpertBean.getName()));
                item.add(new Label("col2", enterpriseExpertBean.getContactInformation()));
                item.add(new Label("col3", enterpriseExpertBean.getType()));
                //修改
                AjaxLink alinkUpdate = new AjaxLink("update") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                    }
                };
                item.add(alinkUpdate.setOutputMarkupId(true));
                //删除
                AjaxLink alinkDelete = new AjaxLink("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                    }
                };
                item.add(alinkDelete.setOutputMarkupId(true));
            }
        };
        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
        //新建按钮
        AjaxLink ajaxLinkCreate = new AjaxLink("create") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                wmc.removeAll();
                wmc.addOrReplace(new EnterpriseExpertCreatePage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkCreate);
    }
}