package com.daren.regulation.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.regulation.api.biz.IRegulationBeanService;
import com.daren.regulation.entities.RegulationBean;
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
 * @类描述：法律法规
 * @创建人：张清欣
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class RegulationListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "法律法规";
    private final static String CONST_ADD = "添加法律法规";
    private final static String CONST_EDIT = "编辑法律法规";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    IrisAbstractDialog dialog;
    Fragment fragment;
    //注入服务
    @Inject
    @Reference(id = "regulationBeanService", serviceInterface = IRegulationBeanService.class)
    private IRegulationBeanService regulationBeanService;
//    final DocumentListPage dialog;

    //构造函数
    public RegulationListPage(String id, WebMarkupContainer wmc) {
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
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final RegulationBean row) {
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
                RegulationAddPage regulationAddPage = new RegulationAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(regulationAddPage);
                fragment = new Fragment(panelId, "addPanel", RegulationListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });

        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    //列表显示
    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container, dialogWrapper;
        final WebMarkupContainer table;

        public MainFragment(String id, String markupId) {
            super(id, markupId, RegulationListPage.this);

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
            final DataView<RegulationBean> listView = new DataView<RegulationBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<RegulationBean> item) {
                    final RegulationBean row = item.getModelObject();
                    item.add(new Label("col1", row.getName()));
                    item.add(new Label("col2", row.getDescription()));

                    AjaxLink alinkDocument = new AjaxLink("document") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            createDialog(target, row, "文档列表");
                        }
                    };
                    alinkDocument.add(new Label("documentlabel", "文档"));
                    item.add(alinkDocument.setOutputMarkupId(true));

                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initUpdateButton(row));
                    //add upload button
                    item.add(initUploadDocumentButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<RegulationBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new RegulationBean()));
            TextField textField = new TextField("name");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
            //add button
            dictForm.add(initAddButton());
            add(dictForm);
        }

        /**
         * 创建dialog
         *
         * @param target
         * @param row
         * @param title
         */
        private void createDialog(AjaxRequestTarget target, final RegulationBean row, final String title) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new DocumentListPage("dialog", title, new CompoundPropertyModel<>(row));
            target.add(dialogWrapper);
            dialog.open(target);
        }

        private void createUploadDialog(AjaxRequestTarget target, final RegulationBean row, final String title) {
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

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final RegulationBean row) {
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
        private AjaxLink initDeleteButton(final RegulationBean row) {
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
                        regulationBeanService.deleteEntity(row.getId());
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
        private AjaxLink initUploadDocumentButton(final RegulationBean row) {
            AjaxLink alink = new AjaxLink("uploadDocument") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createUploadDialog(target, row, "上传文档");
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
                    RegulationBean dictBean = (RegulationBean) form.getModelObject();
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
    class DictDataProvider extends ListDataProvider<RegulationBean> {
        private RegulationBean dictBean = null;

        public void setDictBean(RegulationBean dictBean) {
            this.dictBean = dictBean;
        }

        @Override
        protected List<RegulationBean> getData() {
            //类型为空时候，返回全部记录
            if (dictBean == null)
                return regulationBeanService.getAllEntity();
            else {
                return regulationBeanService.query(dictBean);
            }
        }
    }
}
