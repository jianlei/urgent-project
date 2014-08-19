package com.daren.core.web.wicket;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/23 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */

public abstract class BasePanel extends Panel {
    public final Logger log = Logger.getLogger(getClass());
    //ajax target container
    protected WebMarkupContainer webMarkupContainer;
    protected String tabTitle;//tab的标题
    protected TabbedPanel tabPanel;

    public BasePanel(String id, WebMarkupContainer wmc) {
        super(id);
        this.webMarkupContainer = wmc;



    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Options options = new Options();
        tabPanel = new TabbedPanel("tabs", this.newTabList(), options);
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<? extends ITab> newTabList() {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #1 //
        tabs.add(new AbstractTab(Model.of(tabTitle)) {
            private static final long serialVersionUID = 1L;
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return createMainFragment(panelId, "mainPanel");
            }
        });

        return tabs;
    }

    /**
     * 抽象类，由具体类来实现
     * @param id
     * @param markupId
     * @return
     */
    public Fragment createMainFragment(String id, String markupId){
     return null;
    }
}
