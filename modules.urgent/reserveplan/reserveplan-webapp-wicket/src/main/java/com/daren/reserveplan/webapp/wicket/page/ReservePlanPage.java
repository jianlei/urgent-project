package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
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
import sun.net.dns.ResolverConfiguration;
import com.googlecode.wicket.jquery.core.Options;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanPage extends BasePanel {

    @Inject
    private IReservePlanBeanService reservePlanBeanService;
    private final TabbedPanel tabPanel;
    UserDataProvider provider = new UserDataProvider();
    final RepeatingView createPage = new RepeatingView("createPage");

    public ReservePlanPage(String id, WebMarkupContainer wmc) {

        super(id, wmc);
        //增加tabs支持
        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(wmc), options);
        this.add(tabPanel);


        final WebMarkupContainer webMarkupContainer = wmc;
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final WebMarkupContainer wmc) {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of("预案管理")) {

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
    private Form createQuery(final WebMarkupContainer table, final UserDataProvider provider, final TabbedPanel tabPanel, final WebMarkupContainer wmc) {
        //处理查询
        Form<ReservePlanBean> myform = new Form<>("form", new CompoundPropertyModel<>(new ReservePlanBean()));
        TextField textField = new TextField("name");

        myform.add(textField.setOutputMarkupId(true));


        AjaxButton findButton = new AjaxButton("find", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ReservePlanBean userBean = (ReservePlanBean) form.getModelObject();
                provider.setReservePlanBean(userBean);
                target.add(table);
            }
        };

        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (tabPanel.getModelObject().size() == 1) {
                    tabPanel.add(new AjaxTab(Model.of("预案编辑")) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public WebMarkupContainer getLazyPanel(String panelId) {
                            try {
                                // sleep the thread for a half second to simulate a long load
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                error(e.getMessage());
                            }
                            ReservePlanEditPage reservePlanEditPage = new ReservePlanEditPage(createPage.newChildId(), wmc, null);
                            createPage.add(reservePlanEditPage);
                            Fragment fragment = new Fragment(panelId, "panel-2", ReservePlanPage.this);
                            fragment.add(createPage);
                            return fragment;
                        }
                    });
                }
                tabPanel.setActiveTab(1);
                target.add(tabPanel);
            }

        };
        myform.add(addButton);
        myform.add(findButton);
        return myform;
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId, final WebMarkupContainer wmc) {
            super(id, markupId, ReservePlanPage.this);

            final WebMarkupContainer table = new WebMarkupContainer("table");
            add(table.setOutputMarkupId(true));

            final DataView<ReservePlanBean> listView = new DataView<ReservePlanBean>("rows", provider, 10) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<ReservePlanBean> item) {
                    final ReservePlanBean reservePlanBean = item.getModelObject();

                    item.add(new Label("name", reservePlanBean.getName()));

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
                            reservePlanBeanService.deleteEntity(reservePlanBean.getId());
                            target.add(table);
                        }
                    };
                    item.add(alink.setOutputMarkupId(true));
                }
            };

            CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);
            table.add(listView);
            add(createQuery(table, provider, tabPanel, wmc));
        }
    }

    class UserDataProvider extends ListDataProvider<ReservePlanBean> {
        private ReservePlanBean userBean = null;
        public void setReservePlanBean(ReservePlanBean userBean) {
            this.userBean = userBean;
        }
        @Override
        protected List<ReservePlanBean> getData() {
            if (userBean == null)
                return reservePlanBeanService.getAllEntity();
            else {
                return reservePlanBeanService.getAllEntity();
            }
        }
    }
}