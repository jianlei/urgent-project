package com.daren.core.web.wicket.manager;

import com.daren.core.web.api.workflow.IFormHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-1-24
 * Time: 下午6:47
 * To change this template use File | Settings | File Templates.
 */
public class FormPanelManager {

    private static FormPanelManager install;
    //form集合
    private Map<String, List<IFormHandler>> formMap = new HashMap<>();

    private FormPanelManager() {

    }

    public synchronized static FormPanelManager getInstall() {
        if (install == null) {
            install = new FormPanelManager();
        }
        return install;
    }

    public List<IFormHandler> getFormPanelMap(String key) {
        List<IFormHandler> list = formMap.get(key);
        return list;
    }

   

    /**
     * 根据project name添加
     *
     * @param formHandler
     */
    public void add(IFormHandler formHandler) {
        List<IFormHandler> list;
        String name = formHandler.getProcessDefinitionId();
        if (formMap.containsKey(name)) {
            list = formMap.get(name);
            list.add(formHandler);
        } else {
            list = new ArrayList<>();
            list.add(formHandler);
            formMap.put(name, list);
        }

    }

    public void remove(IFormHandler formHandler) {
        List<IFormHandler> list;
        String name = formHandler.getProcessDefinitionId();
        list = formMap.get(name);
        list.remove(formHandler);
        if (list.size() == 0) {
            formMap.remove(name);
        }
    }


    /**
     * 通过流程id和formkey返回form实例
     * @param DefinitionId 流程id
     * @param formKey formkey
     * @return
     */
    public IFormHandler findFormByKey(String DefinitionId,String formKey) {
        IFormHandler formHandler=null;
        List<IFormHandler> formHandlerList= formMap.get(DefinitionId);
        for (IFormHandler form:formHandlerList){
            if(form.getFormKey().equals(formKey))
                return form;
        }
        return formHandler;
    }


}
