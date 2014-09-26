package com.daren.danger.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.danger.entities.DangerBean;
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
 * @类描述：危化品tab页
 * @创建人：xukexin
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DangerTabPage extends BasePanel {

    final RepeatingView createPage = new RepeatingView("createPage");
    private final TabbedPanel tabPanel;
    AjaxTab createAjaxTab;
    Fragment createFragment;
    DangerAddPage dangerCreatePage;

    public DangerTabPage(String id, WebMarkupContainer wmc) {
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
        tabs.add(new AbstractTab(Model.of("危化品管理")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initDangerPage(id, wmc);
            }
        });
        return tabs;
    }

    private DangerListPage initDangerPage(final String id, final WebMarkupContainer wmc) {
        DangerListPage dangerPage = new DangerListPage(id, wmc) {
            @Override
            protected void createButtonOnClick(DangerBean dangerBean, AjaxRequestTarget target) {
                initDangerCreatePage(id, wmc, dangerBean, target, true);
            }
        };
        return dangerPage;
    }

    private int getActiveTab(String title){
        for (int i = 0; i <tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initDangerCreatePage(final String id, final WebMarkupContainer wmc, final DangerBean dangerBean, AjaxRequestTarget target, boolean goPage) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of("危化品编辑")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    dangerCreatePage = new DangerAddPage(createPage.newChildId(), wmc, dangerBean) {
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("危化品编辑") > 0){
                                tabPanel.getModelObject().remove(getActiveTab("危化品编辑"));
                                createAjaxTab = null;
                                createPage.removeAll();
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(dangerCreatePage);
                    createFragment = new Fragment(panelId, "panel-3", DangerTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            dangerCreatePage = new DangerAddPage(createPage.newChildId(), wmc, dangerBean) {
                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("危化品编辑") > 0){
                        tabPanel.getModelObject().remove(getActiveTab("危化品编辑"));
                        createAjaxTab = null;
                        createPage.removeAll();
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(dangerCreatePage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if(goPage){
            tabPanel.setActiveTab(getActiveTab("危化品编辑"));
        }
    }
}
