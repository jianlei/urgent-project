package com.daren.example.webapp.wicket.page;

import com.daren.core.web.component.jgrowl.JGrowlFeedbackPanel;
import com.daren.core.web.component.jgrowl.Options;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * 项目名称:  urgent-project
 * 类描述:    测试jgrowl
 * 创建人:   dlw
 * 创建时间:  2014/9/15 17:21
 */
public class JGrowl extends BasePanel {

    public JGrowl(String id, WebMarkupContainer wmc) {
        super(id, wmc);
//        add(new Label("tabs",""));
        //调用OnDomReadyHeader方法
/*        this.add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                response.render(OnDomReadyHeaderItem.forScript("loadScript();"));
            }
        });*/

        final JGrowlFeedbackPanel feedback = new JGrowlFeedbackPanel("jgrowlFeedback");
        add(feedback);

        final Options errorOptions = new Options();
        errorOptions.set("header", "Error");
        errorOptions.set("theme", "error");
        errorOptions.set("glue", "before");
        errorOptions.set("position", "center");
        feedback.setErrorMessageOptions(errorOptions);

        final Options infoOptions = new Options();
        infoOptions.set("header", "Info");
        infoOptions.set("theme", "info");
        infoOptions.set("glue", "after");
        infoOptions.set("position", "center");
        infoOptions.set("closer", "false");
        feedback.setInfoMessageOptions(infoOptions);

        final AjaxLink<Void> link = new AjaxLink<Void>("showButton")
        {

            @Override
            public void onClick(final AjaxRequestTarget target)
            {

                error("An ERROR message");

                info("An INFO message");

                target.add(feedback);
            }
        };

        add(link);

    }
}
