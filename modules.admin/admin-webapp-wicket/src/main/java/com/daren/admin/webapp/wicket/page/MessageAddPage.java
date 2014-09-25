package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IMessageBeanService;
import com.daren.admin.entities.MessageBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;


/**
 * 项目名称:  urgent-project
 * 类描述:   系统消息类的新增和修改页面类
 * 创建人:    sunlf
 * 创建时间:  2014/9/25 9:43
 * 修改人:    sunlf
 * 修改时间:  2014/8/4 9:43
 * 修改备注:  [说明本次修改内容]
 */
public class MessageAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    @Inject
    private IMessageBeanService msgBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public MessageAddPage(String id, String type, IModel<MessageBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
        //new model
        {
            isAdd = true;
            initForm(Model.of(new MessageBean()));
        } else
        //edit model
        {
            isAdd = false;
            initForm(model);
        }


    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<MessageBean> bean) {
        final Form<MessageBean> msgForm = new Form("msgForm", new CompoundPropertyModel(bean));

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        msgForm.add(feedbackPanel.setOutputMarkupId(true));

        msgForm.add(new TextField("title").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        msgForm.add(new TextField("message").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        msgForm.add(new TextField("userId").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        msgForm.add(new AjaxButton("save", msgForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    MessageBean dictBean = (MessageBean) form.getModelObject();
                    msgBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        msgForm.setModelObject(new MessageBean());
                    }
                    feedbackPanel.info(type + dictBean.getTitle() + "成功！");
                    target.add(msgForm);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        msgForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);

            }
        });
        msgForm.setOutputMarkupId(true);
        msgForm.add(new JSR303FormValidator());
        add(msgForm);
    }
}
