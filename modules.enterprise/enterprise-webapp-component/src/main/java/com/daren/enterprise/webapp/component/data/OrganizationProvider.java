package com.daren.enterprise.webapp.component.data;

import com.daren.core.util.JNDIHelper;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.entities.OrganizationBean;
import com.vaynberg.wicket.select2.ChoiceProvider;
import com.vaynberg.wicket.select2.Response;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @类描述：用于处理监管机构的下拉列表
 * @创建人：xukexin
 * @创建时间：2014/8/27 18:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrganizationProvider  extends ChoiceProvider<OrganizationBean> {
    IOrganizationBeanService organizationBeanService;
    int PAGE_SIZE = 10;

    public OrganizationProvider() {
        try {
            organizationBeanService = JNDIHelper.getJNDIServiceForName(IOrganizationBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getDisplayText(OrganizationBean choice) {
        return choice.getMc();
    }


    protected Object getId(OrganizationBean choice) {
        return choice.getId();
    }

    @Override
    public void query(String term, int page, Response<OrganizationBean> response) {
        response.addAll(queryMatches(term, page, PAGE_SIZE));
        response.setHasMore(response.size() == PAGE_SIZE);
    }

    @Override
    public void toJson(OrganizationBean choice, JSONWriter writer) throws JSONException {
        writer.key("id").value(getId(choice)).key("text").value(getDisplayText(choice));
    }

    private List<OrganizationBean> queryMatches(String term, int page, int page_size) {
        return organizationBeanService.findByName(term, page, page_size);
    }

    @Override
    public Collection<OrganizationBean> toChoices(Collection<String> ids) {
        ArrayList<OrganizationBean> beans = new ArrayList<OrganizationBean>();
        for (String id : ids) {
            beans.add((OrganizationBean) organizationBeanService.getEntity(new Long(id)));
        }
        return beans;
    }
}
