package com.daren.hazard.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.hazard.entities.HazardBean;
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

public class HazardTabPage extends BasePanel {

    private final TabbedPanel tabPanel;

    final RepeatingView createPage = new RepeatingView("createPage");
    AjaxTab createAjaxTab;
    Fragment createFragment;
    HazardCreatePage hazardCreatePage;

    public HazardTabPage(String id, WebMarkupContainer wmc) {
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
        tabs.add(new AbstractTab(Model.of("重大危险源管理")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initMajorHazardSourcePage(id, wmc);
            }
        });
        return tabs;
    }

    private HazardPage initMajorHazardSourcePage(final String id, final WebMarkupContainer wmc) {
        HazardPage hazardPage = new HazardPage(id, wmc) {
            @Override
            protected void createButtonOnClick(HazardBean majorHazardSourceBean, AjaxRequestTarget target) {
                initMajorHazardSourceCreatePage(id,wmc,majorHazardSourceBean,target,true);
            }
        };
        return hazardPage;
    }

    private int getActiveTab(String title){
        for (int i = 0; i <tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initMajorHazardSourceCreatePage(final String id, final WebMarkupContainer wmc, final HazardBean hazardBean, AjaxRequestTarget target,boolean goPage) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of("重大危险源编辑")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    hazardCreatePage = new HazardCreatePage(createPage.newChildId(), wmc, hazardBean) {
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("重大危险源编辑") > 0){
                                tabPanel.getModelObject().remove(getActiveTab("重大危险源编辑"));
                                createAjaxTab = null;
                                createPage.removeAll();
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(hazardCreatePage);
                    createFragment = new Fragment(panelId, "panel-3", HazardTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            hazardCreatePage = new HazardCreatePage(createPage.newChildId(), wmc, hazardBean) {
                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("重大危险源编辑") > 0){
                        tabPanel.getModelObject().remove(getActiveTab("重大危险源编辑"));
                        createAjaxTab = null;
                        createPage.removeAll();
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(hazardCreatePage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if(goPage){
            tabPanel.setActiveTab(getActiveTab("重大危险源编辑"));
        }
    }
}
