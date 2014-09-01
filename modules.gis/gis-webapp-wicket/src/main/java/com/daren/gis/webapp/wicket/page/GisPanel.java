package com.daren.gis.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.api.IConst;
import com.daren.core.web.component.office.WindowOfficePage;
import com.daren.core.web.wicket.BasePanel;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.entities.EquipmentBean;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.api.biz.ISafetySupervisionExpertBeanService;
import com.daren.expert.entities.EnterpriseExpertBean;
import com.daren.expert.entities.SafetySupervisionExpertBean;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.hazard.entities.HazardBean;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.entities.RescueBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.google.gson.Gson;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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
    WindowOfficePage dialog;
    String AJAX_PARAM1_NAME="type";
    String AJAX_PARAM2_NAME="docId";
    List<DocumentBean> list = new ArrayList<DocumentBean>();
    //注入服务
    @Inject
    @Reference(id = "rescueBeanService", serviceInterface = IRescueBeanService.class)
    private IRescueBeanService rescueBeanService;
    @Inject
    @Reference(id = "enterpriseExpertBeanService", serviceInterface = IEnterpriseExpertBeanService.class)
    private IEnterpriseExpertBeanService enterpriseExpertBeanService;
    @Inject
    @Reference(id = "equipmentBeanService", serviceInterface = ISafetySupervisionExpertBeanService.class)
    private ISafetySupervisionExpertBeanService safetySupervisionExpertBeanService;
    @Inject
    @Reference(id = "equipmentBeanService", serviceInterface = IEquipmentBeanService.class)
    private IEquipmentBeanService equipmentBeanService;
    @Inject
    @Reference(id = "hazardBeanService", serviceInterface = IHazardBeanService.class)
    private IHazardBeanService hazardBeanService;
    @Inject
    @Reference(id = "accidentBeanService", serviceInterface = IAccidentBeanService.class)
    private IAccidentBeanService accidentBeanService;
    @Inject
    @Reference(id = "reservePlanBeanService", serviceInterface = IReservePlanBeanService.class)
    private IReservePlanBeanService reservePlanBeanService;
    @Inject
    @Reference(id = "uploadDocumentService", serviceInterface = IUploadDocumentService.class)
    private IUploadDocumentService uploadDocumentService;

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
        //救援队标注
        AjaxLink ajaxLinkRescue = new AjaxLink("rescueButton") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                List<RescueBean> list = rescueBeanService.getAllEntity();
                Gson gson = new Gson();
                String string = gson.toJson(list);
                ajaxRequestTarget.prependJavaScript("parseRescue(" + string + ")");
            }
        };
        this.add(ajaxLinkRescue);

        //专家标注
        AjaxLink ajaxLinkExperts = new AjaxLink("expertsButton") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                List<EnterpriseExpertBean> listEnt = enterpriseExpertBeanService.getAllEntity();
                List<SafetySupervisionExpertBean> listSafe = safetySupervisionExpertBeanService.getAllEntity();
                Gson gsonEnt = new Gson();
                String strEnt = gsonEnt.toJson(listEnt);
                Gson gsonSafe = new Gson();
                String strSafe = gsonSafe.toJson(listSafe);
                ajaxRequestTarget.prependJavaScript("parseExpert(" + strEnt + "," + strSafe + ")");
            }
        };
        this.add(ajaxLinkExperts);

        //重大危险标注
        AjaxLink ajaxLinkMajor = new AjaxLink("majorButton") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                List<HazardBean> list = hazardBeanService.getAllEntity();
                Gson gson = new Gson();
                String string = gson.toJson(list);
                ajaxRequestTarget.prependJavaScript("parseMajor(" + string + ")");
            }
        };
        this.add(ajaxLinkMajor);

        //物资标注
        AjaxLink ajaxLinkEquipment = new AjaxLink("equipmentButton") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                List<EquipmentBean> list = equipmentBeanService.getAllEntity();
                Gson gson = new Gson();
                String string = gson.toJson(list);
                ajaxRequestTarget.prependJavaScript("parseEquipment(" + string + ")");
            }
        };
        this.add(ajaxLinkEquipment);

        //事故标注
        AjaxLink ajaxLinkAccident = new AjaxLink("accidentButton") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                List<AccidentBean> list = accidentBeanService.queryAccidentByAccidentLevel();
                Gson gson = new Gson();
                String string = gson.toJson(list);
                ajaxRequestTarget.prependJavaScript("parseAccident(" + string + ")");
            }
        };
        this.add(ajaxLinkAccident);

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
                String[] type = param2Value.split("^");
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
                FileInputStream fileInputStream = null;
                try {
                    File tempFile = new File(IConst.OFFICE_WEB_PATH_TEMP + "yingji.docx");
                    fileInputStream = new FileInputStream("D:/apache-tomcat/webapps/uploadfile/[B@15d3f49");
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                    fileInputStream.close();
                    createDialog(target, "Office", IConst.OFFICE_WEB_PATH_READ +  "yingji.docx");

                } catch (Exception e) {
                    e.printStackTrace();
                }


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

    private void createDialog(AjaxRequestTarget target, final String title, final String filePath) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowOfficePage("dialog", title, filePath) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }
}
