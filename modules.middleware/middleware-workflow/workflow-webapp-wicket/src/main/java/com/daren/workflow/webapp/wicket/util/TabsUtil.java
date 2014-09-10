package com.daren.workflow.webapp.wicket.util;

import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @类描述：tab的工具类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/7
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class TabsUtil {
    /**
     * 关闭子tabs
     * @param target
     * @param tabPanel
     */
    public static void deleteTab(AjaxRequestTarget target,TabbedPanel tabPanel){
        if (tabPanel.getModelObject().size() == 2)
            tabPanel.getModelObject().remove(1);
        target.add(tabPanel);
    }
}
