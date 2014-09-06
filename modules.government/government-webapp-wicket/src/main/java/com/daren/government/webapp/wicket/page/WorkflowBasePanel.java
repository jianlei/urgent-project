package com.daren.government.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：工作流抽象类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public abstract  class WorkflowBasePanel extends BasePanel {
    protected final TabbedPanel tabPanel;
    protected final RepeatingView repeatingView = new RepeatingView("repeatingView");
    public WorkflowBasePanel(String id, WebMarkupContainer wmc,String tabTitle) {
        super(id, wmc);
        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(tabTitle), options);
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    protected List<? extends ITab> newTabList(String tabTitle) {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of(tabTitle)) {
            private static final long serialVersionUID = 1L;
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return getMainFragment(panelId, "mainPanel");
            }
        });
        return tabs;
    }

    public abstract Fragment getMainFragment(String panelId,String makeupId);
}
