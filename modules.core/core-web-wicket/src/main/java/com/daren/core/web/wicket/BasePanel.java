package com.daren.core.web.wicket;

import com.daren.admin.api.biz.IDictBeanService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import javax.inject.Inject;

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
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    protected IDictBeanService dictBeanService;
    //ajax target container
    protected WebMarkupContainer webMarkupContainer;

    public BasePanel(String id, WebMarkupContainer wmc) {
        super(id);
        this.webMarkupContainer = wmc;
//        webMarkupContainer.add(new DebugBar("debug"));
    }
}
