package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.entities.RoleBean;
import com.daren.core.util.DateUtil;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
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
 * 项目名称:  urgent-project
 * 类描述:    角色列表页面类
 * 创建人:    sunlf
 * 创建时间:  2014/8/5 16:58
 * 修改人:    sunlf
 * 修改时间:  2014/8/5 16:58
 * 修改备注:  [说明本次修改内容]
 */
public class RoleListPage extends BasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "角色管理";
    private final static String CONST_ADD = "添加角色";
    private final static String CONST_EDIT = "编辑角色";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    //注入角色业务服务
    @Inject
    @Reference(id = "roleBeanService", serviceInterface = IRoleBeanService.class)
    private IRoleBeanService roleBeanService;

    //构造函数
    public RoleListPage(String id, WebMarkupContainer wmc) {
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
     * @param newTabType "修改角色"||"新增角色"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final RoleBean row) {
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
                RoleAddPage roleAddPage = new RoleAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(roleAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", RoleListPage.this);
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
        private final WebMarkupContainer container;

        public MainFragment(String id, String markupId) {
            super(id, markupId, RoleListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));

            //add listview
            final DataView<RoleBean> listView = new DataView<RoleBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<RoleBean> item) {
                    final RoleBean row = item.getModelObject();

                    item.add(new Label("name", row.getName()));
                    item.add(new Label("remark", row.getRemark()));
                    item.add(new Label("creationDate", DateUtil.convertDateToString(row.getCreationDate(), DateUtil.shortSdf)));
                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initUpdateButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<RoleBean> userForm = new Form<>("form", new CompoundPropertyModel<>(new RoleBean()));
            TextField textField = new TextField("name");
            textField.setRequired(false);
            userForm.add(textField.setOutputMarkupId(true));

            //find button
            userForm.add(initFindButton(provider, userForm));
            //add button
            userForm.add(initAddButton());

            add(userForm);
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initUpdateButton(final RoleBean row) {
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
        private AjaxLink initDeleteButton(final RoleBean row) {
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
                        roleBeanService.deleteEntity(row.getId());
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
                    RoleBean userBean = (RoleBean) form.getModelObject();
                    provider.setRoleBean(userBean);
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
    class DictDataProvider extends ListDataProvider<RoleBean> {
        private RoleBean roleBean = null;

        public void setRoleBean(RoleBean roleBean) {
            this.roleBean = roleBean;
        }

        @Override
        protected List<RoleBean> getData() {
            //类型为空时候，返回全部记录
            if (roleBean == null || roleBean.getName().equals(""))
                return roleBeanService.getAllEntity();
            else {
                return roleBeanService.query(roleBean);
            }
        }
    }
}
