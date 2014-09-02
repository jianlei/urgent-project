package com.daren.gis.webapp.wicket.page;


import com.daren.core.api.IConst;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.component.office.WindowOfficePage;
import com.daren.core.web.wicket.component.dialog.InotParametersDialog;
import com.daren.file.entities.DocumentBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.util.file.Files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @类描述：应急演练-文档列表
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DocumentListPage extends InotParametersDialog {
    final WebMarkupContainer dialogWrapper;
    WindowOfficePage dialog;

    public DocumentListPage(String id, String title, List<DocumentBean> list) {
        super(id, title);
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<DocumentBean> lv = new PageableListView<DocumentBean>("rows", list, 10) {
            @Override
            protected void populateItem(ListItem<DocumentBean> item) {
                final DocumentBean docmentBean = item.getModelObject();
                item.add(new Label("name", docmentBean.getName()));
                item.add(new Label("description", docmentBean.getDescription()));
                item.add(initPreviewButton(docmentBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", lv) {
        };
        table.add(pagingNavigator);
        table.setVersioned(false);
        table.add(lv);

        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("mydialog", ""));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
    }

    private void createDialog(AjaxRequestTarget target, final String title, final String filePath) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowOfficePage("mydialog", title, filePath) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    private AjaxLink initPreviewButton(final DocumentBean docmentBean) {
        AjaxLink alink = new AjaxLink("previewDocument") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                FileInputStream fileInputStream = null;
                try {
                    File tempFile = new File(IConst.OFFICE_WEB_PATH_TEMP + docmentBean.getName());
                    fileInputStream = new FileInputStream(docmentBean.getFilePath());
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                    fileInputStream.close();
                    createDialog(target, "Office", IConst.OFFICE_WEB_PATH_READ + docmentBean.getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return alink;
    }
}
