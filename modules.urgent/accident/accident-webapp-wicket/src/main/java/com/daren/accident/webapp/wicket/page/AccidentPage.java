package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
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
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import com.googlecode.wicket.jquery.core.Options;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentPage extends BasePanel {

    @Inject
    private IAccidentBeanService accidentBeanService;

    private final TabbedPanel tabPanel;
    AccidentDataProvider provider = new AccidentDataProvider();
    final RepeatingView createPage = new RepeatingView("createPage");
    AccidentViewPage accidentViewPage;
    AccidentEditPage accidentEditPage;
    Fragment fragment;

    public AccidentPage(String id, WebMarkupContainer wmc) {

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
        tabs.add(new AbstractTab(Model.of("事故管理")) {
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
    private Form createQuery(final WebMarkupContainer table, final AccidentDataProvider provider, final TabbedPanel tabPanel, final WebMarkupContainer wmc) {
        //处理查询
        Form<AccidentBean> myform = new Form<>("form", new CompoundPropertyModel<>(new AccidentBean()));
        TextField textField = new TextField("accidentTitle");
        myform.add(textField.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean userBean = (AccidentBean) form.getModelObject();
                provider.setAccidentBean(userBean);
                target.add(table);
            }
        };

        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                goAccidentEditPage(wmc, null, target);
            }
        };
        myform.add(addButton);
        myform.add(findButton);
        return myform;
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId, final WebMarkupContainer wmc) {
            super(id, markupId, AccidentPage.this);
            final WebMarkupContainer table = new WebMarkupContainer("table");
            add(table.setOutputMarkupId(true));
            final DataView<AccidentBean> listView = new DataView<AccidentBean>("rows", provider, 10) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<AccidentBean> item) {
                    final AccidentBean accidentBean = item.getModelObject();

                    AjaxLink ajaxLink = new AjaxLink("accidentTitleLink") {
                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            goAccidentViewPage(wmc,accidentBean,target);
                        }
                    };
                    ajaxLink.add(new Label("accidentTitle", accidentBean.getAccidentTitle()));

                    item.add(ajaxLink);
                    item.add(new Label("accidentLevel", accidentBean.getAccidentLevel()));
                    item.add(new Label("accidentType", accidentBean.getAccidentType()));
                    item.add(new Label("accidentTime", accidentBean.getAccidentTime()));
                    addDeleteLink(item, "delete", accidentBean, table);
                }
            };
            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);
            table.add(listView);
            add(createQuery(table, provider, tabPanel, wmc));
        }
    }

    private void addDeleteLink(Item<AccidentBean> item, String linkName, final AccidentBean accidentBean, final WebMarkupContainer table) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener listener = new AjaxCallListener();
                listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
                attributes.getAjaxCallListeners().add(listener);
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                accidentBeanService.deleteEntity(accidentBean.getId());
                target.add(table);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    public void goAccidentViewPage( final WebMarkupContainer wmc,final AccidentBean accidentBean,AjaxRequestTarget target){
        if (tabPanel.getModelObject().size() == 1) {
            tabPanel.add(new AjaxTab(Model.of("事故详细信息")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    try {
                        // sleep the thread for a half second to simulate a long load
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        error(e.getMessage());
                    }
                    accidentViewPage = new AccidentViewPage(createPage.newChildId(), wmc, accidentBean, AccidentPage.this);
                    createPage.add(accidentViewPage);
                    fragment = new Fragment(panelId, "panel-2", AccidentPage.this);
                    fragment.add(createPage);
                    return fragment;
                }
            });
        }else {
            accidentViewPage = new AccidentViewPage(createPage.newChildId(), wmc, accidentBean, AccidentPage.this);
            createPage.removeAll();
            createPage.add(accidentViewPage);
            fragment.removeAll();
            fragment.add(createPage);
            target.add(fragment);
        }
        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    public void goAccidentEditPage( final WebMarkupContainer wmc,final AccidentBean accidentBean,AjaxRequestTarget target){
        if (tabPanel.getModelObject().size() == 1) {
            tabPanel.add(new AjaxTab(Model.of("事故详细信息")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    try {
                        // sleep the thread for a half second to simulate a long load
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        error(e.getMessage());
                    }
                    accidentEditPage = new AccidentEditPage(createPage.newChildId(), wmc, accidentBean,AccidentPage.this);
                    createPage.add(accidentEditPage);
                    fragment = new Fragment(panelId, "panel-2", AccidentPage.this);
                    fragment.add(createPage);
                    return fragment;
                }
            });
        }else {
            accidentEditPage = new AccidentEditPage(createPage.newChildId(), wmc, accidentBean,AccidentPage.this);
            createPage.removeAll();
            createPage.add(accidentEditPage);
            fragment.removeAll();
            fragment.add(createPage);
            target.add(fragment);
        }
        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    class AccidentDataProvider extends ListDataProvider<AccidentBean> {
        private AccidentBean accidentBean = null;

        public void setAccidentBean(AccidentBean accidentBean) {
            this.accidentBean = accidentBean;
        }

        @Override
        protected List<AccidentBean> getData() {
            if (accidentBean == null || null == accidentBean.getAccidentTitle() || "".equals(accidentBean.getAccidentTitle().trim()))
                return accidentBeanService.getAllEntity();
            else {
                return accidentBeanService.getAllEntity();
            }
        }
    }
}
