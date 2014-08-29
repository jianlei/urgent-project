package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.util.DateUtil;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanPage extends BasePanel {

    ReservePlanDataProvider provider = new ReservePlanDataProvider();
    Map<String, String> enterpriseNameMap;

    @Inject
    private IReservePlanBeanService reservePlanBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public ReservePlanPage(String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        enterpriseNameMap = enterpriseBeanService.getAllBeansToHashMap();
        initForm(wmc);
    }

    private void initForm(final WebMarkupContainer wmc) {
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        final DataView<ReservePlanBean> listView = new DataView<ReservePlanBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<ReservePlanBean> item) {
                final ReservePlanBean reservePlanBean = item.getModelObject();


                item.add(new Label("name", reservePlanBean.getName()));
                item.add(new Label("approveType", reservePlanBean.getApproveType()));
                item.add(new Label("version", reservePlanBean.getVersion()));
                item.add(new Label("enterpriseId", enterpriseNameMap.get(reservePlanBean.getEnterpriseId())));
                item.add(new Label("approveTime", DateUtil.convertDateToString(reservePlanBean.getApproveTime(), DateUtil.shortSdf)));
                addDownLoadLink(item, "reservePlanApplyId", reservePlanBean.getReservePlanApplyId());
                addDownLoadLink(item, "reservePlanRegisterId", reservePlanBean.getReservePlanRegisterId());
                addDownLoadLink(item, "reviewExpertId", reservePlanBean.getReviewExpertId());
                addDownLoadLink(item, "reviewCommentId", reservePlanBean.getReviewCommentId());
                addDownLoadLink(item, "comprehensivePlanId", reservePlanBean.getComprehensivePlanId());
                addDeleteLink(item, wmc, "delete", reservePlanBean, table);
                addEditLink(item, "edit", reservePlanBean);
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
        };
        table.add(pagingNavigator);
        table.add(listView);
        initTable(table);
    }

    /**
     * 处理查询页面
     *
     * @param table
     * @param
     */
    private void initTable(final WebMarkupContainer table) {
        //处理查询
        Form<ReservePlanBean> reservePlanBeanForm = new Form<>("form", new CompoundPropertyModel<>(new ReservePlanBean()));
        TextField textField = new TextField("name");
        reservePlanBeanForm.add(textField.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", reservePlanBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ReservePlanBean userBean = (ReservePlanBean) form.getModelObject();
                provider.setReservePlanBean(userBean);
                target.add(table);
            }
        };

        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                addButtonOnClick(target, new ReservePlanBean());
            }
        };
        reservePlanBeanForm.add(addButton);
        reservePlanBeanForm.add(findButton);
        add(reservePlanBeanForm);
    }

    protected void addButtonOnClick(AjaxRequestTarget target, ReservePlanBean reservePlanBean) {
    }


    private void addOpenSpotPageLink(Item<ReservePlanBean> item, final WebMarkupContainer wmc, String linkName, final ReservePlanBean reservePlanBean) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                spotPageLinkOnClick(reservePlanBean, target);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    protected void spotPageLinkOnClick(ReservePlanBean reservePlanBean, AjaxRequestTarget target) {
    }

    private void addOpenSpecialPageLink(Item<ReservePlanBean> item, final WebMarkupContainer wmc, String linkName, final ReservePlanBean reservePlanBean) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                specialPageLinkOnClick(reservePlanBean, target);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    protected void specialPageLinkOnClick(ReservePlanBean reservePlanBean, AjaxRequestTarget target) {
    }

    private void addDeleteLink(Item<ReservePlanBean> item, final WebMarkupContainer wmc, String linkName, final ReservePlanBean reservePlanBean, final WebMarkupContainer table) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener listener = new AjaxCallListener();
                listener.onPrecondition("if(!confirm('确定要删除吗')){return false;}");
                attributes.getAjaxCallListeners().add(listener);
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                reservePlanBeanService.deleteEntity(reservePlanBean.getId());
                target.add(table);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    private void addEditLink(Item<ReservePlanBean> item, String linkName, final ReservePlanBean reservePlanBean) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                addButtonOnClick(target, reservePlanBean);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    private void addDownLoadLink(Item item, String downLoadLinkName, String documentId) {
        long documentIdLong;
        if (null != documentId && !"".equals(documentId)) {
            documentIdLong = Long.parseLong(documentId);
        } else {
            documentIdLong = 0;
        }
        if (documentIdLong > 0) {
            DocumentBean documentBean = (DocumentBean) uploadDocumentService.getEntity(Long.parseLong(documentId));
            addDownLoadLink(item, downLoadLinkName, documentBean.getName(), documentBean.getFilePath());
        } else {
            addDownLoadLink(item, downLoadLinkName);
        }
    }

    private void addDownLoadLink(Item item, String downLoadLinkName) {
        item.add(new Label(downLoadLinkName, "未上传"));
    }

    private void addDownLoadLink(Item item, String downLoadLinkName, final String fileName, final String filePath) {
        DownloadLink downloadLink = new DownloadLink(downLoadLinkName, new AbstractReadOnlyModel<File>() {
            private static final long serialVersionUID = 1L;

            @Override
            public File getObject() {
                File tempFile = null;
                FileInputStream fileInputStream = null;
                try {
                    tempFile = new File(fileName);
                    fileInputStream = new FileInputStream(filePath);
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return tempFile;
            }
        });
        item.add(downloadLink.setOutputMarkupId(true).add());
    }

    class ReservePlanDataProvider extends ListDataProvider<ReservePlanBean> {
        private ReservePlanBean reservePlanBean = null;

        public void setReservePlanBean(ReservePlanBean reservePlanBean) {
            this.reservePlanBean = reservePlanBean;
        }

        @Override
        protected List<ReservePlanBean> getData() {
            if (reservePlanBean == null || null == reservePlanBean.getName() || "".equals(reservePlanBean.getName().trim()))
                return reservePlanBeanService.getAllEntity();
            else {
                return reservePlanBeanService.queryByName(reservePlanBean);
            }
        }
    }
}