package com.daren.rescue.webapp.wicket.page;

import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.rescue.entities.RescueBean;
import org.apache.wicket.model.IModel;

/**
 * @类描述：救援队管理-地图标注
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class MapPage extends IrisAbstractDialog<RescueBean> {
    public MapPage(String id, String title, IModel<RescueBean> model) {
        super(id, title, model);
    }
}