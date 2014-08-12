package com.daren.core.web.api.provider;

import org.apache.wicket.Component;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/11 14:09
 * 修改人:    sunlf
 * 修改时间:  2014/8/11 14:09
 * 修改备注:  [说明本次修改内容]
 */
public interface IAboutDialogProvider extends IComponentProvider {
    public Component getComponent(String id, String title);
}
