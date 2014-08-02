package com.daren.regulation.webapp.wicket.page;


import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.regulation.api.biz.IRegulationBeanService;
import com.daren.regulation.api.biz.IUploadDocumentService;
import com.daren.regulation.entities.RegulationBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：法律法规
 * @创建人：张清欣
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RegulationPage extends BasePanel {
    @Inject
    private IRegulationBeanService regulationBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public RegulationPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        List<RegulationBean> list = regulationBeanService.getAllEntity();
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        PageableListView<RegulationBean> lv = new PageableListView<RegulationBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<RegulationBean> item) {
                final RegulationBean regulationBean = item.getModelObject();
                item.add(new Label("col1", regulationBean.getName()));
                item.add(new Label("col2", regulationBean.getDescription()));

                AjaxLink alinkDocument = new AjaxLink("document") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        wmc.removeAll();
                        wmc.addOrReplace(new DocumentListPage(id, wmc, regulationBean.getId()));
                        target.add(wmc);
                    }
                };
                alinkDocument.add(new Label("documentlabel", "文档(" + uploadDocumentService.getDocmentBeanListByAttach(regulationBean.getId()).size() + ")"));
                item.add(alinkDocument.setOutputMarkupId(true));

                //上传文档
                AjaxLink alinkuploadDocument = new AjaxLink("uploadDocument") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        wmc.removeAll();
                        wmc.addOrReplace(new UploadDocumentPage(id, wmc, regulationBean.getId()));
                        target.add(wmc);
                    }
                };
                item.add(alinkuploadDocument.setOutputMarkupId(true));
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
                wmc.addOrReplace(new RegulationCreatePage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkCreate);
    }
}