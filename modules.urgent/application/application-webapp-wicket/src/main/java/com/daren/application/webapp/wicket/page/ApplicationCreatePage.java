package com.daren.application.webapp.wicket.page;


import com.daren.application.api.biz.IApplicationBeanService;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

import javax.inject.Inject;

/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ApplicationCreatePage extends BasePanel {
    @Inject
    private IApplicationBeanService applicationBeanService;

    public ApplicationCreatePage(String id, WebMarkupContainer wmc,String applicationBeanId) {
        super(id,wmc);
        add(new Label("asd",applicationBeanId));
    }
}