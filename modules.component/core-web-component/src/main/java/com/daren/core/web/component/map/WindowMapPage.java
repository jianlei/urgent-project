package com.daren.core.web.component.map;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;


/**
 * @类描述：共公弹框-地图标注
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class WindowMapPage extends MapDialog {
    WebMarkupContainer wmc = new WebMarkupContainer("allmapwmc");
    RepeatingView allmap = new RepeatingView("allmap");

    public WindowMapPage(String id, String title) {
        super(id, title);
        allmap.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);
        wmc.add(allmap);
        this.add(wmc);
        allmap.add(new MyMap(allmap.newChildId()));

        //调用OnDomReadyHeader方法
        this.add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                response.render(OnDomReadyHeaderItem.forScript("loadScript();"));
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