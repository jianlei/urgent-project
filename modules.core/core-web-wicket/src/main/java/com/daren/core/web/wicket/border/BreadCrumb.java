package com.daren.core.web.wicket.border;

import com.daren.core.web.wicket.BasePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：头部导航
 * @创建人：wangkr
 * @创建时间：2014-04-09 下午3:20
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class BreadCrumb extends Panel {
    public BreadCrumb(String id, final Class cls,final boolean isLast) {
        super(id);

        Label iconImg = new Label("iconImg"){
            @Override
            public boolean isVisible() {
                return isLast;
            }
        };
        iconImg.add(new AttributeModifier("class", "icon-home"));
        add(iconImg);

        Link goPage = new Link("goPage") {
            @Override
            public void onClick() {
                setResponsePage(cls);
            }
        };
        add(goPage);

        Label iconAngleRight = new Label("iconAngleRight"){
            @Override
            public boolean isVisible() {
                return isLast;
            }
        };
        iconAngleRight.add(new AttributeModifier("class", "icon-angle-right"));
        add(iconAngleRight);
    }
}


