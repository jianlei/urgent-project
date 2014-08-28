package com.daren.example.webapp.wicket.page;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;


public class WindowOfficePage extends OfficeDialog {
    WebMarkupContainer wmc = new WebMarkupContainer("allmapwmc");
    RepeatingView allmap = new RepeatingView("allmap");

    public WindowOfficePage(String id, String title) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        allmap.add(new IframeOfficePage(allmap.newChildId()));
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