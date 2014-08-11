package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IAboutBeanService;
import com.daren.admin.entities.AboutBean;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

import javax.inject.Inject;

/**
 * 项目名称:  urgent-project
 * 类描述:    系统版本页面类
 * 创建人:    sunlf
 * 创建时间:  2014/8/11 9:11
 * 修改人:    sunlf
 * 修改时间:  2014/8/11 9:11
 * 修改备注:  [说明本次修改内容]
 */
public class AboutPage extends AbstractDialog<AboutBean> {
    @Inject
    @Reference(id = "aboutBeanService", serviceInterface = IAboutBeanService.class)
    private IAboutBeanService aboutBeanService;

    private AboutBean aboutBean;

    public AboutPage(String id, String title) {
        super(id, title);
        aboutBean = aboutBeanService.getSysInfo();
        add(new Label("systemName"));//系统名称
        add(new Label("systemVersion"));//系统版本号
        add(new Label("userName"));//用户名称
    }

    @Override
    public void onClose(AjaxRequestTarget target, DialogButton button) {

    }
}
