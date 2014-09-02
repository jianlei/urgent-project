package com.daren.gis.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.entities.DigitalPlanBean;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 14:29
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 14:29
 * 修改备注:  [说明本次修改内容]
 */
public class GisPanel extends BasePanel implements IHeaderContributor {
    final WebMarkupContainer dialogWrapper;
    DocumentListPage dialog;
    String AJAX_PARAM1_NAME="type";
    String AJAX_PARAM2_NAME="docId";
    List<DocumentBean> list = new ArrayList<DocumentBean>();
    //注入服务
    @Inject
    @Reference(id = "reservePlanBeanService", serviceInterface = IReservePlanBeanService.class)
    private IReservePlanBeanService reservePlanBeanService;
    @Inject
    @Reference(id = "uploadDocumentService", serviceInterface = IUploadDocumentService.class)
    private IUploadDocumentService uploadDocumentService;
    @Inject
    @Reference(id = "digitalPlanBeanService", serviceInterface = IDigitalPlanBeanService.class)
    private IDigitalPlanBeanService digitalPlanBeanService;

    //ajax target container
    public GisPanel(String id, WebMarkupContainer wmc) {
        super(id,wmc);
        // wmc.removeAll();
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

        //调用OnDomReadyHeader方法
        this.add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                response.render(OnDomReadyHeaderItem.forScript("loadScript();"));
            }
        });

        AbstractDefaultAjaxBehavior ajaxBehavior = new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                String param1Value = getRequest().getRequestParameters().getParameterValue(AJAX_PARAM1_NAME).toString();
                String param2Value = getRequest().getRequestParameters().getParameterValue(AJAX_PARAM2_NAME).toString();
                //0企业   1监管机构预案   2专家    3救援队     4物资
                String[] type = param2Value.split("_");
                if(type[0].split(":").length>1){
                    String values = type[0].split(":")[1];
                    for (int i=0; i<values.split("#").length; i++){
                        String v = values.split("#")[i];
                        ReservePlanBean reservePlanBean = (ReservePlanBean) reservePlanBeanService.getEntity(Long.parseLong(v));
                        DocumentBean DocumentBean = (DocumentBean)uploadDocumentService.getEntity(Long.parseLong(reservePlanBean.getComprehensivePlanId()));
                        list.add(DocumentBean);
                    }
                }
                if(type[1].split(":").length>1){
                    String values = type[1].split(":")[1];
                    for (int i = 0; i < values.split("#").length; i++) {
                        String v = values.split("#")[i];
                        DigitalPlanBean digitalPlanBean = (DigitalPlanBean) digitalPlanBeanService.getEntity(Long.parseLong(v));
                        DocumentBean DocumentBean = (DocumentBean) uploadDocumentService.getEntity(Long.parseLong(digitalPlanBean.getDigitalPlanId()));
                        list.add(DocumentBean);
                    }
                }
                if(type[2].split(":").length>1){
                    String values = type[2].split(":")[1];
                }
                if(type[3].split(":").length>1){
                    String values = type[3].split(":")[1];
                }
                if(type[4].split(":").length>1){
                    String values = type[4].split(":")[1];
                }
                System.out.println(list.size());
                createDocumentDialog(target, list, "预案列表");
            }
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);
                String callBackScript = getCallbackFunction(CallbackParameter.explicit(AJAX_PARAM1_NAME), CallbackParameter.explicit(AJAX_PARAM2_NAME)).toString();
                callBackScript = "sendToServer="+callBackScript+";";
                response.render(OnDomReadyHeaderItem.forScript(callBackScript));
            }
        };
        add(ajaxBehavior);
    }

    private void createDocumentDialog(AjaxRequestTarget target, final List<DocumentBean> list, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new DocumentListPage("dialog", title, list);
        target.add(dialogWrapper);
        dialog.open(target);
    }
}