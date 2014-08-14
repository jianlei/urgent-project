package com.daren.accident.webapp.wicket.page;

import com.daren.accident.entities.AccidentBean;
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
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentTabPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    final RepeatingView viewPage = new RepeatingView("viewPage");
    private final TabbedPanel tabPanel;
    AjaxTab viewAjaxTab;
    AjaxTab createAjaxTab;
    Fragment viewFragment;
    Fragment createFragment;
    AccidentViewPage accidentViewPage;
    AccidentEditPage accidentEditPage;

    public AccidentTabPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        //增加tabs支持
        tabPanel = new TabbedPanel("tabs", this.newTabList(id, wmc));
        this.add(tabPanel);
        createPage.setOutputMarkupId(true);
        viewPage.setOutputMarkupId(true);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final String id, final WebMarkupContainer wmc) {
        List<ITab> tabs = new ArrayList();
        tabs.add(new AbstractTab(Model.of("事故管理")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initAccidentPage(id, wmc);
            }
        });
        return tabs;
    }

    private AccidentPage initAccidentPage(final String id, final WebMarkupContainer wmc) {
        AccidentPage accidentPage = new AccidentPage(id, wmc) {
            @Override
            protected void accidentTitleLinkOnClick(AccidentBean accidentBean, AjaxRequestTarget target) {
                initAccidentViewPage(id, wmc, accidentBean, target, true);
            }

            @Override

            protected void addButtonOnClick(AccidentBean accidentBean, AjaxRequestTarget target) {
                initAccidentEditPage(id, wmc, accidentBean, target, true);
            }
        };
        return accidentPage;
    }

    private void initAccidentViewPage(final String id, final WebMarkupContainer wmc, final AccidentBean accidentBean, AjaxRequestTarget target, boolean goPage) {
        if (null == viewAjaxTab) {
            viewAjaxTab = new AjaxTab(Model.of("事故详细信息")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    accidentViewPage = new AccidentViewPage(viewPage.newChildId(), wmc, accidentBean) {
                        @Override
                        protected void onEditOnClick(AccidentBean bean, AjaxRequestTarget target) {
                            initAccidentEditPage(createPage.newChildId(), wmc, bean, target, true);
                        }

                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("事故详细信息") > 0) {
                                tabPanel.getModelObject().remove(getActiveTab("事故详细信息"));
                                viewAjaxTab = null;
                            }
                            target.add(tabPanel);
                        }
                    };
                    viewPage.add(accidentViewPage);
                    viewFragment = new Fragment(panelId, "panel-2", AccidentTabPage.this);
                    viewFragment.add(viewPage);
                    return viewFragment;
                }
            };
            tabPanel.add(viewAjaxTab);
        } else {
            accidentViewPage = new AccidentViewPage(viewPage.newChildId(), wmc, accidentBean) {
                @Override
                protected void onEditOnClick(AccidentBean bean, AjaxRequestTarget target) {
                    initAccidentEditPage(createPage.newChildId(), wmc, bean, target, true);
                }

                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("事故详细信息") > 0) {
                        tabPanel.getModelObject().remove(getActiveTab("事故详细信息"));
                        viewAjaxTab = null;
                    }
                    target.add(tabPanel);
                }
            };
            viewPage.removeAll();
            viewPage.add(accidentViewPage);
            viewFragment.removeAll();
            viewFragment.add(viewPage);
            target.add(viewFragment);
        }
        target.add(tabPanel);
        if (goPage) {
            tabPanel.setActiveTab(getActiveTab("事故详细信息"));
        }
    }

    private int getActiveTab(String title) {
        for (int i = 0; i < tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initAccidentEditPage(final String id, final WebMarkupContainer wmc, final AccidentBean accidentBean, AjaxRequestTarget target, boolean goPage) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of("事故编辑")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    accidentEditPage = new AccidentEditPage(createPage.newChildId(), wmc, accidentBean) {
                        @Override
                        public void onSaveOnclick(AccidentBean bean, AjaxRequestTarget target) {
                            initAccidentViewPage(id, wmc, bean, target, false);
                        }

                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("事故编辑") > 0) {
                                tabPanel.getModelObject().remove(getActiveTab("事故编辑"));
                                createAjaxTab = null;
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(accidentEditPage);
                    createFragment = new Fragment(panelId, "panel-3", AccidentTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            accidentEditPage = new AccidentEditPage(createPage.newChildId(), wmc, accidentBean) {
                @Override
                public void onSaveOnclick(AccidentBean bean, AjaxRequestTarget target) {
                    initAccidentViewPage(id, wmc, bean, target, false);
                }

                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("事故编辑") > 0) {
                        tabPanel.getModelObject().remove(getActiveTab("事故编辑"));
                        createAjaxTab = null;
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(accidentEditPage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if (goPage) {
            tabPanel.setActiveTab(getActiveTab("事故编辑"));
        }
    }
}
