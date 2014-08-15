package com.daren.majorhazardsource.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.majorhazardsource.api.biz.IMajorHazardSourceBeanService;
import com.daren.majorhazardsource.entities.MajorHazardSourceBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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

public class MajorHazardSourcePage extends BasePanel {

    MajorHazardSourceDataProvider provider = new MajorHazardSourceDataProvider();

    @Inject
    private IMajorHazardSourceBeanService majorHazardSourceService;

    public MajorHazardSourcePage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<MajorHazardSourceBean> listView = new DataView<MajorHazardSourceBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<MajorHazardSourceBean> item) {
                final MajorHazardSourceBean majorHazardSourceBean = item.getModelObject();
                item.add(getToCreatePageLink("check_name", majorHazardSourceBean).add(new Label("name", majorHazardSourceBean.getName()))
                );
                item.add(new Label("expertName", majorHazardSourceBean.getExpertName()));
                item.add(new Label("estimate", majorHazardSourceBean.getEstimate()));
                item.add(new Label("lng", majorHazardSourceBean.getLng()));
                item.add(new Label("lat", majorHazardSourceBean.getLat()));
                item.add(new Label("accidentRate", majorHazardSourceBean.getAccidentRate()));


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
                        /*target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+enterpriseBean.getId()+"')");*/
                        majorHazardSourceService.deleteEntity(majorHazardSourceBean.getId());
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

    private AjaxLink getToCreatePageLink(String wicketId, final MajorHazardSourceBean majorHazardSourceBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(majorHazardSourceBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(MajorHazardSourceBean majorHazardSourceBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorHazardSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<MajorHazardSourceBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new MajorHazardSourceBean()));
        TextField textField = new TextField("name");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageLink("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                MajorHazardSourceBean majorHazardSourceBean = (MajorHazardSourceBean) form.getModelObject();
                provider.setMajorHazardSourceBean(majorHazardSourceBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class MajorHazardSourceDataProvider extends ListDataProvider<MajorHazardSourceBean> {
        private MajorHazardSourceBean majorHazardSourceBean = null;

        public void setMajorHazardSourceBean(MajorHazardSourceBean majorHazardSourceBean) {
            this.majorHazardSourceBean = majorHazardSourceBean;
        }

        @Override
        protected List<MajorHazardSourceBean> getData() {
            if (majorHazardSourceBean == null || null == majorHazardSourceBean.getName() || "".equals(majorHazardSourceBean.getName().trim()))
                return majorHazardSourceService.getAllEntity();
            else {
                return majorHazardSourceService.queryMajorHazardSource(majorHazardSourceBean);
            }
        }
    }
}
