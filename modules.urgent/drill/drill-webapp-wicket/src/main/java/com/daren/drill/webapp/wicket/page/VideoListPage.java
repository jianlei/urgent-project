package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.drill.api.biz.IUploadVideoService;
import com.daren.drill.entities.VideoBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
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
public class VideoListPage extends BasePanel {
    @Inject
    private IUploadVideoService uploadVideoService;

    public VideoListPage(final String id, final WebMarkupContainer wmc, final long entityId) {
        super(id, wmc);
        List<VideoBean> list = uploadVideoService.getVideoBeanListByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<VideoBean> lv = new PageableListView<VideoBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<VideoBean> item) {
                final VideoBean videoBean = item.getModelObject();
                item.add(new Label("col1", videoBean.getName()));
                item.add(new Label("col2", videoBean.getDescription()));

                //下载视频
                DownloadLink alinkdownVideo = new DownloadLink("downVideo", new AbstractReadOnlyModel<File>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public File getObject() {
                        File tempFile = null;
                        try {
                            tempFile = new File(videoBean.getName());
                            FileInputStream fileInputStream = new FileInputStream(videoBean.getFilePath());
                            DataInputStream data = new DataInputStream(fileInputStream);
                            Files.writeTo(tempFile, data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return tempFile;
                    }
                }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);
                item.add(alinkdownVideo.setOutputMarkupId(true));
            }
        };
        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);

        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                wmc.removeAll();
                wmc.addOrReplace(new UrgentDrillPage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkReturn);
    }
}
