package com.daren.competency.webapp.wicket.page;

import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.entities.CompetencyBean;
import com.daren.core.util.DateUtil;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;

/**
 * @类描述：显示业务数据的页面类
 * @创建人： zhangqingxin
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class CompetencyBizDataPanel extends Panel {
    @Inject
    ICompetencyService competencyService;
    CompetencyBean bean;
    public CompetencyBizDataPanel(String id, String bizId) {
        super(id);
        bean= (CompetencyBean) competencyService.getEntity(new Long(bizId));
        this.setDefaultModel(new CompoundPropertyModel(bean));
        addBizData();
        
    }

    private void addBizData() {
        //add data to form
        add(new Label("name",new PropertyModel<String>(bean, "name")));
        add(new Label("sex",new PropertyModel<String>(bean, "sex")));
        add(new Label("phone",new PropertyModel<String>(bean, "phone")));
        add(new Label("enterpriseName",new PropertyModel<String>(bean, "enterpriseName")));
        add(new Label("title",new PropertyModel<String>(bean, "title")));
        add(new Label("cultureLevel",new PropertyModel<String>(bean, "cultureLevel")));
        add(new Label("id_code",new PropertyModel<String>(bean, "id_code")));
        add(new Label("unitType",new PropertyModel<String>(bean, "unitType")));
        add(new Label("qualificationsType",new PropertyModel<String>(bean, "qualificationsType")));
        add(new Label("code",new PropertyModel<String>(bean, "code")));
        Date awardDate=new PropertyModel<Date>(bean, "awardDate").getObject();
        add(new Label("awardDate", DateUtil.convertDateToString(awardDate, DateUtil.shortSdf)));
        Date effectiveDate=new PropertyModel<Date>(bean, "effectiveDate").getObject();
        add(new Label("effectiveDate", DateUtil.convertDateToString(effectiveDate, DateUtil.shortSdf)));
        add(new Label("linkHandle",new PropertyModel<String>(bean, "linkHandle")));
    }
}
