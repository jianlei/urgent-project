package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.Files;
import com.googlecode.wicket.jquery.core.Options;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanPage extends BasePanel {

    @Inject
    private IReservePlanBeanService reservePlanBeanService;

    @Inject
    private IUploadDocumentService uploadDocumentService;

    private final TabbedPanel tabPanel;
    ReservePlanDataProvider provider = new ReservePlanDataProvider();
    final RepeatingView createPage = new RepeatingView("createPage");

    ReservePlanEditPage reservePlanEditPage;

    AjaxTab ajaxTabComprehensivePlan;

    Fragment fragment;

    public ReservePlanPage(String id, WebMarkupContainer wmc) {

        super(id, wmc);
        createPage.setOutputMarkupId(true);
        //增加tabs支持
        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(wmc), options);
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final WebMarkupContainer wmc) {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of("预案管理")) {

            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new MainFragment(panelId, "panel-1", wmc);
            }
        });

        return tabs;
    }

    /**
     * 处理查询页面
     *
     * @param table
     * @param
     */
    private Form createQuery(final WebMarkupContainer table, final ReservePlanDataProvider provider, final TabbedPanel tabPanel, final WebMarkupContainer wmc) {
        //处理查询
        Form<ReservePlanBean> myform = new Form<>("form", new CompoundPropertyModel<>(new ReservePlanBean()));
        TextField textField = new TextField("name");
        myform.add(textField.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", myform) {
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
                if (tabPanel.getModelObject().size() == 1) {
                    tabPanel.add(new AjaxTab(Model.of("预案编辑")) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public WebMarkupContainer getLazyPanel(String panelId) {
                            try {
                                // sleep the thread for a half second to simulate a long load
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                error(e.getMessage());
                            }
                            reservePlanEditPage = new ReservePlanEditPage(createPage.newChildId(), wmc, null);
                            createPage.add(reservePlanEditPage);
                            fragment = new Fragment(panelId, "panel-2", ReservePlanPage.this);
                            fragment.add(createPage);
                            return fragment;
                        }
                    });
                }
                tabPanel.setActiveTab(1);
                target.add(tabPanel);
            }
        };
        myform.add(addButton);
        myform.add(findButton);
        return myform;
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId, final WebMarkupContainer wmc) {
            super(id, markupId, ReservePlanPage.this);
            final WebMarkupContainer table = new WebMarkupContainer("table");
            add(table.setOutputMarkupId(true));
            final DataView<ReservePlanBean> listView = new DataView<ReservePlanBean>("rows", provider, 10) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<ReservePlanBean> item) {
                    final ReservePlanBean reservePlanBean = item.getModelObject();


                    item.add(new Label("name", reservePlanBean.getName()));
                    item.add(new Label("description", reservePlanBean.getDescription()));

                    addDownLoadLink(item, "reservePlanApplyId", reservePlanBean.getReservePlanApplyId());
                    addDownLoadLink(item, "reservePlanRegisterId", reservePlanBean.getReservePlanRegisterId());
                    addDownLoadLink(item, "reviewExpertId", reservePlanBean.getReviewExpertId());
                    addDownLoadLink(item, "reviewCommentId", reservePlanBean.getReviewCommentId());
                    addDownLoadLink(item, "comprehensivePlanId", reservePlanBean.getComprehensivePlanId());

                    addPlanBeanListLink(item, wmc, "specialPlanBeanList", reservePlanBean);

                    addPlanBeanListLink(item, wmc, "spotPlanBeanList", reservePlanBean);

                    addDeleteLink(item, wmc, "spotPlanBeanList", reservePlanBean, table);
                }
            };
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);
            table.add(listView);
            add(createQuery(table, provider, tabPanel, wmc));
        }
    }

    private void addDeleteLink(Item<ReservePlanBean> item, final WebMarkupContainer wmc, String linkName, final ReservePlanBean reservePlanBean, final WebMarkupContainer table) {
        AjaxLink ajaxLink = new AjaxLink("delete") {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener listener = new AjaxCallListener();
                listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
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

    private void addPlanBeanListLink(Item<ReservePlanBean> item, final WebMarkupContainer wmc, String linkName, final ReservePlanBean reservePlanBean) {
        AjaxLink comprehensivePlanIdAjaxLink = new AjaxLink(linkName) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                tabPanel.getModelObject();
                if (tabPanel.getModelObject().size() == 1) {
                    tabPanel.add(new AjaxTab(Model.of("预案编辑")) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public WebMarkupContainer getLazyPanel(String panelId) {
                            try {
                                // sleep the thread for a half second to simulate a long load
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                error(e.getMessage());
                            }
                            reservePlanEditPage = new ReservePlanEditPage(createPage.newChildId(), wmc, reservePlanBean);
                            createPage.add(reservePlanEditPage);
                            fragment = new Fragment(panelId, "panel-2", ReservePlanPage.this);
                            fragment.add(createPage);
                            return fragment;
                        }
                    });
                } else {
                    reservePlanEditPage = new ReservePlanEditPage(createPage.newChildId(), wmc, reservePlanBean);
                    createPage.removeAll();
                    createPage.add(reservePlanEditPage);
                    fragment.removeAll();
                    fragment.add(createPage);
                    target.add(fragment);
                }
                tabPanel.setActiveTab(1);
                target.add(tabPanel);
            }
        };
        item.add(comprehensivePlanIdAjaxLink.setOutputMarkupId(true));
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