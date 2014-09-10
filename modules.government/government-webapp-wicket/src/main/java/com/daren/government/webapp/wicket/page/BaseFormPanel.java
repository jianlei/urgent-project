package com.daren.government.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @类描述：form panel的基础类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public abstract class BaseFormPanel extends Panel {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected String currentUserName;//当前登陆用户
    @Inject
    IUserBeanService userBeanService;
    public BaseFormPanel(String id, IModel<?> model) {
        super(id, model);
        currentUserName=userBeanService.getCurrentUserName();
    }
}
