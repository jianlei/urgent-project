package com.daren.enterprise.webapp.component.utils;

import com.daren.core.api.IConst;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextArea;

/**
 * 扩展的textarea，当为企业用户登录时，内容不可修改
 * Created by xukexin on 2014-09-28.
 */
public class EntTextArea<T> extends TextArea {

    protected boolean isEnt=false;//判断是否为企业用户

    public EntTextArea(String id) {
        super(id);
        isEnt=(getApplication().getName().equals(IConst.COMPANY_WICKET_APPLICATION_NAME))?true:false;
    }

    @Override
    public boolean isEnabled() {
        return !isEnt;
    }

    @Override
    protected void onDisabled(ComponentTag tag) {
        tag.put("readonly", "readonly");
    }
}
