package com.daren.core.web.component.navigator;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;

/**
 * Created by Dell on 14-7-29.
 */
public class PagingSetting extends PagingNavigation {

    private static final long serialVersionUID = 1;

    @SuppressWarnings("unused")
    private IPageable arg1 = null;

    private DarenNavigator style = null;

    public PagingSetting(String arg0, IPageable arg1, IPagingLabelProvider arg2,
                         DarenNavigator style) {
        super(arg0, arg1, arg2);
        this.arg1 = arg1;
        this.style = style;
        this.setViewSize(5);
    }

    @Override
    protected void onModelChanged() {
        super.onModelChanged();
        style.get("first").setVisible(true);
        style.get("prev").setVisible(true);
        style.get("next").setVisible(true);
        style.get("last").setVisible(true);
        if (arg1.getCurrentPage() < 2) {
            style.get("first").setVisible(false);
        }
        if (arg1.getCurrentPage() < 1) {
            style.get("prev").setVisible(false);
        }
        if (arg1.getCurrentPage() > arg1.getPageCount() - 2) {
            style.get("next").setVisible(false);
        }
        if (arg1.getCurrentPage() > arg1.getPageCount() - 3) {
            style.get("last").setVisible(false);
        }
    }
}