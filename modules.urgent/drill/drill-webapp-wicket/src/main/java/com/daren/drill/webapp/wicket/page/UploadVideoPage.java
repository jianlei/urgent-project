package com.daren.drill.webapp.wicket.page;

import com.daren.core.api.IConst;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.drill.api.biz.IUploadDocumentService;
import com.daren.drill.entities.UrgentDrillBean;
import com.daren.drill.entities.VideoBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @类描述：应急演练-上传视频
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadVideoPage extends IrisAbstractDialog<UrgentDrillBean> {
    @Inject
    @Reference(id = "uploadDocumentService", serviceInterface = IUploadDocumentService.class)
    private IUploadDocumentService uploadDocumentService;

    public UploadVideoPage(String id, String title, IModel<UrgentDrillBean> model) {
        super(id, title, model);
        final UrgentDrillBean regulationBean = (UrgentDrillBean) model.getObject();
        final VideoBean videoBean = new VideoBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("form", new CompoundPropertyModel(videoBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        //保存按钮
        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                VideoBean videoBean1 = (VideoBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = IConst.OFFICE_WEB_PATH_WRITE + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            videoBean1.setFilePath(path);
                            videoBean1.setName(fileUpload.getClientFileName());
                            videoBean1.setSize(fileUpload.getSize());
                            videoBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                videoBean1.setAttach(regulationBean.getId());
                uploadDocumentService.saveEntity(videoBean1);
                super.onSubmit(target, form);
            }
        };
        form.add(ajaxSubmitLinkCreate);
    }
}