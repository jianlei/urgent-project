package com.daren.gis.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.core.api.IConst;
import com.daren.core.web.wicket.BasePanel;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.entities.DigitalPlanBean;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.gis.webapp.wicket.page.model.ResourceJson;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.google.gson.Gson;
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
    @Inject
    @Reference(id = "accidentBeanService", serviceInterface = IAccidentBeanService.class)
    private IAccidentBeanService accidentBeanService;

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
                List<DocumentBean> list = new ArrayList<DocumentBean>();
                String param1Value = getRequest().getRequestParameters().getParameterValue(AJAX_PARAM1_NAME).toString();
                Gson gson = new Gson();
                ResourceJson json = gson.fromJson(param1Value, ResourceJson.class);
                if (null != json.getReserve() && json.getReserve().length > 0) {
                    String[] reserve = json.getReserve();
                    for (int i = 0; i < reserve.length; i++) {
                        ReservePlanBean reservePlanBean = (ReservePlanBean) reservePlanBeanService.getEntity(Long.parseLong(reserve[i]));
                        DocumentBean DocumentBean = (DocumentBean)uploadDocumentService.getEntity(Long.parseLong(reservePlanBean.getComprehensivePlanId()));
                        list.add(DocumentBean);
                    }
                }
                if (null != json.getDigital() && json.getDigital().length > 0) {
                    String[] digital = json.getDigital();
                    for (int i = 0; i < digital.length; i++) {
                        DigitalPlanBean digitalPlanBean = (DigitalPlanBean) digitalPlanBeanService.getEntity(Long.parseLong(digital[i]));
                        DocumentBean DocumentBean = (DocumentBean) uploadDocumentService.getEntity(Long.parseLong(digitalPlanBean.getDigitalPlanId()));
                        list.add(DocumentBean);
                    }
                }
                if ((null != json.getEquipment() && json.getEquipment().length > 0) || (null != json.getExpert() && json.getExpert().length > 0) || (null != json.getRescue() && json.getRescue().length > 0)) {
                    String fileName = accidentBeanService.printAccidentResource(json.getEquipment(), json.getExpert(), json.getRescue());
                    DocumentBean documentBean = new DocumentBean();
                    documentBean.setName("综合资源列表.xls");
                    documentBean.setFilePath(IConst.OFFICE_WEB_PATH_WRITE + fileName);
                    list.add(documentBean);
                }

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