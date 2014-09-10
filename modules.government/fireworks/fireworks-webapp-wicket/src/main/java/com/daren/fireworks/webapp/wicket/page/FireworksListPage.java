package com.daren.fireworks.webapp.wicket.page;

import com.daren.government.webapp.wicket.page.WorkflowBasePanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * Created by Administrator on 2014/9/9.
 */
public class FireworksListPage extends WorkflowBasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "新建流程";
    private WebMarkupContainer wmc;

    public FireworksListPage(String id, WebMarkupContainer wmc, String tabTitle) {
        super(id, wmc, CONST_LIST);
    }

    @Override
    public Fragment getMainFragment(String panelId, String makeupId) {
        return new MainFragment(panelId, makeupId);
    }

    public class MainFragment extends Fragment {
        public MainFragment(String id, String markupId) {
            super(id, markupId, FireworksListPage.this);

        }
    }
}
