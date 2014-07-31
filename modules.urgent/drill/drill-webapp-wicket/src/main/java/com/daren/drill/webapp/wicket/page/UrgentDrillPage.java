package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.drill.api.biz.IUploadDocumentService;
import com.daren.drill.api.biz.IUploadVideoService;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.entities.UrgentDrillBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UrgentDrillPage extends BasePanel {
    @Inject
    private IUrgentDrillBeanService urgentDrillBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;
    @Inject
    private IUploadVideoService uploadVideoService;

    public UrgentDrillPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        List<UrgentDrillBean> list = urgentDrillBeanService.getAllEntity();
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<UrgentDrillBean> lv = new PageableListView<UrgentDrillBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<UrgentDrillBean> item) {
                final UrgentDrillBean urgentDrillBean = item.getModelObject();
                item.add(new Label("col1", urgentDrillBean.getName()));
                item.add(new Label("col2", urgentDrillBean.getDescription()));

                AjaxLink alinkDocument = new AjaxLink("document") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                    }
                };
                alinkDocument.add(new Label("documentlabel", "文档(" + uploadDocumentService.getDocmentBeanListByAttach(urgentDrillBean.getId()).size() + ")"));
                item.add(alinkDocument.setOutputMarkupId(true));

                AjaxLink alinkVideo = new AjaxLink("video") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                    }
                };
                alinkVideo.add(new Label("videolabel", "视频(" + uploadVideoService.getVideoBeanListByAttach(urgentDrillBean.getId()).size() + ")"));
                item.add(alinkVideo.setOutputMarkupId(true));

                AjaxLink alinkImage = new AjaxLink("image") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                    }
                };
                item.add(alinkImage.setOutputMarkupId(true));

                //上传文档
                AjaxLink alinkuploadDocument = new AjaxLink("uploadDocument") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        wmc.removeAll();
                        wmc.addOrReplace(new UploadDocumentPage(id, wmc, urgentDrillBean.getId()));
                        target.add(wmc);
                    }
                };
                item.add(alinkuploadDocument.setOutputMarkupId(true));
                //上传视频
                AjaxLink alinkuploadVideo = new AjaxLink("uploadVideo") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        wmc.removeAll();
                        wmc.addOrReplace(new UploadVideoPage(id, wmc, urgentDrillBean.getId()));
                        target.add(wmc);
                    }
                };
                item.add(alinkuploadVideo.setOutputMarkupId(true));
                //上传图片
                AjaxLink alinkuploadImage = new AjaxLink("uploadImage") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        System.out.println("AJAX WORKS");
                    }
                };
                item.add(alinkuploadImage.setOutputMarkupId(true));
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
                wmc.addOrReplace(new UrgentDrillCreatePage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkCreate);
    }
}