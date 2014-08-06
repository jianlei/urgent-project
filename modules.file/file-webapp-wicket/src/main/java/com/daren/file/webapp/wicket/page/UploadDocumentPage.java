package com.daren.file.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import org.apache.wicket.markup.html.WebMarkupContainer;

import javax.inject.Inject;

/**
 * @类描述：应急演练-上传文档
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UploadDocumentPage extends BasePanel {
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public UploadDocumentPage(final String id, final WebMarkupContainer wmc, final long entityId) {
        super(id, wmc);
       /* final DocumentBean docmentBean = new DocumentBean();
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
                DocumentBean docmentBean1 = (DocumentBean) form.getDefaultModelObject();
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
                docmentBean1.setAttach(entityId);
                uploadDocumentService.saveEntity(docmentBean1);
                super.onSubmit(target, form);
                wmc.removeAll();
                wmc.addOrReplace(new FilePage(id, wmc));
                target.add(wmc);
            }
        };
        add(ajaxSubmitLinkCreate);
        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                wmc.removeAll();
                wmc.addOrReplace(new FilePage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkReturn);*/
    }
}