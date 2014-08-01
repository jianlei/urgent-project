package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.drill.api.biz.IUploadImageService;
import com.daren.drill.entities.ImageBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadImagePage extends BasePanel {
    @Inject
    private IUploadImageService uploadImageService;

    public UploadImagePage(final String id, final WebMarkupContainer wmc, final long entityId) {
        super(id, wmc);
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
                            fileUpload.getSize();
                            File file = new File("F:\\saveFilePath\\" + fileUpload.getMD5());
                            fileUpload.writeTo(file);
                            imageBean1.setFilePath("F:\\saveFilePath\\");
                            imageBean1.setName(fileUpload.getClientFileName());
                            imageBean1.setSize(fileUpload.getSize());
                            imageBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageBean1.setAttach(entityId);
                uploadImageService.saveEntity(imageBean1);
                super.onSubmit(target, form);
                wmc.removeAll();
                wmc.addOrReplace(new UrgentDrillPage(id, wmc));
                target.add(wmc);
            }
        };
        add(ajaxSubmitLinkCreate);
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