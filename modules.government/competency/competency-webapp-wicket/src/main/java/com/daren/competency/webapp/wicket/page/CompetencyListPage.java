package com.daren.competency.webapp.wicket.page;

import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.entities.CompetencyBean;
import com.daren.competency.webapp.wicket.Const;
import com.daren.core.util.DateUtil;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：安全资格证书(培训)
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetencyListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    CompetencyDataProvider provider = new CompetencyDataProvider();
    @Inject
    private ICompetencyService competencyService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private TaskService taskService;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    public CompetencyListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        //初始化dialogWrapper
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog", ""));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
        this.add(feedbackPanel);
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        DataView<CompetencyBean> listView = new DataView<CompetencyBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<CompetencyBean> item) {
                final CompetencyBean competencyBean = item.getModelObject();
                item.add(new Label("name", competencyBean.getName()));
                item.add(new Label("sex", competencyBean.getSex()));
                item.add(new Label("phone", competencyBean.getPhone()));
                item.add(new Label("enterpriseName", competencyBean.getEnterpriseName()));
                item.add(new Label("title", competencyBean.getTitle()));
                item.add(new Label("cultureLevel", competencyBean.getCultureLevel()));
                item.add(new Label("id_code", competencyBean.getId_code()));
                item.add(new Label("unitType", competencyBean.getUnitType()));
                item.add(new Label("qualificationsType", competencyBean.getQualificationsType()));
                item.add(new Label("linkHandle", competencyBean.getLinkHandle()));
                item.add(getToCreatePageLink("check_name", competencyBean));
                item.add(getToUploadPageLink("upload", competencyBean));
                item.add(getDuplicateLink("duplicate", competencyBean));
                item.add(getSubmitLink("submit", competencyBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final CompetencyBean competencyBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final CompetencyBean competencyBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(competencyBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final CompetencyBean competencyBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", competencyBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getSubmitLink(String wicketId, final CompetencyBean competencyBean){
        AjaxLink alinkSubmit = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String bizKey= Const.PROCESS_KEY + ":" + competencyBean.getPhone() + ":" + competencyBean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(competencyBean.getPhone());
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(Const.PROCESS_KEY, bizKey);
                    competencyBean.setProcessInstanceId(instance.getProcessInstanceId());
                    Task task=taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
                    competencyBean.setLinkHandle(task.getName());
                    competencyService.saveEntity(competencyBean);
                    feedbackPanel.info("安全资格证书(培训),启动成功！");
                }catch (Exception e){
                    feedbackPanel.info("安全资格证书(培训),启动失败！");
                }finally {
                    identityService.setAuthenticatedUserId(null);
                }
                target.add(feedbackPanel);
            }

        };
        return alinkSubmit;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final CompetencyBean competencyBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", competencyBean, "upload");
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(CompetencyBean competencyBean, AjaxRequestTarget target) {
    }

    private void createDialog(AjaxRequestTarget target, final String title, CompetencyBean competencyBean, String type) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", title, competencyBean.getId(), type, "competency") {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final CompetencyDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<CompetencyBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new CompetencyBean()));
        TextField textField = new TextField("name");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                CompetencyBean competencyBean = (CompetencyBean) form.getModelObject();
                provider.setCompetencyBean(competencyBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class CompetencyDataProvider extends ListDataProvider<CompetencyBean> {
        private CompetencyBean competencyBean = null;
        public void setCompetencyBean(CompetencyBean competencyBean) {
            this.competencyBean = competencyBean;
        }
        @Override
        protected List<CompetencyBean> getData() {
            if (competencyBean == null || null == competencyBean.getName() || "".equals(competencyBean.getName().trim()))
                return competencyService.getAllEntity();
            else {
                return competencyService.getAllEntity();
            }
        }
    }
}
