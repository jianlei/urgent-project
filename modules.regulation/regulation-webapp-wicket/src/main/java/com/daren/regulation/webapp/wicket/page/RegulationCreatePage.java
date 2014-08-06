package com.daren.regulation.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.regulation.api.biz.IRegulationBeanService;
import com.daren.regulation.entities.RegulationBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

/**
 * @类描述：法律法规
 * @创建人：张清欣
 * @创建时间：2014-07-28 下午16:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RegulationCreatePage extends BasePanel {
    @Inject
    private IRegulationBeanService regulationService;

    public RegulationCreatePage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final RegulationBean regulationBean = new RegulationBean();
        Form form = new Form("form", new CompoundPropertyModel(regulationBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(new TextField("name"));
        form.add(new TextField("description"));
        //保存按钮
        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                RegulationBean regulationBean1 = (RegulationBean) form.getModelObject();
                regulationBean1.setEnterpriseId(1l);
                regulationService.saveEntity(regulationBean1);
                super.onSubmit(target, form);
               /* wmc.removeAll();
                wmc.addOrReplace(new RegulationPage(id, wmc));
                target.add(wmc);*/
            }
        };
        add(ajaxSubmitLinkCreate);
        //返回按钮
        AjaxLink ajaxLinkReturn = new AjaxLink("return") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                /*wmc.removeAll();
                wmc.addOrReplace(new RegulationPage(id, wmc));
                ajaxRequestTarget.add(wmc);*/
            }
        };
        this.add(ajaxLinkReturn);
    }
}
