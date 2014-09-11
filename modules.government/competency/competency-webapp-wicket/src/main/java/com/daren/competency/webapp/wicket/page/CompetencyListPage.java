package com.daren.competency.webapp.wicket.page;

import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.entities.CompetencyBean;
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
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetencyListPage extends BasePanel {

    CompetencyDataProvider provider = new CompetencyDataProvider();

    @Inject
    private ICompetencyService competencyService;

    public CompetencyListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<CompetencyBean> listView = new DataView<CompetencyBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<CompetencyBean> item) {
                final CompetencyBean competencyBean = item.getModelObject();
                item.add(new Label("name", competencyBean.getName()));
                item.add(new Label("sex", competencyBean.getSex()));
                item.add(new Label("enterpriseId", competencyBean.getEnterpriseId()));
                item.add(new Label("title", competencyBean.getTitle()));
                item.add(new Label("cultureLevel", competencyBean.getCultureLevel()));
                item.add(new Label("id_code", competencyBean.getId_code()));
                item.add(new Label("unitType", competencyBean.getUnitType()));
                item.add(new Label("qualificationsType", competencyBean.getQualificationsType()));
                item.add(new Label("awardDate", competencyBean.getAwardDate()));
                item.add(new Label("effectiveDate", competencyBean.getEffectiveDate()));
                item.add(new Label("code", competencyBean.getCode()));

                item.add(getToCreatePageLink("check_name", competencyBean));

            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final CompetencyBean competencyBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final CompetencyBean competencyBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(CompetencyBean competencyBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final CompetencyDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<CompetencyBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new CompetencyBean()));
        TextField textField = new TextField("name");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                CompetencyBean competencyBean = (CompetencyBean) form.getModelObject();
                provider.setCompetencyBean(competencyBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class CompetencyDataProvider extends ListDataProvider<CompetencyBean> {
        private CompetencyBean competencyBean = null;

        public void setCompetencyBean(CompetencyBean competencyBean) {
            this.competencyBean = competencyBean;
        }

        @Override
        protected List<CompetencyBean> getData() {
            if (competencyBean == null || null == competencyBean.getName() || "".equals(competencyBean.getName().trim()))
                return competencyService.getAllEntity();
            else {
                return competencyService.getAllEntity();
            }
        }
    }
}
