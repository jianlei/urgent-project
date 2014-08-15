package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.drill.api.biz.IUploadDocumentService;
import com.daren.drill.api.biz.IUploadImageService;
import com.daren.drill.api.biz.IUploadVideoService;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.entities.UrgentDrillBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UrgentDrillListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "应急演练";
    private final static String CONST_ADD = "添加演练";
    private final static String CONST_EDIT = "编辑演练";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    IrisAbstractDialog dialog;
    Fragment fragment;
    //注入服务
    @Inject
    @Reference(id = "urgentDrillBeanService", serviceInterface = IUrgentDrillBeanService.class)
    private IUrgentDrillBeanService urgentDrillBeanService;
    @Inject
    @Reference(id = "uploadDocumentService", serviceInterface = IUploadDocumentService.class)
    private IUploadDocumentService uploadDocumentService;
    @Inject
    @Reference(id = "uploadVideoService", serviceInterface = IUploadVideoService.class)
    private IUploadVideoService uploadVideoService;
    @Inject
    @Reference(id = "uploadImageService", serviceInterface = IUploadImageService.class)
    private IUploadImageService uploadImageService;

    //构造函数
    public UrgentDrillListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(), options);
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<? extends ITab> newTabList() {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of(CONST_LIST)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new MainFragment(panelId, "mainPanel");
            }
        });

        return tabs;
    }

    /**
     * 初始化新增按钮
     *
     * @return
     */
    private IndicatingAjaxLink initAddButton() {
        //新增
        IndicatingAjaxLink addButton = new IndicatingAjaxLink("add") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createNewTab(target, CONST_ADD, null);
            }
        };
        return addButton;
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改字典"||"新增字典"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final UrgentDrillBean row) {
        //去掉第二个tab
        if (tabPanel.getModelObject().size() == 2) {
            tabPanel.getModelObject().remove(1);
        }
        tabPanel.add(new AjaxTab(Model.of(newTabType)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getLazyPanel(String panelId) {
                //通过repeatingView增加新的panel
                repeatingView.removeAll();
                UrgentDrillAddPage urgentDrillAddPage = new UrgentDrillAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(urgentDrillAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", UrgentDrillListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });

        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    //列表显示
    public class MainFragment extends Fragment {
        final WebMarkupContainer table;
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container, dialogWrapper;

        public MainFragment(String id, String markupId) {
            super(id, markupId, UrgentDrillListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add dialog

            dialogWrapper = new WebMarkupContainer("dialogWrapper") {
                @Override
                protected void onBeforeRender() {
                    if (dialog == null) {
                        addOrReplace(new Label("dialog"));
                    } else {
                        addOrReplace(dialog);
                    }
                    super.onBeforeRender();
                }
            };
            container.add(dialogWrapper.setOutputMarkupId(true));

            //add table
            table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));

            //add listview
            final DataView<UrgentDrillBean> listView = new DataView<UrgentDrillBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<UrgentDrillBean> item) {
                    final UrgentDrillBean row = item.getModelObject();
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("description", row.getDescription()));

                    AjaxLink alinkDocument = new AjaxLink("document") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createDocumentDialog(target, row, "文档列表");
                        }
                    };
                    alinkDocument.add(new Label("documentlabel", "文档(" + uploadDocumentService.getDocmentBeanListByAttach(row.getId()).size() + ")"));
                    item.add(alinkDocument.setOutputMarkupId(true));

                    AjaxLink alinkVideo = new AjaxLink("video") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createVideoDialog(target, row, "视频列表");
                        }
                    };
                    alinkVideo.add(new Label("videolabel", "视频(" + uploadVideoService.getVideoBeanListByAttach(row.getId()).size() + ")"));
                    item.add(alinkVideo.setOutputMarkupId(true));

                    AjaxLink alinkImage = new AjaxLink("image") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createImageDialog(target, row, "图片列表");
                        }
                    };
                    alinkImage.add(new Label("imagelabel", "图片(" + uploadImageService.getImageBeanListByAttach(row.getId()).size() + ")"));
                    item.add(alinkImage.setOutputMarkupId(true));

                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initUpdateButton(row));
                    //add upload document button
                    item.add(initUploadDocumentButton(row));
                    //add upload video button
                    item.add(initUploadVideoButton(row));
                    //add upload image button
                    item.add(initUploadImageButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<UrgentDrillBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new UrgentDrillBean()));
            TextField textField = new TextField("name");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
            //add button
            dictForm.add(initAddButton());

            add(dictForm);
        }

        private void createDocumentDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new DocumentListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createVideoDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new VideoListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createImageDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new ImageListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadDocumentDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new UploadDocumentPage("dialog", title, new CompoundPropertyModel<>(row)) {
                @Override
                public void updateTarget(AjaxRequestTarget target) {
                    target.add(table);
                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadVideoDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new UploadVideoPage("dialog", title, new CompoundPropertyModel<>(row)) {
                @Override
                public void updateTarget(AjaxRequestTarget target) {
                    target.add(table);
                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadImageDialog(AjaxRequestTarget target, final UrgentDrillBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new UploadImagePage("dialog", title, new CompoundPropertyModel<>(row)) {
                @Override
                public void updateTarget(AjaxRequestTarget target) {
                    target.add(table);
                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final UrgentDrillBean row) {
            //修改功能
            AjaxLink alink = new AjaxLink("edit") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createNewTab(target, CONST_EDIT, row);
                }
            };
            return alink;
        }

        /**
         * 初始化删除按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initDeleteButton(final UrgentDrillBean row) {
            //删除功能
            AjaxLink alink = new AjaxLink("delete") {
                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
                    AjaxCallListener listener = new AjaxCallListener();
                    listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
                    attributes.getAjaxCallListeners().add(listener);
                }

                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        urgentDrillBeanService.deleteEntity(row.getId());
                        feedbackPanel.info("删除成功！");
                        target.addChildren(getPage(), FeedbackPanel.class);
                        target.add(container);
                    } catch (Exception e) {
                        feedbackPanel.error("删除失败！");
                        e.printStackTrace();
                    }
                }
            };
            return alink;
        }

        /**
         * 初始化文档上传按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUploadDocumentButton(final UrgentDrillBean row) {
            AjaxLink alink = new AjaxLink("uploadDocument") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadDocumentDialog(target, row, "上传文档");
                }
            };
            return alink;
        }

        /**
         * 初始化视频上传按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUploadVideoButton(final UrgentDrillBean row) {
            AjaxLink alink = new AjaxLink("uploadVideo") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadVideoDialog(target, row, "上传视频");
                }
            };
            return alink;
        }

        /**
         * 初始化图片上传按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUploadImageButton(final UrgentDrillBean row) {
            AjaxLink alink = new AjaxLink("uploadImage") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadImageDialog(target, row, "上传图片");
                }
            };
            return alink;
        }

        /**
         * 初始化查询按钮
         *
         * @param provider
         * @param form
         * @return
         */
        private IndicatingAjaxButton initFindButton(final DictDataProvider provider, Form form) {

            return new IndicatingAjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    UrgentDrillBean dictBean = (UrgentDrillBean) form.getModelObject();
                    provider.setDictBean(dictBean);
                    target.add(container);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    target.add(feedbackPanel);
                }
            };
        }
    }

    /**
     * //查询数据提供者
     */
    class DictDataProvider extends ListDataProvider<UrgentDrillBean> {
        private UrgentDrillBean dictBean = null;

        public void setDictBean(UrgentDrillBean dictBean) {
            this.dictBean = dictBean;
        }

        @Override
        protected List<UrgentDrillBean> getData() {
            //类型为空时候，返回全部记录
            if (dictBean == null)
                return urgentDrillBeanService.getAllEntity();
            else {
                return urgentDrillBeanService.query(dictBean);
            }
        }
    }
}