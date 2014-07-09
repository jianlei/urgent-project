package com.daren.enterprise.webapp.wicket.page;


import com.daren.core.web.wicket.BasePage;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import org.apache.aries.blueprint.annotation.Reference;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterprisePage extends BasePage {
    @Named
    @Inject
    @Reference(id = "enterpriseService", serviceInterface = IEnterpriseBeanService.class)
    private IEnterpriseBeanService enterpriseBeanService;


}