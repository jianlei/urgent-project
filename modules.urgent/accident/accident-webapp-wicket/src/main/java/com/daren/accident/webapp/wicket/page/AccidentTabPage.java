package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
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
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentTabPage extends BasePanel {

    private final TabbedPanel tabPanel;

    public AccidentTabPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        //增加tabs支持
        tabPanel = new TabbedPanel("tabs", this.newTabList(id,wmc));
        this.add(tabPanel);
    }

    /**
     * 添加tabs
     *
     * @return
     */
    private List<ITab> newTabList(final String id,final WebMarkupContainer wmc) {
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

    private AccidentPage initAccidentPage(final String id,final WebMarkupContainer wmc){
        AccidentPage accidentPage = new AccidentPage(id,wmc){
            @Override
            protected void accidentTitleLinkOnClick(AccidentBean accidentBean,AjaxRequestTarget target) {
                initAccidentViewPage(id,wmc,accidentBean,target);
            }
        };
        return accidentPage;
    }

    private void initAccidentViewPage(final String id,final WebMarkupContainer wmc,final AccidentBean accidentBean,AjaxRequestTarget target){
        AjaxTab ajaxTab = new AjaxTab(Model.of("事故详细信息")) {
            private static final long serialVersionUID = 1L;
            @Override
            protected WebMarkupContainer getLazyPanel(String panelId) {
                final AccidentViewPage accidentViewPage = new AccidentViewPage(panelId,wmc,accidentBean){
                    @Override
                    protected void onEditOnClick(AccidentBean accidentBean, AjaxRequestTarget target) {
                        initAccidentEditPage(id,wmc,accidentBean,target);
                    }
                };
                return accidentViewPage;
            }
        };
        tabPanel.add(ajaxTab);
        target.add(tabPanel);
        tabPanel.setActiveTab(tabPanel.size());
    }
    private void initAccidentEditPage(final String id,final WebMarkupContainer wmc,final AccidentBean accidentBean,AjaxRequestTarget target){
        tabPanel.add(new AjaxTab(Model.of("事故编辑")) {
            private static final long serialVersionUID = 1L;
            @Override
            protected WebMarkupContainer getLazyPanel(String panelId) {
                final AccidentEditPage accidentEditPage = new AccidentEditPage(panelId,wmc,accidentBean){

                };
                return accidentEditPage;
            }
        });
        target.add(tabPanel);
        tabPanel.setActiveTab(tabPanel.size());
    }
}
