package com.daren.enterprise.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @类描述：监管机构
 * @创建人：xukexin
 * @创建时间：2014/8/26 14:55
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ThirdListPage extends BasePanel {

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    public ThirdListPage(final String id, final WebMarkupContainer wmc){
        super(id, wmc);
        feedbackPanel.error("未发现任何第三方接口！");
        this.add(feedbackPanel);
    }


}
