package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.workflow.webapp.wicket.page.WorkflowBasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import org.activiti.engine.FormService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：危险化学品列表页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageListPage extends WorkflowBasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "新建流程";
    @Inject
    @Reference(id = "chemistryManageBeanService", serviceInterface = IChemistryManageBeanService.class)
    protected IChemistryManageBeanService chemistryManageBeanService;
    ChemistryManageDataProvider provider = new ChemistryManageDataProvider();
    @Inject
    private transient FormService formService;
    private WebMarkupContainer wmc;

    public ChemistryManageListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc, CONST_LIST);

    }

    @Override
    public Fragment getMainFragment(String panelId, String makeupId) {
        return new MainFragment(panelId, makeupId);
    }

    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;

        public MainFragment(String id, String markupId) {
            super(id, markupId, ChemistryManageListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));

            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));
            //add listview
            final DataView<ChemistryManageBean> listView = new DataView<ChemistryManageBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<ChemistryManageBean> item) {
                    final ChemistryManageBean row = item.getModelObject();
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("header", row.getHeader()));
                    item.add(new Label("phone", row.getPhone()));
                    item.add(new Label("address", row.getAddress()));
                    item.add(new Label("unitType", row.getUnitType()));
                    item.add(new Label("linkHandle", row.getLinkHandle()));

                    item.add(initUploadButton(row));
                    item.add(initSubMitButton(row));
                    item.add(initUpdateButton(row));
                }
            };
            table.add(listView);

            //search form
            Form<ChemistryManageBean> form = new Form<>("form", new CompoundPropertyModel<>(new ChemistryManageBean()));
            TextField textField = new TextField("name");
            form.add(textField.setOutputMarkupId(true));
            add(form);
            form.add(initFindButton(provider, form));
            form.add(initCreateButton());

        }

        /**
         * 初始化查询按钮
         *
         * @param provider
         * @param form
         * @return
         */
        private AjaxButton initFindButton(final ChemistryManageDataProvider provider, Form form) {
            return new AjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    ChemistryManageBean dictBean = (ChemistryManageBean) form.getModelObject();
                    provider.setBean(dictBean);
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
     * 初始化修改按钮
     *
     * @param row 数据
     * @return link
     */
    private IndicatingAjaxLink initUpdateButton(final ChemistryManageBean row) {
        //修改功能
        IndicatingAjaxLink alink = new IndicatingAjaxLink("update") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createNewTab(target, CONST_LIST, row);
            }
        };
        return alink;
    }

    /**
     * 初始化上传按钮
     *
     * @param row 数据
     * @return link
     */
    private AjaxLink initUploadButton(final ChemistryManageBean row) {
        //修改功能
        AjaxLink alink = new AjaxLink("upload") {
            @Override
            public void onClick(AjaxRequestTarget target) {
//                createNewTab(target, CONST_EDIT, row);
            }
        };
        return alink;
    }

    /**
     * 初始化提交按钮
     *
     * @param row 数据
     * @return link
     */
    private AjaxLink initSubMitButton(final ChemistryManageBean row) {
        //修改功能
        AjaxLink alink = new AjaxLink("submit") {
            @Override
            public void onClick(AjaxRequestTarget target) {
//                createNewTab(target, CONST_EDIT, row);
                target.appendJavaScript("alert('" + row.getName() + "');");
            }
        };
        return alink;
    }

    /**
     * 初始化新建按钮
     *
     * @return link
     */
    private AjaxButton initCreateButton() {
        //新增
        AjaxButton addButton = new AjaxButton("create") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_LIST, null);
            }
        };
        return addButton;
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改"||"新增"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final ChemistryManageBean row) {
        //去掉第二个tab
        if (tabPanel.getModelObject().size() == 2) {
            tabPanel.getModelObject().remove(1);
        }
        tabPanel.add(new AjaxTab(Model.of(newTabType)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getLazyPanel(String panelId) {
                //通过repeatingView增加新的panel
//
                ChemistryManageAddPage chemistryManageAddPage = new ChemistryManageAddPage(repeatingView.newChildId(), Model.of(row), newTabType) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.removeAll();
                repeatingView.add(chemistryManageAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", ChemistryManageListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });
        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    /**
     * 查询数据提供者
     */
    class ChemistryManageDataProvider extends ListDataProvider<ChemistryManageBean> {
        private ChemistryManageBean bean = null;

        public void setBean(ChemistryManageBean bean) {
            this.bean = bean;
        }

        @Override
        protected List<ChemistryManageBean> getData() {
            //类型为空时候，返回全部记录
            if (bean == null || bean.getName().equals(""))
                return chemistryManageBeanService.getAllEntity();
            else {
                return chemistryManageBeanService.query(bean);
            }
        }
    }
}
