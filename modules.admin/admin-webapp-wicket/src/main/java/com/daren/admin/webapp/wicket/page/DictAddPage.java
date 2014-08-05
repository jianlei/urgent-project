package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.entities.DictBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;


/**
 * 项目名称:  urgent-project
 * 类描述:   字典类的新增和修改页面类
 * 创建人:    sunlf
 * 创建时间:  2014/8/4 9:43
 * 修改人:    sunlf
 * 修改时间:  2014/8/4 9:43
 * 修改备注:  [说明本次修改内容]
 */
public class DictAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;
    private FeedbackPanel feedbackPanel; //信息显示

    public DictAddPage(String id, String type, IModel<DictBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
            //new model
            initForm(Model.of(new DictBean()));
        else
            //edit model
            initForm(model);


    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<DictBean> model) {
        Form<DictBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));

        feedbackPanel = new FeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));

        dictForm.add(new TextField("label").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("value").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("type").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("description"));
        dictForm.add(new TextField("sort").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    DictBean dictBean = (DictBean) form.getModelObject();
                    dictBeanService.saveEntity(dictBean);
                    feedbackPanel.info(type + dictBean.getType() + "成功！");
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

        dictForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
        dictForm.setOutputMarkupId(true);
        dictForm.add(new JSR303FormValidator());
        add(dictForm);
    }
}
