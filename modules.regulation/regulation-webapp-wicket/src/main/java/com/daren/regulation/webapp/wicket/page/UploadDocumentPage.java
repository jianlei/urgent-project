package com.daren.regulation.webapp.wicket.page;

import com.daren.regulation.api.biz.IUploadDocumentService;
import com.daren.regulation.entities.DocmentBean;
import com.daren.regulation.entities.RegulationBean;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：法律法规-上传文档
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadDocumentPage extends AbstractDialog<RegulationBean> {
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public UploadDocumentPage(String id, String title, IModel<RegulationBean> model) {
        super(id, title, model);
        final RegulationBean regulationBean = (RegulationBean) model.getObject();
        final DocmentBean docmentBean = new DocmentBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("form", new CompoundPropertyModel(docmentBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DocmentBean docmentBean1 = (DocmentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            docmentBean1.setFilePath(path);
                            docmentBean1.setName(fileUpload.getClientFileName());
                            docmentBean1.setSize(fileUpload.getSize());
                            docmentBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                docmentBean1.setAttach(regulationBean.getId());
                uploadDocumentService.saveEntity(docmentBean1);
                super.onSubmit(target, form);
            }
        };
        add(ajaxSubmitLinkCreate);
    }

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("autoOpen", true);
        behavior.setOption("closeOnEscape", false);
    }

    @Override
    public void setModelObject(RegulationBean regulationBean) {
        this.setDefaultModel(new CompoundPropertyModel<>(regulationBean));
    }

    @Override
    protected List<DialogButton> getButtons() {
        List<DialogButton> b = new ArrayList<DialogButton>();
        b.add(new DialogButton("close"));
        return b;
    }

    @Override
    public void onClose(AjaxRequestTarget target, DialogButton button) {
        this.setVisible(false);
    }

    @Override
    public boolean isDefaultCloseEventEnabled() {
        return true;
    }
}