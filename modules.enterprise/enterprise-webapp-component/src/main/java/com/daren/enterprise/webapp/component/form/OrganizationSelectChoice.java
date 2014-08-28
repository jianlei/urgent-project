package com.daren.enterprise.webapp.component.form;

import com.daren.core.api.persistence.PersistentEntity;
import com.daren.core.web.component.json.JsonBuilder;
import com.daren.enterprise.entities.OrganizationBean;
import com.daren.enterprise.webapp.component.data.OrganizationProvider;
import com.vaynberg.wicket.select2.AbstractSelect2Choice;
import com.vaynberg.wicket.select2.ChoiceProvider;
import com.vaynberg.wicket.select2.JQuery;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * @类描述：监管机构下拉选择
 * @创建人：xukexin
 * @创建时间：2014/8/27 18:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public abstract class OrganizationSelectChoice <M extends PersistentEntity>
        extends AbstractSelect2Choice<OrganizationBean,M> {

    public OrganizationSelectChoice(String id, IModel<M> model) {
        this(id, model, new OrganizationProvider());
        getSettings().setMinimumInputLength(2);//设置最小输入字符为2个
    }

    public OrganizationSelectChoice(String id, IModel<M> model, ChoiceProvider<OrganizationBean> provider) {
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
                toJson(value,selection);
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
    public abstract void setId(M bean,String input);
}