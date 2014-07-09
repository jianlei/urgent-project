package com.daren.admin.webapp.wicket.page;

import com.daren.core.web.wicket.PermissionConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.markup.html.WebPage;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 20:36
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 20:36
 * 修改备注:  [说明本次修改内容]
 */
public class Page1 extends WebPage {
    public Page1() {
        Session session = SecurityUtils.getSubject().getSession();
        Object o = session.getAttribute(PermissionConstant.SYS_CURRENT_USER);
    }
}
