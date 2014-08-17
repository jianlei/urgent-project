package com.daren.enterprise.webapp.gis.page;

import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/16 13:50
 * 修改人:    sunlf
 * 修改时间:  2014/8/16 13:50
 * 修改备注:  [说明本次修改内容]
 */
public class GisContainer extends BasePanel {
    GisPanel components;

    public GisContainer(String id, WebMarkupContainer wmc) {
        super(id, wmc);
//        components = new GisPanel("gis", wmc);
//        this.add(components);
    }
}
