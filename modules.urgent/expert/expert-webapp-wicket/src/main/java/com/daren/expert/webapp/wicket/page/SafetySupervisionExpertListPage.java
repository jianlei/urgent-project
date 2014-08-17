package com.daren.expert.webapp.wicket.page;

import com.daren.core.util.DateUtil;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.expert.api.biz.ISafetySupervisionExpertBeanService;
import com.daren.expert.entities.SafetySupervisionExpertBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
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
 * 行管专家管理
 * Created by Administrator on 2014/8/6.
 */
public class SafetySupervisionExpertListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "行管专家";
    private final static String CONST_ADD = "添加专家";
    private final static String CONST_EDIT = "编辑专家";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    //注入服务
    @Inject
    @Reference(id = "safetySupervisionExpertBeanService", serviceInterface = ISafetySupervisionExpertBeanService.class)
    private ISafetySupervisionExpertBeanService safetySupervisionExpertBeanService;

    //构造函数
    public SafetySupervisionExpertListPage(String id, WebMarkupContainer wmc) {
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
    private AjaxButton initAddButton() {
        //新增
        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
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
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final SafetySupervisionExpertBean row) {
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
                SafetySupervisionExpertAddPage safetySupervisionExpertAddPage = new SafetySupervisionExpertAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(safetySupervisionExpertAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", SafetySupervisionExpertListPage.this);
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
            super(id, markupId, SafetySupervisionExpertListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));

            //add listview
            final DataView<SafetySupervisionExpertBean> listView = new DataView<SafetySupervisionExpertBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<SafetySupervisionExpertBean> item) {
                    final SafetySupervisionExpertBean row = item.getModelObject();
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("date", DateUtil.convertDateToString(row.getDate(), DateUtil.shortSdf)));
                    item.add(new Label("sex", row.getSex()));
                    item.add(new Label("skillTitle", row.getSkillTitle()));
                    item.add(new Label("degree", row.getDegree()));
                    item.add(new Label("nation", row.getNation()));
                    item.add(new Label("type", row.getType()));
                    item.add(new Label("city", row.getCity()));
                    item.add(new Label("address", row.getAddress()));
                    item.add(new Label("tel", row.getTel()));
                    item.add(new Label("phone", row.getPhone()));
                    item.add(new Label("eMail", row.geteMail()));
                    item.add(new Label("language", row.getLanguage()));
                    item.add(new Label("domain", row.getDomain()));
                    item.add(new Label("direction", row.getDirection()));
                    item.add(new Label("longitude", row.getLongitude()));
                    item.add(new Label("latitude", row.getLatitude()));
                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initUpdateButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<SafetySupervisionExpertBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new SafetySupervisionExpertBean()));
            TextField textField = new TextField("type");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
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
        private AjaxLink initUpdateButton(final SafetySupervisionExpertBean row) {
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
        private AjaxLink initDeleteButton(final SafetySupervisionExpertBean row) {
            //删除功能
            AjaxLink alink = new AjaxLink("delete") {
                @Override
                protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                    super.updateAjaxAttributes(attributes);
                    AjaxCallListener listener = new AjaxCallListener();
                    listener.onPrecondition("if(!confirm('确定要删除吗')){return false;}");
                    attributes.getAjaxCallListeners().add(listener);
                }

                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        safetySupervisionExpertBeanService.deleteEntity(row.getId());
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
         * 初始化查询按钮
         *
         * @param provider
         * @param form
         * @return
         */
        private AjaxButton initFindButton(final DictDataProvider provider, Form form) {

            return new AjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    SafetySupervisionExpertBean dictBean = (SafetySupervisionExpertBean) form.getModelObject();
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
    class DictDataProvider extends ListDataProvider<SafetySupervisionExpertBean> {
        private SafetySupervisionExpertBean dictBean = null;

        public void setDictBean(SafetySupervisionExpertBean dictBean) {
            this.dictBean = dictBean;
        }

        @Override
        protected List<SafetySupervisionExpertBean> getData() {
            //类型为空时候，返回全部记录
            if (dictBean == null || dictBean.getType().equals(""))
                return safetySupervisionExpertBeanService.getAllEntity();
            else {
                return safetySupervisionExpertBeanService.query(dictBean);
            }
        }
    }
}