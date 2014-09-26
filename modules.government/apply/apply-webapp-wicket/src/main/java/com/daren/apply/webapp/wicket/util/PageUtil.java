package com.daren.apply.webapp.wicket.util;

import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.api.IConst;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.util.file.Files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2014/9/26.
 */
public class PageUtil {
    public static AjaxLink initPreviewButton(final AttachmentBean attachmentBean) {
        AjaxLink alink = new AjaxLink("previewDuplicate") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                FileInputStream fileInputStream = null;
                try {
                    File tempFile = new File(IConst.OFFICE_WEB_PATH_TEMP + attachmentBean.getName());
                    fileInputStream = new FileInputStream(attachmentBean.getPath());
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                    fileInputStream.close();
//                    createDialog(target, "Office", IConst.OFFICE_WEB_PATH_READ + attachmentBean.getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return alink;
    }
}
