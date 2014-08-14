package com.daren.equipment.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.equipment.entities.EquipmentBean;
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

public class EquipmentTabPage extends BasePanel {

    private final TabbedPanel tabPanel;

    final RepeatingView createPage = new RepeatingView("createPage");
    AjaxTab createAjaxTab;
    Fragment createFragment;
    EquipmentCreatePage majorHazardSourceCreatePage;

    public EquipmentTabPage(String id, WebMarkupContainer wmc) {
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
        tabs.add(new AbstractTab(Model.of("物资装备管理")) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return initEquipmentPage(id, wmc);
            }
        });
        return tabs;
    }

    private EquipmentPage initEquipmentPage(final String id, final WebMarkupContainer wmc) {
        EquipmentPage equipmentPage = new EquipmentPage(id, wmc) {
            @Override
            protected void createButtonOnClick(EquipmentBean equipmentBean, AjaxRequestTarget target) {
                initEquipmentCreatePage(id,wmc,equipmentBean,target,true);
            }
        };
        return equipmentPage;
    }

    private int getActiveTab(String title){
        for (int i = 0; i <tabPanel.getModelObject().size(); i++) {
            if (tabPanel.getModelObject().get(i).getTitle().getObject().equals(title)) {
                return i;
            }
        }
        return 0;
    }

    private void initEquipmentCreatePage(final String id, final WebMarkupContainer wmc, final EquipmentBean equipmentBean, AjaxRequestTarget target,boolean goPage) {
        if (null == createAjaxTab) {
            createAjaxTab = new AjaxTab(Model.of("物资装备编辑")) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    majorHazardSourceCreatePage = new EquipmentCreatePage(createPage.newChildId(), wmc, equipmentBean) {
                        @Override
                        protected void onDeleteTabs(AjaxRequestTarget target) {
                            if (getActiveTab("物资装备编辑") > 0){
                                tabPanel.getModelObject().remove(getActiveTab("物资装备编辑"));
                                createAjaxTab = null;
                                createPage.removeAll();
                            }
                            target.add(tabPanel);
                        }
                    };
                    createPage.add(majorHazardSourceCreatePage);
                    createFragment = new Fragment(panelId, "panel-3", EquipmentTabPage.this);
                    createFragment.add(createPage);
                    return createFragment;
                }
            };
            tabPanel.add(createAjaxTab);
        } else {
            majorHazardSourceCreatePage = new EquipmentCreatePage(createPage.newChildId(), wmc, equipmentBean) {
                @Override
                protected void onDeleteTabs(AjaxRequestTarget target) {
                    if (getActiveTab("物资装备编辑") > 0){
                        tabPanel.getModelObject().remove(getActiveTab("物资装备编辑"));
                        createAjaxTab = null;
                    }
                    target.add(tabPanel);
                }
            };
            createPage.removeAll();
            createPage.add(majorHazardSourceCreatePage);
            createFragment.removeAll();
            createFragment.add(createPage);
            target.add(createFragment);
        }
        target.add(tabPanel);
        if(goPage){
            tabPanel.setActiveTab(getActiveTab("物资装备编辑"));
        }
    }
}
