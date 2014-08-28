package com.daren.enterprise.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.entities.OrganizationBean;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：监管机构
 * @创建人：xukexin
 * @创建时间：2014/8/26 14:55
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrganizationTabPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    private final TabbedPanel tabPanel;
    AjaxTab createAjaxTab;
    Fragment createFragment;
    OrganizationAddPage organizationCreatePage;

    public OrganizationTabPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        //增加tabs支持
        tabPanel = new TabbedPanel("tabs", this.newTabList(id, wmc));
        this.add(tabPanel);
        createPage.setOutputMarkupId(true);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final String id, final WebMarkupContainer wmc) {
        List<ITab> tabs = new ArrayList();
        tabs.add(new AbstractTab(Model.of("监管机构管理")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initOrganizationPage(id, wmc);
            }
        });
        return tabs;
    }

    private OrganizationListPage initOrganizationPage(final String id, final WebMarkupContainer wmc) {
        OrganizationListPage organizationListPage = new OrganizationListPage(id, wmc) {
            @Override
            protected void createButtonOnClick(OrganizationBean organizationBean, AjaxRequestTarget target) {
                initOrganizationCreatePage(id,wmc,organizationBean,target,true);
            }
        };
        return organizationListPage;
    }

    private int getActiveTab(String title){
        for (int i = 0; i <tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initOrganizationCreatePage(final String id, final WebMarkupContainer wmc, final OrganizationBean organizationBean, AjaxRequestTarget target,boolean goPage) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of("监管机构编辑")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    organizationCreatePage = new OrganizationAddPage(createPage.newChildId(), wmc, organizationBean) {
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("监管机构编辑") > 0){
                                tabPanel.getModelObject().remove(getActiveTab("监管机构编辑"));
                                createAjaxTab = null;
                                createPage.removeAll();
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(organizationCreatePage);
                    createFragment = new Fragment(panelId, "panel-3", OrganizationTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            organizationCreatePage = new OrganizationAddPage(createPage.newChildId(), wmc, organizationBean) {
                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("监管机构编辑") > 0){
                        tabPanel.getModelObject().remove(getActiveTab("监管机构编辑"));
                        createAjaxTab = null;
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(organizationCreatePage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if(goPage){
            tabPanel.setActiveTab(getActiveTab("监管机构编辑"));
        }
    }
}
