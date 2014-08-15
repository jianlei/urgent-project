package com.daren.enterprise.webapp.gis.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.entities.EquipmentBean;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.api.biz.ISafetySupervisionExpertBeanService;
import com.daren.expert.entities.EnterpriseExpertBean;
import com.daren.expert.entities.SafetySupervisionExpertBean;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.majorhazardsource.entities.MajorHazardSourceBean;
import com.daren.rescue.api.biz.IRescueBeanService;
import com.daren.rescue.entities.RescueBean;
import com.google.gson.Gson;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;

import javax.inject.Inject;
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
public class GisPanel extends BasePanel {
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

    //ajax target container
    public GisPanel(String id, WebMarkupContainer wmc) {
        super(id, wmc);

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
                List<MajorHazardSourceBean> list = hazardBeanService.getAllEntity();
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
    }
}
