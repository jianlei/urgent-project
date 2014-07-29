package com.daren.majorhazardsource.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.majorhazardsource.api.biz.IMajorHazardSourceBeanService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MajorHazardSourcePage extends BasePanel {

    @Inject
    private IMajorHazardSourceBeanService majorHazardSourceService;

    public MajorHazardSourcePage(String id, WebMarkupContainer wmc) {


        super(id, wmc);

        List list=majorHazardSourceService.getAllEntity();
    }
}