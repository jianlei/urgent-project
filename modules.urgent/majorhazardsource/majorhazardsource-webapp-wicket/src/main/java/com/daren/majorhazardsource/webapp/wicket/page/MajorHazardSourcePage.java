package com.daren.majorhazardsource.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.majorhazardsource.api.biz.IMajorHazardSourceBeanService;
import com.daren.majorhazardsource.entities.MajorHazardSourceBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

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

    @Inject
    private IMajorHazardSourceBeanService majorHazardSourceService;

    public MajorHazardSourcePage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        List<MajorHazardSourceBean> enterpriseBeanList = majorHazardSourceService.getAllEntity();

        PageableListView<MajorHazardSourceBean> lv = new PageableListView<MajorHazardSourceBean>("rows", enterpriseBeanList, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<MajorHazardSourceBean> item) {
                final MajorHazardSourceBean majorHazardSourceBean = item.getModelObject();
                item.add(getToCreatePageLink(id, wmc, "check_name", majorHazardSourceBean.getId()).add(new Label("name", majorHazardSourceBean.getName()))
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
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", lv);
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);

        add(getToCreatePageLink(id, wmc, "create", -1));
    }

    private AjaxLink getToCreatePageLink(final String id, final WebMarkupContainer wmc, String wicketId, final long enterpriseBeanId) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                wmc.removeAll();
                wmc.addOrReplace(new MajorHazardSourceCreatePage(id,wmc,enterpriseBeanId));
                target.add(wmc);
            }
        };
        return ajaxLink;
    }
}
