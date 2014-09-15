package com.daren.attachment.webapp.wicket.page;


import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * Created by Administrator on 2014/9/12.
 */
public class WindowGovernmentPage extends GovernmentDialog {
    private String pageType;
    WebMarkupContainer wmc = new WebMarkupContainer("allmapwmc");
    RepeatingView allmap = new RepeatingView("allmap");
    public WindowGovernmentPage(String id, String title, long preateId, String type, String appType) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        pageType = type;
        if(type.equals("list")){
            allmap.add(new ListPage(allmap.newChildId(), preateId, appType));
        }else{
            allmap.add(new UploadPage(allmap.newChildId(), preateId, appType));
        }

    }

    protected void myOnClose(AjaxRequestTarget target) {}

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        if(pageType.equals("list")){
            behavior.setOption("width", 800);
            behavior.setOption("height", 650);
        }else{
            behavior.setOption("width", 400);
            behavior.setOption("height", 300);
        }
    }
}
