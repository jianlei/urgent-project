package com.daren.expert.webapp.wicket.page;

import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.expert.entities.EnterpriseExpertBean;
import org.apache.wicket.model.IModel;

/**
 * @类描述：专家管理-地图标注
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EnterpriseMapPage extends IrisAbstractDialog<EnterpriseExpertBean> {
    public EnterpriseMapPage(String id, String title, IModel<EnterpriseExpertBean> model) {
        super(id, title, model);
    }
}