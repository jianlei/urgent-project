package com.daren.fireworks.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.fireworks.entities.FireworksBean;
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
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksTabPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    private final TabbedPanel tabPanel;
    AjaxTab createAjaxTab;
    Fragment createFragment;
    FireworksAddPage fireworksAddPage;

    public FireworksTabPage(String id, WebMarkupContainer wmc) {
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
        tabs.add(new AbstractTab(Model.of("烟花爆竹经营许可证")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initFireworksListPage(id, wmc);
            }
        });
        return tabs;
    }

    private FireworksListPage initFireworksListPage(final String id, final WebMarkupContainer wmc) {
        FireworksListPage hazardPage = new FireworksListPage(id, wmc) {
            @Override
            protected void createButtonOnClick(FireworksBean competencyBean, AjaxRequestTarget target) {
                initFireworksCreatePage(id, wmc, competencyBean, target, true, "编辑流程");
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

    private void initFireworksCreatePage(final String id, final WebMarkupContainer wmc, final FireworksBean fireworksBean, AjaxRequestTarget target, boolean goPage, final String pageName) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of(pageName)) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    fireworksAddPage = new FireworksAddPage(createPage.newChildId(), wmc, fireworksBean) {
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
                    createPage.add(fireworksAddPage);
                    createFragment = new Fragment(panelId, "panel-3", FireworksTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            fireworksAddPage = new FireworksAddPage(createPage.newChildId(), wmc, fireworksBean) {
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
            createPage.add(fireworksAddPage);
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
