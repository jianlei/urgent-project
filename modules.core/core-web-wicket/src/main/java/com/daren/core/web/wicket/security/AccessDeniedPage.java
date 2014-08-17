package com.daren.core.web.wicket.security;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class AccessDeniedPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public AccessDeniedPage() {
        add(new JQueryFeedbackPanel("feedback"));
        add(new BookmarkablePageLink<Void>("loginLink", SignInPage.class));
    }
}
