package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.entities.PermissionBean;
import com.daren.admin.webapp.wicket.data.PermissionTreeProvider;
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
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.table.TreeColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
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

/**
 * @类描述：权限管理列表类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午11:04
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class PermissionListPage extends BasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "权限管理";
    private final static String CONST_ADD = "添加权限";
    private final static String CONST_EDIT = "编辑权限";
    private final static String CONST_ADD_CHILD = "添加子权限";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    private PermissionTreeProvider provider = new PermissionTreeProvider();
    //注入权限业务服务
    @Inject
    @Reference(id = "permissionBeanService", serviceInterface = IPermissionBeanService.class)
    private IPermissionBeanService permissionBeanService;

    //构造函数
    public PermissionListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        tabPanel = new TabbedPanel("tabs", this.newTabList(), new Options());
        this.add(tabPanel);
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改权限"||"新增权限"
     * @param row        数据
     */
    private void createNewTab(final AjaxRequestTarget target, final String newTabType, final PermissionBean row) {
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
                PermissionAddPage dictAddPage = null;
                if (newTabType.equals(CONST_ADD_CHILD)) {
                    dictAddPage = new PermissionAddPage(repeatingView.newChildId(), newTabType, null, row) {
                        //关闭新增tab
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            removeTab(target);
                        }
                    };
                } else {
                    dictAddPage = new PermissionAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                        //关闭新增tab
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            removeTab(target);
                        }
                    };

                }

                repeatingView.add(dictAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", PermissionListPage.this);
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
    private AjaxLink initAddChildButton(final PermissionBean permissionBean) {
        //新增
        AjaxLink addButton = new AjaxLink("addChild") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createNewTab(target, CONST_ADD_CHILD, permissionBean);
            }
        };
        return addButton;
    }


    //列表显示
    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;

        public MainFragment(String id, String markupId) {
            super(id, markupId, PermissionListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //添加数表
            IrisTableTree<PermissionBean, String> tree = new IrisTableTree<PermissionBean, String>(
                    "tree", treeColumns(), provider, numPerPage) {
            };

            container.add(tree.setOutputMarkupId(true));

            //增加form
            Form<PermissionBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new PermissionBean()));
            //add button
            dictForm.add(initAddButton());

            add(dictForm);
        }

        /**
         * 创建列
         *
         * @return
         */
        private List<? extends IColumn<PermissionBean, String>> treeColumns() {
            List<IColumn<PermissionBean, String>> columns = new ArrayList<IColumn<PermissionBean, String>>();
            columns.add(new TreeColumn<PermissionBean, String>(Model.of("名称"), null));
//            columns.add(new PropertyColumn<PermissionBean, String>(Model.of("名称"), "name"));
            columns.add(new PropertyColumn<PermissionBean, String>(Model.of("序号"), "sort"));
            columns.add(new PropertyColumn<PermissionBean, String>(Model.of("是否显示"), "isShow"));
            columns.add(new PropertyColumn<PermissionBean, String>(Model.of("权限标识"), "permission"));
            columns.add(new AbstractColumn<PermissionBean, String>(Model.of("操作")) {
                @Override
                public void populateItem(Item<ICellPopulator<PermissionBean>> cellItem, String componentId, IModel<PermissionBean> rowModel) {
                    /*cellItem.add(new AttributeModifier("style", new
                            Model<String>("width:20px;")));*/
                    cellItem.add(new ActionFragment(componentId, "action", MainFragment.this, rowModel));
                }
            });
            return columns;
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final PermissionBean row) {
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
        private AjaxLink initDeleteButton(final PermissionBean row) {
            //删除功能
            AjaxLink alink = new AjaxLink("delete") {
                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
                    AjaxCallListener listener = new AjaxCallListener();
                    listener.onPrecondition("if(!confirm('" + getString("urgent.delete.confirm") + "')){return false;}");
                    attributes.getAjaxCallListeners().add(listener);
                }

                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        permissionBeanService.deleteEntity(row.getId());
                        feedbackPanel.info("删除成功！");
                        target.addChildren(getPage(), JQueryFeedbackPanel.class);
                        target.add(tabPanel);
                    } catch (Exception e) {
                        feedbackPanel.error("删除失败！");
                        e.printStackTrace();
                    }
                }
            };
            return alink;
        }

        /**
         * 操作Fragment类
         */
        private class ActionFragment extends Fragment {
            public ActionFragment(String id, String markupId, MarkupContainer markupProvider, IModel<PermissionBean> model) {
                super(id, markupId, markupProvider, model);
                PermissionBean bean = model.getObject();
                add(initDeleteButton(bean));
                add(initUpdateButton(bean));
                add(initAddChildButton(bean));
            }
        }
    }
}
