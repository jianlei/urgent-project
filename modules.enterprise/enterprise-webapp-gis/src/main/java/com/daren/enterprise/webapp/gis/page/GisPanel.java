package com.daren.enterprise.webapp.gis.page;

import com.daren.core.web.wicket.BasePanel;
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

            }
        };
        this.add(ajaxLinkExperts);
    }
}
