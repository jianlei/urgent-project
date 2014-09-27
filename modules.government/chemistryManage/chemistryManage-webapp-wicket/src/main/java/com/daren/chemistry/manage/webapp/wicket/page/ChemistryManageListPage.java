package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.chemistry.manage.webapp.wicket.Const;
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
 * @类描述：危险化学品经营许可证
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    final WebMarkupContainer wmc;
    WindowGovernmentPage dialog;
    ChemistryManageDataProvider provider = new ChemistryManageDataProvider();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IChemistryManageBeanService chemistryManageBeanService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private TaskService taskService;
    @Inject
    private IUserBeanService userBeanService;
    private String pageType;

    public ChemistryManageListPage(final String id, final WebMarkupContainer wmc,String pageType) {
        super(id, wmc);
        this.wmc=wmc;
        this.pageType = pageType;
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
        DataView<ChemistryManageBean> listView = new DataView<ChemistryManageBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<ChemistryManageBean> item) {
                final ChemistryManageBean chemistryManageBean = item.getModelObject();
                item.add(new Label("qyCode", chemistryManageBean.getQyCode()));
                item.add(new Label("qyName", chemistryManageBean.getQyName()));
                item.add(new Label("address", chemistryManageBean.getAddress()));
                item.add(new Label("mainHead", chemistryManageBean.getMainHead()));
                item.add(new Label("phone", chemistryManageBean.getPhone()));
                item.add(new Label("linkHandle", chemistryManageBean.getLinkHandle()));
                item.add(getToCreatePageLink("check_name", chemistryManageBean));
                item.add(getToUploadPageLink("upload", chemistryManageBean));
                item.add(getDuplicateLink("duplicate", chemistryManageBean));
                item.add(getSubmitLink("submit", chemistryManageBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(final ChemistryManageBean chemistryManageBean) {
        AjaxButton ajaxLink = new AjaxButton("create") {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(chemistryManageBean, target);
            }
        };
        if("manage".equals(pageType)){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final ChemistryManageBean chemistryManageBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(chemistryManageBean, target);
            }
        };
        if((chemistryManageBean.getLinkHandle()!=null&&!"".equals(chemistryManageBean.getLinkHandle()))||"manage".equals(pageType)){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final ChemistryManageBean chemistryManageBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, chemistryManageBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getSubmitLink(String wicketId, final ChemistryManageBean chemistryManageBean){
        AjaxLink alinkSubmit = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String bizKey= Const.PROCESS_KEY + ":" + userBeanService.getCurrentUserLoginName() + ":" + chemistryManageBean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(userBeanService.getCurrentUserLoginName());
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(Const.PROCESS_KEY, bizKey);
                    chemistryManageBean.setProcessInstanceId(instance.getProcessInstanceId());
                    Task task=taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
                    chemistryManageBean.setLinkHandle(task.getName());
                    chemistryManageBeanService.saveEntity(chemistryManageBean);
                    feedbackPanel.info("危险化学品经营许可证,启动成功！");
                }catch (Exception e){
                    feedbackPanel.info("危险化学品经营许可证,启动失败！");
                }finally {
                    identityService.setAuthenticatedUserId(null);
                }
                target.add(feedbackPanel);
                target.add(wmc);
            }

        };
        if((chemistryManageBean.getLinkHandle()!=null&&!"".equals(chemistryManageBean.getLinkHandle()))||"manage".equals(pageType)){
            alinkSubmit.setVisible(false);
        }
        return alinkSubmit;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final ChemistryManageBean chemistryManageBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, chemistryManageBean, "upload");
            }
        };
        if((chemistryManageBean.getLinkHandle()!=null&&!"".equals(chemistryManageBean.getLinkHandle()))||"manage".equals(pageType)){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    private void createDialog(AjaxRequestTarget target, ChemistryManageBean chemistryManageBean, String type) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", "上传附件", chemistryManageBean.getId(), type, "chemistryManage") {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    protected void createButtonOnClick(ChemistryManageBean chemistryManageBean, AjaxRequestTarget target) {
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final ChemistryManageDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<ChemistryManageBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new ChemistryManageBean()));
        TextField textField = new TextField("qyName");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton(null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ChemistryManageBean chemistryManageBean = (ChemistryManageBean) form.getModelObject();
                provider.setChemistryManageBean(chemistryManageBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }

    class ChemistryManageDataProvider extends ListDataProvider<ChemistryManageBean> {
        private ChemistryManageBean chemistryManageBean = null;
        public void setChemistryManageBean(ChemistryManageBean chemistryManageBean) {
            this.chemistryManageBean = chemistryManageBean;
        }
        @Override
        protected List<ChemistryManageBean> getData() {
            if("enterprise".equals(pageType)){
                return chemistryManageBeanService.getChemistryManageByLoginName(userBeanService.getCurrentUserLoginName());
            }else{
                return chemistryManageBeanService.getAllEntity();
            }
        }
    }
}
