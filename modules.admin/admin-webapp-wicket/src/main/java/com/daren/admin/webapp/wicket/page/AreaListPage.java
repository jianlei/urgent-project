package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IAreaBeanService;
import com.daren.admin.entities.AreaBean;
import com.daren.admin.webapp.wicket.data.AreaTreeProvider;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisDeleteAjaxLink;
import com.daren.core.web.component.table.IrisTableTree;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.table.TreeColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目名称:  urgent-project
 * 类描述:    区域列表类
 * 创建人:    sunlf
 * 创建时间:  2014/8/7 15:54
 * 修改人:    sunlf
 * 修改时间:  2014/8/7 15:54
 * 修改备注:  [说明本次修改内容]
 */
public class AreaListPage extends BasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "区域管理";
    private final static String CONST_ADD = "添加区域";
    private final static String CONST_EDIT = "编辑区域";
    private final static String CONST_ADD_CHILD = "添加子区域";
    private final static String CONST_TYPE = "area_relation"; //type const

    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    private AreaTreeProvider provider = new AreaTreeProvider();
    private Map<String, String> typeMap;
    //注入区域业务服务
    @Inject
    @Reference(id = "areaBeanService", serviceInterface = IAreaBeanService.class)
    private IAreaBeanService areaBeanService;

    /*//注入字典业务服务
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;*/

    //构造函数
    public AreaListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        tabPanel = new TabbedPanel("tabs", this.newTabList(), new Options());
        typeMap = dictBeanService.getDictMap(CONST_TYPE);

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
    private AjaxButton initAddButton() {
        //新增
        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_ADD, null);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_ADD, null);
            }
        };
        return addButton;
    }

    /**
     * 初始化新增child按钮
     *
     * @return
     */
    private AjaxLink initAddChildButton(final AreaBean bean) {
        //新增child
        AjaxLink addButton = new AjaxLink("addChild") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createNewTab(target, CONST_ADD_CHILD, bean);
            }
        };
        return addButton;
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改区域"||"新增区域"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final AreaBean row) {
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
                AreaAddPage dictAddPage;
                if (newTabType.equals(CONST_ADD_CHILD)) {
                    dictAddPage = new AreaAddPage(repeatingView.newChildId(), newTabType, null, row) {
                        //关闭新增tab
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            removeTab(target);
                        }
                    };
                } else {
                    dictAddPage = new AreaAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                        //关闭新增tab
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            removeTab(target);
                        }
                    };

                }
                repeatingView.add(dictAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", AreaListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }

            private void removeTab(AjaxRequestTarget target) {
                //刷新数据
                provider.getData();
                if (tabPanel.getModelObject().size() == 2)
                    tabPanel.getModelObject().remove(1);
                target.add(tabPanel);
            }
        });

        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    //列表显示
    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;

        public MainFragment(String id, String markupId) {
            super(id, markupId, AreaListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //添加数表
            IrisTableTree<AreaBean, String> tree = new IrisTableTree<AreaBean, String>(
                    "tree", treeColumns(), provider, numPerPage) {
            };

            container.add(tree.setOutputMarkupId(true));

            //增加form
            Form<AreaBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new AreaBean()));
            //add button
            dictForm.add(initAddButton());

            add(dictForm);
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final AreaBean row) {
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
        private AjaxLink initDeleteButton(final AreaBean row) {
            //删除功能
            IrisDeleteAjaxLink alink = new IrisDeleteAjaxLink("delete") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        areaBeanService.deleteEntity(row.getId());
                        feedbackPanel.info("删除成功！");
                        target.addChildren(getPage(), JQueryFeedbackPanel.class);
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
         * 创建列
         *
         * @return
         */
        private List<? extends IColumn<AreaBean, String>> treeColumns() {
            List<IColumn<AreaBean, String>> columns = new ArrayList<IColumn<AreaBean, String>>();
            columns.add(new TreeColumn<AreaBean, String>(Model.of("区域名称"), null));
            columns.add(new PropertyColumn<AreaBean, String>(Model.of("区域编码"), "code"));
            columns.add(new PropertyColumn<AreaBean, String>(Model.of("区域类型"), "type") {
                //通过key值获得对应的value
                @Override
                public void populateItem(Item<ICellPopulator<AreaBean>> item, String componentId, IModel<AreaBean> rowModel) {
                    item.add(new Label(componentId, typeMap.get(rowModel.getObject().getType())));
                }
            });
            columns.add(new AbstractColumn<AreaBean, String>(Model.of("操作")) {
                @Override
                public void populateItem(Item<ICellPopulator<AreaBean>> cellItem, String componentId, IModel<AreaBean> rowModel) {
                    /*cellItem.add(new AttributeModifier("style", new
                            Model<String>("width:20px;")));*/
                    cellItem.add(new ActionFragment(componentId, "action", MainFragment.this, rowModel));
                }
            });
            return columns;
        }

        /**
         * 操作Fragment类
         */
        private class ActionFragment extends Fragment {
            public ActionFragment(String id, String markupId, MarkupContainer markupProvider, IModel<AreaBean> model) {
                super(id, markupId, markupProvider, model);
                AreaBean bean = model.getObject();
                add(initDeleteButton(bean));
                add(initUpdateButton(bean));
                add(initAddChildButton(bean));
            }
        }
    }
}
