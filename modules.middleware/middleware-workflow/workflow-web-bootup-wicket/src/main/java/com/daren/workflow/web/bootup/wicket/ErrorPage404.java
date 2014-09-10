package com.daren.workflow.web.bootup.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by dell on 14-3-28.
 */
public class ErrorPage404 extends WebPage {
    public ErrorPage404() {
    }

    public ErrorPage404(final PageParameters parameters) {

        add(new Label("404", "Page Not Found!"));

    }
}
