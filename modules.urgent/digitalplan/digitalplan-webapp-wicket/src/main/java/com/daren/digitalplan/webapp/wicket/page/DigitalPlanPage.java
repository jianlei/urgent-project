package com.daren.digitalplan.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.entities.DigitalPlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
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

public class DigitalPlanPage extends BasePanel {

    MajorDigitalPlanSourceDataProvider provider = new MajorDigitalPlanSourceDataProvider();

    @Inject
    private IDigitalPlanBeanService majorDigitalPlanSourceService;

    public DigitalPlanPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<DigitalPlanBean> listView = new DataView<DigitalPlanBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<DigitalPlanBean> item) {
                final DigitalPlanBean digitalplanBean = item.getModelObject();
                item.add(new Label("name", digitalplanBean.getName()));
                item.add(new Label("expertName", digitalplanBean.getExpertName()));
                item.add(new Label("estimate", digitalplanBean.getEstimate()));
                item.add(new Label("lng", digitalplanBean.getLng()));
                item.add(new Label("lat", digitalplanBean.getLat()));
                item.add(new Label("accidentRate", digitalplanBean.getAccidentRate()));

                item.add(getToCreatePageLink("check_name", digitalplanBean));

                AjaxLink alink = new AjaxLink("del") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('确定要删除么?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        majorDigitalPlanSourceService.deleteEntity(digitalplanBean.getId());
                        target.add(table);
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final DigitalPlanBean digitalplanBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(digitalplanBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final DigitalPlanBean digitalplanBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(digitalplanBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(DigitalPlanBean digitalplanBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorDigitalPlanSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<DigitalPlanBean> majorDigitalPlanSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new DigitalPlanBean()));
        TextField textField = new TextField("name");

        majorDigitalPlanSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorDigitalPlanSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorDigitalPlanSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorDigitalPlanSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DigitalPlanBean digitalplanBean = (DigitalPlanBean) form.getModelObject();
                provider.setDigitalPlanBean(digitalplanBean);
                target.add(table);
            }
        };
        majorDigitalPlanSourceBeanForm.add(findButton);
    }


    class MajorDigitalPlanSourceDataProvider extends ListDataProvider<DigitalPlanBean> {
        private DigitalPlanBean digitalplanBean = null;

        public void setDigitalPlanBean(DigitalPlanBean digitalplanBean) {
            this.digitalplanBean = digitalplanBean;
        }

        @Override
        protected List<DigitalPlanBean> getData() {
            if (digitalplanBean == null || null == digitalplanBean.getName() || "".equals(digitalplanBean.getName().trim()))
                return majorDigitalPlanSourceService.getAllEntity();
            else {
                return majorDigitalPlanSourceService.queryDigitalPlanSource(digitalplanBean);
            }
        }
    }
}
