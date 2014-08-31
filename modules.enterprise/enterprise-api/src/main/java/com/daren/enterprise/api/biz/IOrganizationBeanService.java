package com.daren.enterprise.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.enterprise.entities.OrganizationBean;

import java.util.List;
import java.util.Map;

/**
 * 监管机构
 * Created by xukexin on 2014/8/26.
 */
public interface IOrganizationBeanService extends IBizService {
    /**
     * 获取监管机构的列表
     * @param organizationBean
     * @return
     */
    List<OrganizationBean> queryOrganization(OrganizationBean organizationBean);

    /**
     *
     * @return
     */
    Map<String, String> getAllBeansToHashMap();
    /**
     * 根据名称进行查询
     * @param term
     * @param page
     * @param page_size
     * @return
     */
    List<OrganizationBean> findByName(String term, int page, int page_size);

    /**
     * 通过机构代码获得机构
     * @param id
     * @return
     */
    OrganizationBean getByJgdm(String id);
}
