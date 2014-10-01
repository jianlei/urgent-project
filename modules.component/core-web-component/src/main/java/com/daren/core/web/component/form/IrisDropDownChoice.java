package com.daren.core.web.component.form;

import com.daren.admin.api.biz.IDictBeanService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;

/**
 * 项目名称:  urgent-project
 * 类描述:    客户化下拉列表，通过数据字典的String，返回下拉控件
 * 创建人:    sunlf
 * 创建时间:  2014/8/13 10:37
 * 修改人:    sunlf
 * 修改时间:  2014/8/13 10:37
 * 修改备注:  [说明本次修改内容]
 */
public class IrisDropDownChoice<T> extends DropDownChoice {
    //注入字典业务服务
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;

    private Map<String, String> typeMap;
    private String key;

    /**
     * 构造函数
     *
     * @param id      组件id
     * @param dictKey 数据字典key
     */
    public IrisDropDownChoice(String id, String dictKey) {
        super(id);
        key = dictKey;
        typeMap = dictBeanService.getDictMap(key);
    }
    /**
     * 构造函数
     *
     * @param id      组件id
     * @param typeMap 下拉列表内容，不能传空
     */
    public IrisDropDownChoice(String id,Map<String, String> typeMap) {
        super(id);
        this.typeMap = typeMap;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel dropDownModel = new Model() {
            public java.io.Serializable getObject() {
                return new ArrayList(typeMap.keySet());
            }
        };

        IChoiceRenderer choiceRenderer = new IChoiceRenderer() {
            public String getDisplayValue(Object object) {
                return typeMap.get(object);
            }

            public String getIdValue(Object object, int index) {
                return object.toString();
            }
        };

        this.setChoices(dropDownModel);
        this.setChoiceRenderer(choiceRenderer);
        this.setOutputMarkupId(true);
    }

    @Override
    protected String getNullKeyDisplayValue() {
        return "请选择";
    }
}
