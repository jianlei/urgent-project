package com.daren.core.web.wicket.navigator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Created by Dell on 14-7-29.
 */
public class DarenNavigator extends PagingNavigator {

    private static final long serialVersionUID = 1;

    public DarenNavigator(String arg0, final IPageable arg1, final Integer total) {
        super(arg0, arg1);
        Link totallink = new Link("totallink") {
            private static final long serialVersionUID = 1;

            @Override
            public boolean isEnabled() {
                return arg1.getPageCount() - 1 > 0;
            }

            @Override
            public void onClick() {
                arg1.setCurrentPage(arg1.getPageCount() - 1);
            }
        };
        totallink.add(new Label("totalcount", new LoadableDetachableModel() {
            private static final long serialVersionUID = 1;

            @Override
            protected Object load() {
                return String.valueOf(arg1.getPageCount());
            }
        }));
        this.add(totallink);
        this.add(new Label("total", total.toString()));
    }

    public DarenNavigator(String arg0, final IPageable arg1,
                     IPagingLabelProvider arg2, final Integer total) {
        super(arg0, arg1, arg2);
        Link totallink = new Link("totallink") {
            private static final long serialVersionUID = 1;

            @Override
            public boolean isEnabled() {
                return arg1.getPageCount() - 1 > 0;
            }

            @Override
            public void onClick() {
                arg1.setCurrentPage(arg1.getPageCount() - 1);
            }
        };
        totallink.add(new Label("totalcount", new LoadableDetachableModel() {
            private static final long serialVersionUID = 1;

            @Override
            protected Object load() {
                return String.valueOf(arg1.getPageCount());
            }
        }));
        this.add(totallink);
        this.add(new Label("total", total.toString()));
    }

    protected PagingNavigation newNavigation(IPageable arg0, IPagingLabelProvider arg1) {
        return new PagingSetting("navigation", arg0, arg1, this);
    }

}