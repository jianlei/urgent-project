package com.daren.fireworks.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.entities.FireworksBean;
import com.daren.fireworks.webapp.wicket.Const;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.FormService;
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
import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    FireworksDataProvider provider = new FireworksDataProvider();
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private IFireworksService fireworksService;
    @Inject
    private TaskService taskService;
    @Inject
    private IUserBeanService userBeanService;

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    private String phoneNumber = null;
    final WebMarkupContainer wmc;
    public FireworksListPage(final String id, final WebMarkupContainer wmc, String phone) {
        super(id, wmc);
        this.wmc = wmc;
        phoneNumber = phone;
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
        DataView<FireworksBean> listView = new DataView<FireworksBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<FireworksBean> item) {
                final FireworksBean fireworksBean = item.getModelObject();
                item.add(new Label("name", fireworksBean.getName()));
                item.add(new Label("head", fireworksBean.getHead()));
                item.add(new Label("phone", fireworksBean.getPhone()));
                item.add(new Label("address", fireworksBean.getAddress()));
                item.add(new Label("economicsType", fireworksBean.getEconomicsType()));
                item.add(new Label("storageAddress", fireworksBean.getStorageAddress()));
                item.add(new Label("scope", fireworksBean.getScope()));
                item.add(new Label("linkHandle", fireworksBean.getLinkHandle()));
                item.add(getToCreatePageLink("check_name", fireworksBean));
                item.add(getToUploadPageLink("upload", fireworksBean));
                item.add(getDuplicateLink("duplicate", fireworksBean));
                item.add(getSubmitLink("submit", fireworksBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final FireworksBean fireworksBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(fireworksBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final FireworksBean fireworksBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(fireworksBean, target);
            }
        };
        if(fireworksBean.getLinkHandle()!=null&&!"".equals(fireworksBean.getLinkHandle())){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final FireworksBean fireworksBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传附件", fireworksBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getSubmitLink(String wicketId, final FireworksBean fireworksBean){
        AjaxLink alinkSubmit = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String bizKey= Const.PROCESS_KEY + ":" + fireworksBean.getPhone() + ":" + fireworksBean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(fireworksBean.getPhone());
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(Const.PROCESS_KEY, bizKey);
                    fireworksBean.setProcessInstanceId(instance.getProcessInstanceId());
                    Task task=taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
                    fireworksBean.setLinkHandle(task.getName());
                    fireworksService.saveEntity(fireworksBean);
                    feedbackPanel.info("烟花爆竹经营许可证,启动成功！");
                }catch (Exception e){
                    feedbackPanel.info("烟花爆竹经营许可证,启动失败！");
                }finally {
                    identityService.setAuthenticatedUserId(null);
                }
                target.add(feedbackPanel);
                target.add(wmc);
            }
        };
        if(fireworksBean.getLinkHandle()!=null&&!"".equals(fireworksBean.getLinkHandle())){
            alinkSubmit.setVisible(false);
        }
        return alinkSubmit;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final FireworksBean fireworksBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传附件", fireworksBean, "upload");
            }
        };
        if(fireworksBean.getLinkHandle()!=null&&!"".equals(fireworksBean.getLinkHandle())){
            ajaxLink.setVisible(false);
        }
        return ajaxLink;
    }

    protected void createButtonOnClick(FireworksBean fireworksBean, AjaxRequestTarget target) {
    }

    private void createDialog(AjaxRequestTarget target, final String title, FireworksBean fireworksBean, String type) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", title, fireworksBean.getId(), type, "fireworks") {
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
    private void createQuery(final WebMarkupContainer table, final FireworksDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<FireworksBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new FireworksBean()));
        TextField textField = new TextField("name");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                FireworksBean fireworksBean = (FireworksBean) form.getModelObject();
                provider.setFireworksBean(fireworksBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class FireworksDataProvider extends ListDataProvider<FireworksBean> {
        private FireworksBean fireworksBean = null;
        public void setFireworksBean(FireworksBean fireworksBean) {
            this.fireworksBean = fireworksBean;
        }
        @Override
        protected List<FireworksBean> getData() {
            return fireworksService.getFireworksBeanByLoginName(userBeanService.getCurrentUserLoginName());
        }
    }
}