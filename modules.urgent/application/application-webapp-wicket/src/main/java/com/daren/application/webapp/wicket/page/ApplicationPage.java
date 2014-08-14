package com.daren.application.webapp.wicket.page;

import com.daren.application.api.biz.IApplicationBeanService;
import com.daren.application.entities.ApplicationBean;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ApplicationPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    private final TabbedPanel tabPanel;
    ApplicationDataProvider provider = new ApplicationDataProvider();
    @Inject
    private IApplicationBeanService applicationBeanService;

    @Inject
    private IUploadDocumentService uploadDocumentService;

    public ApplicationPage(String id, WebMarkupContainer wmc) {

        super(id, wmc);
        Options options = new Options();
        options.set("collapsible", true);
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
        tabs.add(new AbstractTab(Model.of("预案模板管理")) {

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
    private Form createQuery(final WebMarkupContainer table, final ApplicationDataProvider provider, final TabbedPanel tabPanel, final WebMarkupContainer wmc) {
        //处理查询
        Form<ApplicationBean> myform = new Form<>("form", new CompoundPropertyModel<>(new ApplicationBean()));

        AjaxButton refreshButton = new AjaxButton("refresh", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ApplicationBean userBean = (ApplicationBean) form.getModelObject();
                provider.setApplicationBean(userBean);
                target.add(table);
            }
        };

        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (tabPanel.getModelObject().size() == 1) {
                    tabPanel.add(new AjaxTab(Model.of("新增预案模板")) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public WebMarkupContainer getLazyPanel(String panelId) {
                            try {
                                // sleep the thread for a half second to simulate a long load
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                error(e.getMessage());
                            }
                            ApplicationCreatePage applicationCreatePage = new ApplicationCreatePage(createPage.newChildId(), wmc, null);
                            createPage.add(applicationCreatePage);
                            Fragment fragment = new Fragment(panelId, "panel-2", ApplicationPage.this);
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
        myform.add(refreshButton);
        return myform;
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId, final WebMarkupContainer wmc) {
            super(id, markupId, ApplicationPage.this);

            final WebMarkupContainer table = new WebMarkupContainer("table");
            add(table.setOutputMarkupId(true));

            final DataView<ApplicationBean> listView = new DataView<ApplicationBean>("rows", provider, 10) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(Item<ApplicationBean> item) {
                    final ApplicationBean applicationBean = item.getModelObject();

                    item.add(new Label("name", applicationBean.getName()));
                    DocumentBean documentBean = uploadDocumentService.queryDocumentByAttach(applicationBean.getId()).get(0);

                    item.add(new Label("description", documentBean.getDescription()));

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
                            applicationBeanService.deleteEntity(applicationBean.getId());
                            target.add(table);
                        }
                    };
                    item.add(alink.setOutputMarkupId(true));
                }
            };

            CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
            };
            table.add(pagingNavigator);
            table.add(listView);
            add(createQuery(table, provider, tabPanel, wmc));
        }
    }

    class ApplicationDataProvider extends ListDataProvider<ApplicationBean> {
        private ApplicationBean userBean = null;

        public void setApplicationBean(ApplicationBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected List<ApplicationBean> getData() {
            if (userBean == null)
                return applicationBeanService.getAllEntity();
            else {
                return applicationBeanService.getAllEntity();
            }
        }
    }
}