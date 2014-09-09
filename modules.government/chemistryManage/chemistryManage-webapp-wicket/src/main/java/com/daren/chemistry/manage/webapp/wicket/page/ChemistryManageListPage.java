package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.admin.entities.UserBean;
import com.daren.government.webapp.wicket.model.AvailableProcessesModel;
import com.daren.government.webapp.wicket.model.ProcessDefinitionModel;
import com.daren.government.webapp.wicket.page.WorkflowBasePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：危险化学品列表页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageListPage extends WorkflowBasePanel {
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "新建流程";
    @Inject
    private transient FormService formService;
    private WebMarkupContainer wmc;

    public ChemistryManageListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc,CONST_LIST);

    }

    @Override
    public Fragment getMainFragment(String panelId, String makeupId) {
        return new MainFragment(panelId,makeupId);
    }

    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;
        public MainFragment(String id, String markupId) {
            super(id, markupId, ChemistryManageListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));

            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));
            final IModel<List<ProcessDefinition>> listOfProcesses=new AvailableProcessesModel();
            //add listview
            final ListView<ProcessDefinition> listView = new ListView<ProcessDefinition>("rows", listOfProcesses) {
                private static final long serialVersionUID = 1L;
                @Override
                protected void populateItem(ListItem<ProcessDefinition> item) {
                    final ProcessDefinition row = item.getModelObject();
                    item.add(new Label("id", row.getId()));
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("description", row.getDescription()));
                    item.add(new Label("version", row.getVersion()));
                    item.add(new Label("suspended", row.isSuspended()));
                    item.add(initStartButton(row));
                }
            };
            table.add(listView);


            //search form
            Form<UserBean> userForm = new Form<>("form", new CompoundPropertyModel<>(new UserBean()));
            TextField textField = new TextField("name");
            userForm.add(textField.setOutputMarkupId(true));

//            userForm.add(initStartButton());
            add(userForm);

        }

        /**
         * 创建新的页面，用于新增和修改
         *
         * @param target
         * @param tabTitle "修改字典"||"新增字典"
         * @param model        数据
         */
        private void createNewTab(AjaxRequestTarget target, final String tabTitle, final IFormHandler formHandler, final IModel model) {
            //去掉第二个tab
            if (tabPanel.getModelObject().size() == 2) {
                tabPanel.getModelObject().remove(1);
            }
            tabPanel.add(new AjaxTab(Model.of(tabTitle)) {
                private static final long serialVersionUID = 1L;

                @Override
                public WebMarkupContainer getLazyPanel(String panelId) {
                    //通过repeatingView增加新的panel
                    repeatingView.removeAll();
                    repeatingView.add(formHandler.getPanel(repeatingView.newChildId(),model ) );
                    Fragment fragment = new Fragment(panelId, "addPanel", ProcessListPage.this);
                    fragment.add(repeatingView);
                    return fragment;
                }
            });

            tabPanel.setActiveTab(1);
            target.add(tabPanel);
        }
    }
}
