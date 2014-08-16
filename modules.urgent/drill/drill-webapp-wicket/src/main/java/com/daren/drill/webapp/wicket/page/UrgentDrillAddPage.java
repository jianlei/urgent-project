package com.daren.drill.webapp.wicket.page;

import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.entities.UrgentDrillBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * @类描述：应急演练-添加
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UrgentDrillAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    //注入服务
    @Inject
    @Reference(id = "urgentDrillBeanService", serviceInterface = IUrgentDrillBeanService.class)
    private IUrgentDrillBeanService urgentDrillBeanService;
    private FeedbackPanel feedbackPanel; //信息显示

    public UrgentDrillAddPage(String id, String type, IModel<UrgentDrillBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null) {
            isAdd = true;
            initForm(Model.of(new UrgentDrillBean()));
        } else {
            isAdd = false;
            initForm(model);
        }


    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<UrgentDrillBean> model) {
        final Form<UrgentDrillBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));

        feedbackPanel = new FeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));
        dictForm.add(new TextField("name"));
        dictForm.add(new TextField("description"));
        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    UrgentDrillBean dictBean = (UrgentDrillBean) form.getModelObject();
                    dictBean.setEnterpriseId(1l);
                    urgentDrillBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new UrgentDrillBean());
                    }
                    feedbackPanel.info(type + dictBean.getName() + "成功！");

                    target.add(form);
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

        dictForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
        dictForm.setOutputMarkupId(true);
//        dictForm.add(new JSR303FormValidator());
        add(dictForm);
    }
}