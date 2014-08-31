package com.daren.enterprise.webapp.component.form;

import com.daren.enterprise.entities.OrganizationBean;
import com.daren.enterprise.webapp.component.data.OrganizationProvider;
import com.vaynberg.wicket.select2.Select2Choice;
import org.apache.wicket.model.IModel;

/**
 * @类描述：监管机构选择
 * @创建人：xukexin
 * @创建时间：2014/8/30 13:01
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrgnizationSelect2Choice extends Select2Choice<OrganizationBean> {


    public OrgnizationSelect2Choice(String id, IModel model) {
        super(id, model, new OrganizationProvider());
        getSettings().setMinimumInputLength(2);
    }
}
