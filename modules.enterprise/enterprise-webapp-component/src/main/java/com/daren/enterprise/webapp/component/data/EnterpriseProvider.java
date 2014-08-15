package com.daren.enterprise.webapp.component.data;

import com.daren.core.util.JNDIHelper;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.TextChoiceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/15 11:19
 * 修改人:    sunlf
 * 修改时间:  2014/8/15 11:19
 * 修改备注:  [说明本次修改内容]
 */
public class EnterpriseProvider extends TextChoiceProvider<EnterpriseBean> {
    IEnterpriseBeanService enterpriseBeanService;
    int PAGE_SIZE = 10;

    public EnterpriseProvider() {
        try {
            enterpriseBeanService = JNDIHelper.getJNDIServiceForName(IEnterpriseBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getDisplayText(EnterpriseBean choice) {
        return choice.getQyid();
    }

    @Override
    protected Object getId(EnterpriseBean choice) {
        return choice.getId();
    }

    @Override
    public void query(String term, int page, Response<EnterpriseBean> response) {
        response.addAll(queryMatches(term, page, PAGE_SIZE));
        response.setHasMore(response.size() == PAGE_SIZE);
    }

    private List<EnterpriseBean> queryMatches(String term, int page, int page_size) {
        return enterpriseBeanService.findByName(term, page, page_size);
    }

    @Override
    public Collection<EnterpriseBean> toChoices(Collection<String> ids) {
        ArrayList<EnterpriseBean> beans = new ArrayList<EnterpriseBean>();
        for (String id : ids) {
            beans.add((EnterpriseBean) enterpriseBeanService.getEntity(new Long(id)));
        }
        return beans;
    }


}