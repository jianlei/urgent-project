package com.daren.example.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @类描述：测试jScript和wicket交互的例子
 * @创建人： sunlingfeng
 * @创建时间：2014/8/28
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class JScriptTest extends BasePanel{
    String AJAX_PARAM1_NAME="pan";
    String AJAX_PARAM2_NAME="da";
    public JScriptTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        final JQueryFeedbackPanel feedbackPanel=new JQueryFeedbackPanel("feedback");
        add(feedbackPanel);

        AbstractDefaultAjaxBehavior ajaxBehavior = new AbstractDefaultAjaxBehavior() {

            @Override
            protected void respond(AjaxRequestTarget target) {
                String param1Value = getRequest().getRequestParameters().getParameterValue(AJAX_PARAM1_NAME).toString();
                String param2Value = getRequest().getRequestParameters().getParameterValue(AJAX_PARAM2_NAME).toString();
                System.out.println("Param 1:" + param1Value + "Param 2:" + param2Value);

                feedbackPanel.info("first name="+param1Value);
                feedbackPanel.info("second name="+param2Value);
                target.add(feedbackPanel);
            }

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);
                String callBackScript = getCallbackFunction(CallbackParameter.explicit(AJAX_PARAM1_NAME), CallbackParameter.explicit(AJAX_PARAM2_NAME)).toString();
                callBackScript = "sendToServer="+callBackScript+";";
                response.render(OnDomReadyHeaderItem.forScript(callBackScript));
            }

        };

        add(ajaxBehavior);
    }
}
