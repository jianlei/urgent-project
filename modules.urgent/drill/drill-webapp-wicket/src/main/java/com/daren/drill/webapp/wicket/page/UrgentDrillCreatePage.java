package com.daren.drill.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.drill.api.biz.IUrgentDrillBeanService;
import com.daren.drill.entities.UrgentDrillBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

/**
 * @类描述：应急演练
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UrgentDrillCreatePage extends BasePanel {
    @Inject
    private IUrgentDrillBeanService urgentDrillBeanService;

    public UrgentDrillCreatePage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final UrgentDrillBean urgentDrillBean = new UrgentDrillBean();
        Form form = new Form("form", new CompoundPropertyModel(urgentDrillBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(new TextField("name"));
        form.add(new TextField("description"));
        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                UrgentDrillBean urgentDrillBean1 = (UrgentDrillBean) form.getDefaultModelObject();
                urgentDrillBean.setEnterpriseId(1l);
                urgentDrillBeanService.saveEntity(urgentDrillBean);
                super.onSubmit(target, form);
                wmc.removeAll();
                wmc.addOrReplace(new UrgentDrillPage(id, wmc));
                target.add(wmc);
            }
        };
        add(ajaxSubmitLinkCreate);
        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                wmc.removeAll();
                wmc.addOrReplace(new UrgentDrillPage(id, wmc));
                ajaxRequestTarget.add(wmc);
            }
        };
        this.add(ajaxLinkReturn);
    }
}