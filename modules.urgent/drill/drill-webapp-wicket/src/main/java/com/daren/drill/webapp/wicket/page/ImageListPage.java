package com.daren.drill.webapp.wicket.page;


import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
import com.daren.drill.api.biz.IUploadImageService;
import com.daren.drill.entities.ImageBean;
import com.daren.drill.entities.UrgentDrillBean;
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
 * @类描述：应急演练-图片列表
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ImageListPage extends IrisAbstractDialog<UrgentDrillBean> {
    @Inject
    @Reference(id = "uploadImageService", serviceInterface = IUploadImageService.class)
    private IUploadImageService uploadImageService;

    public ImageListPage(String id, String title, IModel<UrgentDrillBean> model) {
        super(id, title, model);
        UrgentDrillBean regulationBean = (UrgentDrillBean) model.getObject();
        long entityId = regulationBean.getId();
        List<ImageBean> list = uploadImageService.getImageBeanListByAttach(entityId);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<ImageBean> lv = new PageableListView<ImageBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<ImageBean> item) {
                final ImageBean docmentBean = item.getModelObject();
                item.add(new Label("name", docmentBean.getName()));
                item.add(new Label("description", docmentBean.getDescription()));

                //下载文档
                DownloadLink alinkdownDocument = new DownloadLink("downImage", new AbstractReadOnlyModel<File>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public File getObject() {
                        File tempFile = null;
                        try {
                            tempFile = new File(docmentBean.getName());
                            FileInputStream fileInputStream = new FileInputStream(docmentBean.getFilePath());
                            DataInputStream data = new DataInputStream(fileInputStream);
                            Files.writeTo(tempFile, data);
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
