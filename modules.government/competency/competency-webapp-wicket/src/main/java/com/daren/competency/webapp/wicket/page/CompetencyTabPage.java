package com.daren.competency.webapp.wicket.page;

import com.daren.competency.entities.CompetencyBean;
import com.daren.core.web.wicket.BasePanel;
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
 * @类描述：安全资格证书(培训)
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetencyTabPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    private final TabbedPanel tabPanel;
    AjaxTab createAjaxTab;
    Fragment createFragment;
    CompetencyAddPage competencyAddPage;

    public CompetencyTabPage(String id, WebMarkupContainer wmc, String phone) {
        super(id, wmc);
        //增加tabs支持
        tabPanel = new TabbedPanel("tabs", this.newTabList(id, wmc, phone));
        this.add(tabPanel);
        createPage.setOutputMarkupId(true);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final String id, final WebMarkupContainer wmc, final String phone) {
        List<ITab> tabs = new ArrayList();
        tabs.add(new AbstractTab(Model.of("安全资格证书（培训）")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initCompetencyListPage(id, wmc, phone);
            }
        });
        return tabs;
    }

    private CompetencyListPage initCompetencyListPage(final String id, final WebMarkupContainer wmc, String phone) {
        CompetencyListPage hazardPage = new CompetencyListPage(id, wmc, phone) {
            @Override
            protected void createButtonOnClick(CompetencyBean competencyBean, AjaxRequestTarget target) {
                initCompetencyCreatePage(id, wmc, competencyBean, target, true, "编辑流程");
            }
        };
        return hazardPage;
    }

    private int getActiveTab(String title) {
        for (int i = 0; i < tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initCompetencyCreatePage(final String id, final WebMarkupContainer wmc, final CompetencyBean competencyBean, AjaxRequestTarget target, boolean goPage, final String pageName) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of(pageName)) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    competencyAddPage = new CompetencyAddPage(createPage.newChildId(), wmc, competencyBean) {
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab(pageName) > 0) {
                                tabPanel.getModelObject().remove(getActiveTab(pageName));
                                createAjaxTab = null;
                                createPage.removeAll();
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(competencyAddPage);
                    createFragment = new Fragment(panelId, "panel-3", CompetencyTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            competencyAddPage = new CompetencyAddPage(createPage.newChildId(), wmc, competencyBean) {
                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab(pageName) > 0) {
                        tabPanel.getModelObject().remove(getActiveTab(pageName));
                        createAjaxTab = null;
                        createPage.removeAll();
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(competencyAddPage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if (goPage) {
            tabPanel.setActiveTab(getActiveTab(pageName));
        }
    }
}
