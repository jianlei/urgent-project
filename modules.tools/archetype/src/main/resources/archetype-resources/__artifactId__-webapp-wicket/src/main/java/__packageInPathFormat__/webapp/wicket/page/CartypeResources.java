package ${package}.webapp.wicket.page;

import ${package}.api.biz.IBrandService;
import ${package}.core.persistence.BrandBeanImpl;
import com.daren.core.web.wicket.BasePage;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;
import org.lightcouch.Attachment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @类描述：车型资源
 * @创建人：wangkr
 * @创建时间：2014-03-31 上午10:13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CartypeResources extends BasePage {
    @Named
    @Inject
    @Reference(id = "brandService", serviceInterface = IBrandService.class)
    private IBrandService brandService;

    private final FileUploadField file;
    Label label1 = new Label("pageName1", "上传文件");
    Label label2 = new Label("pageName2", "更新文件");
    /*final TextField formZipcode;*/
    BrandBeanImpl brandBean;


    public CartypeResources(PageParameters param) {
        final String brandId = param.get("brandId").toString();

        if(null != brandId && !"".equals(brandId)){
            brandBean = (BrandBeanImpl) brandService.findBrand(brandId);
        }

        label1.setVisible(true);
        label2.setVisible(false);
        add(label1);
        add(label2);

        final Form<CartypeResourcesBean> form = new Form<CartypeResourcesBean>("uploadFileForm", new CompoundPropertyModel<CartypeResourcesBean>(new CartypeResourcesBean()));
        add(form);
/*

        formZipcode = new TextField("describe");

        form.add(formZipcode);
*/

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                CartypeResourcesBean resourceBean = (CartypeResourcesBean) getParent().getDefaultModelObject();
                label1.setVisible(true);
                label2.setVisible(false);

                FileUpload upload = file.getFileUpload();
                if (upload != null) {

                    Attachment attachment2 = new Attachment();
                    attachment2.setStub(false);
                    String data = Base64.encodeBase64String(upload.getBytes());
                    attachment2.setData(data);

                    if (null != resourceBean.getName() && !"".equals(resourceBean.getName())) {
                        brandBean.addAttachment(resourceBean.getName(), attachment2);
                    } else {
                        brandBean.addAttachment(upload.getClientFileName(), attachment2);
                    }
                    brandService.putBrand(brandBean);
                } else {
                }
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("brandId", brandId);
                setResponsePage(CartypeResources.class, pageParameters);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                System.out.println("onError");
            }
        });

        add(new Link("reset_from_1") {
            @Override
            public void onClick() {
                form.setModelObject(new CartypeResourcesBean());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(new Link("reset_from_2") {
            @Override
            public void onClick() {
                form.setModelObject(new CartypeResourcesBean());
                label1.setVisible(true);
                label2.setVisible(false);
            }
        });

        form.add(file = new FileUploadField("file"));
        form.add(new UploadProgressBar("progress", form, file));

        Map<String, Attachment> attachmentMap = brandBean.getAttachments();
        List<CartypeResourcesBean> distributorList = new ArrayList<CartypeResourcesBean>();
        if (null != attachmentMap && attachmentMap.size() > 0) {
            for (String key : attachmentMap.keySet()) {
                CartypeResourcesBean cartypeResourcesBean = new CartypeResourcesBean();
                Attachment attachment = attachmentMap.get(key);
                cartypeResourcesBean.setName(key);
                cartypeResourcesBean.setDescribe(attachment.getContentType());
                cartypeResourcesBean.setSize(attachment.getLength() + "B");
                distributorList.add(cartypeResourcesBean);
            }
        }
        ListDataProvider<CartypeResourcesBean> listDataProvider = new ListDataProvider<CartypeResourcesBean>(distributorList);
        add(new DataView<CartypeResourcesBean>("productRow", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<CartypeResourcesBean> item) {
                final CartypeResourcesBean distributor;
                distributor = item.getModelObject();

                item.add(new Label("name", distributor.getName()));
                item.add(new Label("describe", distributor.getDescribe()));
                item.add(new Label("size", distributor.getSize()));
                item.add(new Link("edit") {
                    @Override
                    public void onClick() {
                        form.setModelObject((CartypeResourcesBean) getParent().getDefaultModelObject());
                        label1.setVisible(false);
                        label2.setVisible(true);
                    }
                });
                item.add(new DownloadLink("download", new AbstractReadOnlyModel<File>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public File getObject() {
                        String fileName= distributor.getName();
                        File tempFile = null;
                        try {
                            try {
                                tempFile = new File(fileName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String fileUrl = brandService.getAttachmentUrl(brandBean,fileName);//brandBean.getAttachments().get(fileName).getData();
                            if(null != fileUrl && !"".equals(fileUrl)){
                                URL url = new URL(fileUrl);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                DataInputStream data = new DataInputStream(connection.getInputStream());
                                Files.writeTo(tempFile, data);
                            }else{
                                InputStream data = new ByteArrayInputStream("啥也没找到".getBytes());
                                Files.writeTo(tempFile, data);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return tempFile;
                    }
                }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true));
               /* AjaxLink deply = new AjaxLink("deploy") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                    }

                    @Override
                    public boolean isVisible() {
                        return distributor.getStatus().equals("未发布");
                    }
                };*//*
                item.add(deply);*/
                item.add(new Link("delete") {
                    @Override
                    public void onClick() {
                        CartypeResourcesBean resourceBean = (CartypeResourcesBean) getParent().getDefaultModelObject();
                        Map<String, Attachment> attachmentMap1 = brandBean.getAttachments();
                        for (String key : attachmentMap1.keySet()) {
                            if (resourceBean.getName().equals(key)) {
                                attachmentMap1.remove(key);
                                break;
                            }
                        }
                        brandBean.setAttachments(attachmentMap1);
                        brandService.putBrand(brandBean);
                        PageParameters pageParameters = new PageParameters();
                        pageParameters.add("brandId", brandId);
                        setResponsePage(CartypeResources.class, pageParameters);
                    }
                });
                /*item.add(new Link("history") {
                    @Override
                    public void onClick() {
                    }
                });*/
                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        });
    }

    class CartypeResourcesBean implements Serializable {
        String name;
        String size;
        String date;
        String version;
        String status;
        String file;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        String describe;
    }
}


