package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.drill.api.biz.IUploadImageService;
import com.daren.drill.entities.ImageBean;
import com.daren.drill.entities.UrgentDrillBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
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
 * @类描述：应急演练-上传图片
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadImagePage extends IrisAbstractDialog<UrgentDrillBean> {
    @Inject
    @Reference(id = "uploadImageService", serviceInterface = IUploadImageService.class)
    private IUploadImageService uploadImageService;

    public UploadImagePage(String id, String title, IModel<UrgentDrillBean> model) {
        super(id, title, model);
        final UrgentDrillBean regulationBean = (UrgentDrillBean) model.getObject();
        final ImageBean imageBean = new ImageBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("form", new CompoundPropertyModel(imageBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ImageBean imageBean1 = (ImageBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            imageBean1.setFilePath(path);
                            imageBean1.setName(fileUpload.getClientFileName());
                            imageBean1.setSize(fileUpload.getSize());
                            imageBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageBean1.setAttach(regulationBean.getId());
                uploadImageService.saveEntity(imageBean1);
                super.onSubmit(target, form);
            }
        };
        add(ajaxSubmitLinkCreate);
    }
}