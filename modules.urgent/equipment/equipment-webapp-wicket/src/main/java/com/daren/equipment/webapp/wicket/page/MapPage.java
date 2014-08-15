package com.daren.equipment.webapp.wicket.page;

import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.equipment.entities.EquipmentBean;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import org.apache.wicket.model.IModel;

/**
 * @类描述：救援队管理-地图标注
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class MapPage extends IrisAbstractDialog<EquipmentBean> {
    public MapPage(String id, String title, IModel<EquipmentBean> model) {
        super(id, title, model);
    }

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("width", 600);
        behavior.setOption("height", 500);
    }
}