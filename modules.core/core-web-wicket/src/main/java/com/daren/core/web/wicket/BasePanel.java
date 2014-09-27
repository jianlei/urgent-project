package com.daren.core.web.wicket;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.core.api.IConst;
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
    protected boolean isEnt=false;//判断是否为企业用户

    public BasePanel(String id, WebMarkupContainer wmc) {
        super(id);
        this.webMarkupContainer = wmc;
        isEntUser();
//        webMarkupContainer.add(new DebugBar("debug"));
    }

    /**
     * 判断是否为企业登陆用户
     */
    private void isEntUser() {
        isEnt=(getApplication().getName().equals(IConst.COMPANY_WICKET_APPLICATION_NAME))?true:false;
    }
}
