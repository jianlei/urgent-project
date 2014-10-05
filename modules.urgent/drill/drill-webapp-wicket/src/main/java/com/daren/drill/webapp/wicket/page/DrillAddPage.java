package com.daren.drill.webapp.wicket.page;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.api.IConst;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.entities.UrgentDrillBean;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.form.EntTextField;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.Map;

/**
 * @类描述：应急演练-添加
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DrillAddPage extends Panel {

    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    protected boolean isEnt = false;//判断是否为企业用户
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    protected IDictBeanService dictBeanService;
    private boolean isAdd;
    //注入服务
    @Inject
    @Reference(id = "urgentDrillBeanService", serviceInterface = IUrgentDrillBeanService.class)
    private IUrgentDrillBeanService urgentDrillBeanService;
    @Inject
    @Reference(id = "userBeanService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;
    @Inject
    @Reference(id = "enterpriseBeanService", serviceInterface = IEnterpriseBeanService.class)
    private IEnterpriseBeanService enterpriseBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public DrillAddPage(String id, String type, IModel<UrgentDrillBean> model) {
        super(id, model);
        this.type = type;
        isEnt = (getApplication().getName().equals(IConst.COMPANY_WICKET_APPLICATION_NAME))?true:false;
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
        UrgentDrillBean urgentDrillBean = model.getObject();
        //企业id
        long qyid = urgentDrillBean.getQyid();
        if(qyid==0){
            UserBean userBean = userBeanService.getCurrentUser();
            String qyidStr = userBean!=null ? userBean.getQyid() : "0";
            qyid = Long.parseLong(qyidStr==null?"0":qyidStr);
        }
        EnterpriseBean enterpriseBean = (EnterpriseBean)enterpriseBeanService.getEntity(qyid);
        String qymc = enterpriseBean!=null ? enterpriseBean.getQymc() : "";
        final Map<String,String> confirm_map =dictBeanService.getDictMap(IDictConstService.DRILL_CONFIRM);
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        if(model!=null && urgentDrillBean.getIs_confirm()==0){
            feedbackPanel.info("企业上传的应急演练需由安监局确认后生效！");
        }
        dictForm.add(feedbackPanel.setOutputMarkupId(true));
        dictForm.add(new EntTextField("name",1));
        dictForm.add(new EntTextField("description",1));

        int is_confrim = urgentDrillBean!=null ? urgentDrillBean.getIs_confirm() : 0;
        dictForm.add(new Label("confirm_status", confirm_map.get(is_confrim+"")));
        dictForm.add(new Label("qymc", qymc));

        AjaxButton ajaxButton = new AjaxButton("save", dictForm) {

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                if(isEnt){          //企业用户
                    tag.put("value", "保存");
                }else{              //监管机构
                    tag.put("value", "确认");
                }
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    UrgentDrillBean dictBean = (UrgentDrillBean) form.getModelObject();
                    dictBean.setEnterpriseId(1l);
                    //企业id
                    long qyid = dictBean.getQyid();
                    if(qyid==0){
                        UserBean userBean = userBeanService.getCurrentUser();
                        String qyidStr = userBean!=null ? userBean.getQyid() : "0";
                        qyid = Long.parseLong(qyidStr==null?"0":qyidStr);
                    }
                    dictBean.setQyid(qyid);
                    //确认状态
                    if(isEnt){      //企业用户
                        dictBean.setIs_confirm(0);
                    }else{          //监管机构
                        dictBean.setIs_confirm(1);
                    }
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

        };
        dictForm.add(ajaxButton);

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