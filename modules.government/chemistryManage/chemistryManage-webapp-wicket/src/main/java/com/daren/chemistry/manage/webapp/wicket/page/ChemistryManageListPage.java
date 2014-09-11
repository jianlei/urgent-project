package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：危险化学品经营许可证
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageListPage extends BasePanel {

    ChemistryManageDataProvider provider = new ChemistryManageDataProvider();

    @Inject
    private IChemistryManageBeanService chemistryManageBeanService;

    public ChemistryManageListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<ChemistryManageBean> listView = new DataView<ChemistryManageBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<ChemistryManageBean> item) {
                final ChemistryManageBean competencyBean = item.getModelObject();
                item.add(new Label("name", competencyBean.getName()));
                item.add(new Label("header", competencyBean.getHeader()));
                item.add(new Label("phone", competencyBean.getPhone()));
                item.add(new Label("address", competencyBean.getAddress()));
                item.add(new Label("mode", competencyBean.getMode()));
                item.add(new Label("unitType", competencyBean.getUnitType()));
                item.add(new Label("scope", competencyBean.getScope()));
                item.add(getToCreatePageLink("check_name", competencyBean));

            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final ChemistryManageBean competencyBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final ChemistryManageBean competencyBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(ChemistryManageBean competencyBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final ChemistryManageDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<ChemistryManageBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new ChemistryManageBean()));
        TextField textField = new TextField("name");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ChemistryManageBean competencyBean = (ChemistryManageBean) form.getModelObject();
                provider.setChemistryManageBean(competencyBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class ChemistryManageDataProvider extends ListDataProvider<ChemistryManageBean> {
        private ChemistryManageBean competencyBean = null;

        public void setChemistryManageBean(ChemistryManageBean competencyBean) {
            this.competencyBean = competencyBean;
        }

        @Override
        protected List<ChemistryManageBean> getData() {
            if (competencyBean == null || null == competencyBean.getName() || "".equals(competencyBean.getName().trim()))
                return chemistryManageBeanService.getAllEntity();
            else {
                return chemistryManageBeanService.getAllEntity();
            }
        }
    }
}
