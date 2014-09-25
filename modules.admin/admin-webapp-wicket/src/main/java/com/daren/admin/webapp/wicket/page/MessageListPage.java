package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IMessageBeanService;
import com.daren.admin.entities.MessageBean;
import com.daren.core.util.DateUtil;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisDeleteAjaxLink;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
 * @类描述：系统消息列表页面
 * @创建人：sunlf
 * @创建时间：2014-09-25 下午10:46
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MessageListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "系统消息管理";
    private final static String CONST_ADD = "添加系统消息";
    private final static String CONST_EDIT = "编辑系统消息";
    private final TabbedPanel tabPanel;
    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    MsgDataProvider provider = new MsgDataProvider();
    //注入字典业务服务
    @Inject
    private IMessageBeanService messageBeanService;


    //构造函数
    public MessageListPage(String id, WebMarkupContainer wmc) {
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
    private AjaxButton initAddButton(Form form) {
        //新增
        AjaxButton addButton = new AjaxButton("add", form) {
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
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改字典"||"新增字典"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final MessageBean row) {
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
                MessageAddPage dictAddPage = new MessageAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(dictAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", MessageListPage.this);
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
            super(id, markupId, MessageListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));

            //add listview
            final DataView<MessageBean> listView = new DataView<MessageBean>("rows", provider, numPerPage) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<MessageBean> item) {
                    final MessageBean row = item.getModelObject();

                    item.add(new Label("title", row.getTitle()));//消息名称
                    item.add(new Label("message", row.getMessage()));//消息内容
                    item.add(new Label("sender", row.getSender()));//发送人
                    item.add(new Label("creationDate", DateUtil.convertDateToString(row.getCreationDate(), DateUtil.longSdf
                    )));//创建日期
                    String status=row.getStatus()==0?"未读":"已读";
                    item.add(new Label("status", status));//消息状态
                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
//                    item.add(initUpdateButton(row));
                }
            };
            table.add(listView);

            //增加分页指示器
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);

            //增加form
            Form<MessageBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new MessageBean()));
            TextField textField = new TextField("title");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
            //add button
            dictForm.add(initAddButton(dictForm));

            add(dictForm);
        }

        /**
         * 初始化更新按钮
         *
         * @param row 数据
         * @return link
         *//*
        private AjaxLink initUpdateButton(final MessageBean row) {
            //修改功能
            AjaxLink alink = new AjaxLink("edit") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createNewTab(target, CONST_EDIT, row);
                }
            };
            return alink;
        }*/

        /**
         * 初始化删除按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initDeleteButton(final MessageBean row) {
            //删除功能
            IrisDeleteAjaxLink alink = new IrisDeleteAjaxLink("delete") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        messageBeanService.deleteEntity(row.getId());
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
        private AjaxButton initFindButton(final MsgDataProvider provider, Form form) {

            return new AjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    MessageBean dictBean = (MessageBean) form.getModelObject();
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
     * //查询数据提供者
     */
    class MsgDataProvider extends ListDataProvider<MessageBean> {
        private MessageBean bean = null;

        public void setBean(MessageBean bean) {
            this.bean = bean;
        }

        @Override
        protected List<MessageBean> getData() {
            //类型为空时候，返回全部记录
            if (bean == null || bean.getTitle().equals(""))
                return messageBeanService.getAllEntity();
            else {
                return messageBeanService.query(bean);
            }
        }
    }
}
