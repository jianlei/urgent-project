package com.daren.core.web.component.navigator;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/31 0:11
 * 修改人:    sunlf
 * 修改时间:  2014/7/31 0:11
 * 修改备注:  [说明本次修改内容]
 */
public class CustomerPagingNavigator extends AjaxPagingNavigator {
    public CustomerPagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
    }

    public CustomerPagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }
}
