package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IAreaBeanService;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.entities.AreaBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;

/**
 * 项目名称:  urgent-project
 * 类描述:    区域增加或修改页面类
 * 创建人:    sunlf
 * 创建时间:  2014/8/7 15:55
 * 修改人:    sunlf
 * 修改时间:  2014/8/7 15:55
 * 修改备注:  [说明本次修改内容]
 */
public class AreaAddPage extends Panel {
    private final static String CONST_TYPE = "area_relation"; //type const
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    private boolean isAddChild = false;
    private AreaBean bean;
    private Map<String, String> typeMap;

    @Inject
    @Reference(id = "areaBeanService", serviceInterface = IAreaBeanService.class)
    private IAreaBeanService areaBeanService;

    //注入字典业务服务
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;

    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public AreaAddPage(String id, String type, IModel<AreaBean> model) {
        super(id, model);
        this.type = type;
        typeMap = dictBeanService.getDictMap(CONST_TYPE);

        if (model.getObject() == null)
        //new model
        {
            isAdd = true;
            initForm(Model.of(new AreaBean()));
        } else
        //edit model
        {
            isAdd = false;
            initForm(model);
        }


    }

    /**
     * 新增子节点的构造函数
     *
     * @param id
     * @param newTabType
     * @param model
     * @param bean
     */
    public AreaAddPage(String id, String newTabType, IModel<AreaBean> model, AreaBean bean) {
        super(id, model);
        this.type = newTabType;
        this.bean = bean;
        AreaBean areaBean = new AreaBean();
        areaBean.setParent(bean);
        initForm(Model.of(areaBean));
        isAddChild = true;
        typeMap = dictBeanService.getDictMap(CONST_TYPE);
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<AreaBean> model) {
        final Form<AreaBean> areaForm = new Form("areaForm", new CompoundPropertyModel(model));

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        areaForm.add(feedbackPanel.setOutputMarkupId(true));


        areaForm.add(new Label("parent.name"));
        areaForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        IModel dropDownModel = new Model() {
            public java.io.Serializable getObject() {
                return new ArrayList(typeMap.keySet());
            }
        };
        //下拉列表
        DropDownChoice<String> listType = new DropDownChoice<String>(
                "type", dropDownModel, new IChoiceRenderer() {
            public String getDisplayValue(Object object) {
                return typeMap.get(object);
            }

            public String getIdValue(Object object, int index) {
                return object.toString();
            }
        });
        areaForm.add(listType);
//        areaForm.add(new TextField("type").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        areaForm.add(new TextField("code").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        areaForm.add(new AjaxButton("save", areaForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    AreaBean areaBean = (AreaBean) form.getModelObject();
                    areaBeanService.saveEntity(areaBean);
                    if (isAdd) {
                        areaForm.setModelObject(new AreaBean());
                    }
                    if (isAddChild) {
                        AreaBean bean = new AreaBean();
                        bean.setParent(AreaAddPage.this.bean);
                        areaForm.setModelObject(bean);
                    }
                    feedbackPanel.info(type + areaBean.getName() + "成功！");
                    target.add(areaForm);
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

        areaForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
        areaForm.setOutputMarkupId(true);
        areaForm.add(new JSR303FormValidator());
        add(areaForm);
    }
}
