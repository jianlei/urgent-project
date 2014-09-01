package com.daren.drill.webapp.wicket.page;


import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.drill.api.biz.IUploadVideoService;
import com.daren.drill.entities.UrgentDrillBean;
import com.daren.drill.entities.VideoBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @类描述：应急演练-视频列表
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class VideoListPage extends IrisAbstractDialog<UrgentDrillBean> {
    @Inject
    @Reference(id = "uploadVideoService", serviceInterface = IUploadVideoService.class)
    private IUploadVideoService uploadVideoService;

    public VideoListPage(String id, String title, IModel<UrgentDrillBean> model) {
        super(id, title, model);
        UrgentDrillBean regulationBean = (UrgentDrillBean) model.getObject();
        long entityId = regulationBean.getId();
        List<VideoBean> list = uploadVideoService.getVideoBeanListByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<VideoBean> lv = new PageableListView<VideoBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<VideoBean> item) {
                final VideoBean docmentBean = item.getModelObject();
                item.add(new Label("name", docmentBean.getName()));
                item.add(new Label("description", docmentBean.getDescription()));

                //下载文档
                DownloadLink alinkdownDocument = new DownloadLink("downVideo", new AbstractReadOnlyModel<File>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public File getObject() {
                        File tempFile = null;
                        try {
                            tempFile = new File(docmentBean.getName());
                            FileInputStream fileInputStream = new FileInputStream(docmentBean.getFilePath());
                            DataInputStream data = new DataInputStream(fileInputStream);
                            Files.writeTo(tempFile, data);
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return tempFile;
                    }
                }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);
                item.add(alinkdownDocument.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);
    }
}
