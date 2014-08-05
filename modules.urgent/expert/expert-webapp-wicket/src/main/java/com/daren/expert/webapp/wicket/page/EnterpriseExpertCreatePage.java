package com.daren.expert.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.expert.entities.EnterpriseExpertBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @类描述：企业专家
 * @创建人：张清欣
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EnterpriseExpertCreatePage extends BasePanel {
    private static List<String> TYPE = new ArrayList<String>();

    static {
        TYPE.add("1");
        TYPE.add("2");
        TYPE.add("3");
    }

    public EnterpriseExpertCreatePage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final EnterpriseExpertBean enterpriseExpertBean = new EnterpriseExpertBean();
        Form form = new Form("form", new CompoundPropertyModel(enterpriseExpertBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(new TextField("name"));
        form.add(new TextField("contactInformation"));
        DropDownChoice typeChoice = new DropDownChoice("type", TYPE) {
            @Override
            protected void onModelChanged() {
                super.onModelChanged();
            }
        };
        form.add(typeChoice);
        typeChoice.setRequired(true);
        typeChoice.setNullValid(true);

        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

            }
        };
        add(ajaxSubmitLinkCreate);
        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                wmc.removeAll();
                wmc.addOrReplace(new EnterpriseExpertPage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkReturn);
    }
}