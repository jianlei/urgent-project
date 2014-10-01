package com.daren.enterprise.webapp.component.form;

import com.daren.core.api.persistence.PersistentEntity;
import com.daren.core.web.component.json.JsonBuilder;
import com.daren.core.web.component.select2.AbstractSelect2Choice;
import com.daren.core.web.component.select2.ChoiceProvider;
import com.daren.core.web.component.select2.JQuery;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.data.EnterpriseProvider;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * 项目名称:  urgent-project
 * 类描述:    企业搜索下拉组件类
 * 创建人:    sunlf
 * 创建时间:  2014/8/15 11:10
 * 修改人:    sunlf
 * 修改时间:  2014/8/15 11:10
 * 修改备注:  [说明本次修改内容]
 */
public abstract class EnterpriseSelect3Choice<M extends PersistentEntity> extends AbstractSelect2Choice<EnterpriseBean, M> {


    public EnterpriseSelect3Choice(String id, IModel<M> model) {
        this(id, model, new EnterpriseProvider());
        getSettings().setMinimumInputLength(2);//设置最小输入字符为2个
    }

    public EnterpriseSelect3Choice(String id, IModel<M> model, ChoiceProvider<EnterpriseBean> provider) {
        super(id, model, provider);
    }


    @Override
    protected void convertInput() {

        String input = getWebRequest().getRequestParameters().getParameterValue(getInputName()).toString();
        if (Strings.isEmpty(input)) {
            setConvertedInput(null);
        } else {
            M value = (M) getModelObject();
            setId(value, input);
            setConvertedInput(value);
        }
    }

    @Override
    protected void renderInitializationScript(IHeaderResponse response) {

        M value;
        if (getWebRequest().getRequestParameters().getParameterNames().contains(getInputName())) {
            convertInput();
            value = (M) getConvertedInput();
        } else {
            value = (M) getModelObject();
        }

        if (value != null) {

            JsonBuilder selection = new JsonBuilder();

            try {
                selection.object();
                toJson(value, selection);
                selection.endObject();
            } catch (JSONException e) {
                throw new RuntimeException("Error converting model object to Json", e);
            }
            response.render(OnDomReadyHeaderItem.forScript(JQuery.execute("$('#%s').select2('data', %s);",
                    getJquerySafeMarkupId(), selection.toJson())));
        }
    }


    private void toJson(M choice, JSONWriter writer) throws JSONException {
        writer.key("id").value(getId(choice)).key("text").value(getDisplayText(choice));
    }

    public abstract String getId(M choice);

    public abstract String getDisplayText(M choice);

    public abstract void setId(M bean, String input);


}
