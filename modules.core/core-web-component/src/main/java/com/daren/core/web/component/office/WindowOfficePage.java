package com.daren.core.web.component.office;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;


public class WindowOfficePage extends OfficeDialog {
    WebMarkupContainer wmc = new WebMarkupContainer("allmapwmc");
    RepeatingView allmap = new RepeatingView("allmap");

    public WindowOfficePage(String id, String title, final String filePath) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        allmap.add(new OfficePage(allmap.newChildId()));

        //调用OnDomReadyHeader方法
        this.add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                response.render(OnDomReadyHeaderItem.forScript("loadScript('" + filePath + "');"));
            }
        });
    }

    @Override
    public void onClose(AjaxRequestTarget target, DialogButton button) {
        allmap.removeAll();
        target.add(wmc);
    }

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("width", 800);
        behavior.setOption("height", 650);
    }
}