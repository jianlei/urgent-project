package com.daren.enterprise.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.DarenNavigator;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;

import javax.inject.Inject;
import java.util.List;


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

    final PageableListView<EnterpriseBean> enterpriseBeanDataView;

    public EnterprisePage(String id, WebMarkupContainer wmc) {


        super(id, wmc);

        List<EnterpriseBean> enterpriseBeanList=enterpriseBeanService.getAllEntity();
        enterpriseBeanDataView = new PageableListView("enterpriseBeanRows",enterpriseBeanList,10){
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(ListItem item) {
                EnterpriseBean enterpriseBean;
                enterpriseBean = (EnterpriseBean)item.getModelObject();
                item.add(new Label("enterpriseName", enterpriseBean.getName()));
                item.add(new Label("enterpriseEmail", enterpriseBean.getEmail()));
                item.add(new Label("enterpriseAccount", enterpriseBean.getAccount()));
                item.add(new Label("enterprisePassword", enterpriseBean.getPassword()));
            }
        };
        enterpriseBeanDataView.setOutputMarkupId(true);
        add(enterpriseBeanDataView);
        add(new DarenNavigator("navigator", enterpriseBeanDataView,enterpriseBeanDataView.getList().size()));
    }
}