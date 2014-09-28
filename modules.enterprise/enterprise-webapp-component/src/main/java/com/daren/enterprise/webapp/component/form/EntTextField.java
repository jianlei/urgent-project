package com.daren.enterprise.webapp.component.form;

import com.daren.core.api.IConst;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;

/**
 * 扩展的textfield(input)，当为企业用户登录时，内容不可修改
 * Created by xukexin on 2014-09-28.
 */
public class EntTextField extends TextField {

    protected boolean isEnt=false;//判断是否为企业用户

    public EntTextField(String id) {
        super(id);
        isEnt=(getApplication().getName().equals(IConst.COMPANY_WICKET_APPLICATION_NAME))?true:false;
    }

    public EntTextField(String id,int flag){
        super(id);
        isEnt=(getApplication().getName().equals(IConst.COMPANY_WICKET_APPLICATION_NAME))?false:true;
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
