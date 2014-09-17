package com.daren.apply.webapp.wicket.page;

import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageTabPage;
import com.daren.competency.webapp.wicket.page.CompetencyTabPage;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.core.web.wicket.security.CaptchaImage;
import com.daren.fireworks.webapp.wicket.page.FireworksTabPage;
import com.daren.operations.webapp.wicket.page.OperationsTabPage;
import com.daren.production.webapp.wicket.page.ProductionTabPage;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类描述：提供给第三方使用的申请流程页面
 * @创建人： 王凯冉
 * @创建时间：20140913
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ApplyPage extends WebPage {

    @Inject
    private transient RepositoryService repositoryService;

    RepeatingView rv = new RepeatingView("myApplyProcess");
    WebMarkupContainer wmc = new WebMarkupContainer("wmc");

    @NotNull(message = "'手机号'是必填项")
    @Pattern(regexp = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)", message = "手机格式不正确")
    String phone;


    String selected;

    @NotNull(message = "'校验码'是必填项")
    private String validateCode;

    public ApplyPage() {
        super();

        wmc.add(rv);
        this.add(wmc);
        rv.setOutputMarkupId(true);
        wmc.setOutputMarkupId(true);

        final JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);
        form.setOutputMarkupId(true);

        final TextField nameText = new TextField("phone");

        form.add(nameText);

        TextField validateCode = new TextField("validateCode");
        form.add(validateCode.add(new ValidationStyleBehavior()));

        final CaptchaImage captchaImage = new CaptchaImage("captchaImage");
        captchaImage.setOutputMarkupId(true);

        form.add(new AjaxFallbackLink("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                captchaImage.detach();
                if (target != null) {
                    target.add(captchaImage);
                } else {
                    // javascript is disable
                }
            }
        }.add(captchaImage));

        form.add(new JSR303FormValidator());


        AjaxButton ajaxSubmitLink = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                captchaImage.detach();
                target.add(captchaImage);
                ApplyPage apply = (ApplyPage) form.getModelObject();
                getPanelByProcessName(apply.getSelected(), apply.getPhone());
                target.add(feedbackPanel);
                target.add(wmc);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(feedbackPanel);
            }
        };
        form.add(ajaxSubmitLink);

        initSelect(form);
    }

    public void getPanelByProcessName(String processName, String phone) {
        rv.removeAll();
        if ("Demo Process".equals(processName)) {
            rv.add(new NotFoundProcess(rv.newChildId()));
        } else if ("请假流程".equals(processName)) {
            rv.add(new NotFoundProcess(rv.newChildId()));
        } else if ("危险化学品经营许可证".equals(processName)) {
            rv.add(new ChemistryManageTabPage(rv.newChildId(), wmc));
        } else if ("烟花爆竹经营许可证流程".equals(processName)) {
            rv.add(new FireworksTabPage(rv.newChildId(), wmc));
        } else if ("安全资格证书(培训)".equals(processName)) {
            rv.add(new CompetencyTabPage(rv.newChildId(), wmc));
        } else if ("特种作业人员操作资格证".equals(processName)) {
            rv.add(new OperationsTabPage(rv.newChildId(), wmc));
        } else if (" 安全生产许可证(非煤矿矿山企业)".equals(processName)) {
            rv.add(new ProductionTabPage(rv.newChildId(), wmc));
        } else if ("".equals(processName)) {

        } else {
            rv.add(new NotFoundProcess(rv.newChildId()));
        }
    }


    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap, Form form) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        form.add(listSites);
    }

    private void initSelect(Form form) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        Map<String, String> typeMap = new HashMap<String, String>();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ProcessDefinition processDefinition = list.get(i);
                typeMap.put(processDefinition.getName(), processDefinition.getName());
            }
        }
        initSelect("selected", typeMap, form);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
