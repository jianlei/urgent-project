package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 20:36
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 20:36
 * 修改备注:  [说明本次修改内容]
 */
public class Page1 extends BasePanel {
    private static final int pageNum = 3;
    private final TabbedPanel tabPanel;
    UserDataProvider provider = new UserDataProvider();
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;

    public Page1(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        //增加tabs支持
//        this.add(new JQueryBehavior("#tabs", "tabs"));

        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(), options);
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList() {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of("用户管理")) {

            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new MainFragment(panelId, "panel-1");
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
    private Form createQuery(final WebMarkupContainer table, final UserDataProvider provider, final TabbedPanel tabPanel) {
        //处理查询
        Form<UserBean> myform = new Form<>("form", new CompoundPropertyModel<>(new UserBean()));
        TextField textField = new TextField("name");

        myform.add(textField.setOutputMarkupId(true));


        IndicatingAjaxButton findButton = new IndicatingAjaxButton("find", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    // sleep the thread for a half second to simulate a long load
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    error(e.getMessage());
                }
                UserBean userBean = (UserBean) form.getModelObject();
                provider.setUserBean(userBean);
                target.add(table);
            }
        };

        IndicatingAjaxButton addButton = new IndicatingAjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (tabPanel.getModelObject().size() == 1) {
                    tabPanel.add(new AjaxTab(Model.of("Tab (ajax)")) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public WebMarkupContainer getLazyPanel(String panelId) {
                            try {
                                // sleep the thread for a half second to simulate a long load
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                error(e.getMessage());
                            }

                            return new Fragment(panelId, "panel-2", Page1.this);
                        }
                    });
                }
                tabPanel.setActiveTab(1);
                target.add(tabPanel);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
            }
        };
        myform.add(addButton);
        myform.add(findButton);
        return myform;
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId) {
            super(id, markupId, Page1.this);

            final WebMarkupContainer table = new WebMarkupContainer("table");
            add(table.setOutputMarkupId(true));

            final DataView<UserBean> listView = new DataView<UserBean>("rows", provider, pageNum) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<UserBean> item) {
                    final UserBean row = item.getModelObject();

                    item.add(new Label("col1", row.getName()));
                    item.add(new Label("col2", row.getLoginName()));
                    item.add(new Label("col3", row.getPhone()));

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
                        /*target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+row.getId()
     *//* you probably want to encode this first *//*+"')");*/
                            userBeanService.delUser(row);
                            // target.add(new HomePage());
                            target.add(table);
                        }
                    };
//                alink.add(new Label("linklabel", "删除").setOutputMarkupId(true));
                    item.add(alink.setOutputMarkupId(true));
                }
            };

            CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);
//        table.setVersioned(false);


            table.add(listView);
            add(createQuery(table, provider, tabPanel));
        }
    }

    class UserDataProvider extends ListDataProvider<UserBean> {
        private UserBean userBean = null;

        public void setUserBean(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected List<UserBean> getData() {
            if (userBean == null)
                return userBeanService.getAllUser();
            else {
                return userBeanService.queryUser(userBean);
            }
        }
    }
}


