package com.daren.enterprise.webapp.component.form;

import com.daren.core.web.component.select2.Select2Choice;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.data.EnterpriseProvider;
import org.apache.wicket.model.IModel;

/**
 * 项目名称:  urgent-project
 * 类描述:    企业搜索下拉组件类
 * 创建人:    sunlf
 * 创建时间:  2014/8/15 11:10
 * 修改人:    sunlf
 * 修改时间:  2014/8/15 11:10
 * 修改备注:  [说明本次修改内容]
 */
public class EnterpriseSelect2Choice extends Select2Choice<EnterpriseBean> {
    public EnterpriseSelect2Choice(String id, IModel model) {
        super(id, model, new EnterpriseProvider());
        getSettings().setMinimumInputLength(2);
    }
}
