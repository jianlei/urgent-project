package com.daren.hazard.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.hazard.entities.HazardBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
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

public class HazardPage extends BasePanel {

    MajorHazardSourceDataProvider provider = new MajorHazardSourceDataProvider();

    @Inject
    private IHazardBeanService majorHazardSourceService;

    public HazardPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<HazardBean> listView = new DataView<HazardBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<HazardBean> item) {
                final HazardBean hazardBean = item.getModelObject();
                item.add(new Label("name", hazardBean.getName()));
                item.add(new Label("expertName", hazardBean.getExpertName()));
                item.add(new Label("estimate", hazardBean.getEstimate()));
                item.add(new Label("lng", hazardBean.getLng()));
                item.add(new Label("lat", hazardBean.getLat()));
                item.add(new Label("accidentRate", hazardBean.getAccidentRate()));

                item.add(getToCreatePageLink("check_name", hazardBean));

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
                        majorHazardSourceService.deleteEntity(hazardBean.getId());
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

    private AjaxButton getToCreatePageLink(String wicketId, final HazardBean hazardBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(hazardBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(HazardBean hazardBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorHazardSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<HazardBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new HazardBean()));
        TextField textField = new TextField("name");

        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageLink("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                HazardBean hazardBean = (HazardBean) form.getModelObject();
                provider.setHazardBean(hazardBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class MajorHazardSourceDataProvider extends ListDataProvider<HazardBean> {
        private HazardBean hazardBean = null;

        public void setHazardBean(HazardBean hazardBean) {
            this.hazardBean = hazardBean;
        }

        @Override
        protected List<HazardBean> getData() {
            if (hazardBean == null || null == hazardBean.getName() || "".equals(hazardBean.getName().trim()))
                return majorHazardSourceService.getAllEntity();
            else {
                return majorHazardSourceService.queryHazardSource(hazardBean);
            }
        }
    }
}
