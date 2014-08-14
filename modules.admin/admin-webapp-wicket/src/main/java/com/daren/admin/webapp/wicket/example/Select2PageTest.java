package com.daren.admin.webapp.wicket.example;

import com.daren.admin.webapp.wicket.data.Country;
import com.daren.core.web.wicket.BasePanel;
import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.Select2Choice;
import com.vaynberg.wicket.select2.TextChoiceProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    测试select2
 * 创建人:    sunlf
 * 创建时间:  2014/8/14 18:21
 * 修改人:    sunlf
 * 修改时间:  2014/8/14 18:21
 * 修改备注:  [说明本次修改内容]
 */
public class Select2PageTest extends BasePanel {
    private Country country = Country.US;
//    private List<Country> countries = new ArrayList<Country>(Arrays.asList(new Country[]{Country.US, Country.CA}));

    public Select2PageTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        add(new Label("country", new PropertyModel<Object>(this, "country")));

        Form<?> form = new Form<Void>("single");
        add(form);

        Select2Choice<Country> country = new Select2Choice<Country>("country", new PropertyModel<Country>(this,
                "country"), new CountriesProvider());
        country.getSettings().setMinimumInputLength(1);
        form.add(country);
    }

    private static List<Country> queryMatches(String term, int page, int pageSize) {

        List<Country> result = new ArrayList<Country>();

        term = term.toUpperCase();

        final int offset = page * pageSize;

        int matched = 0;
        for (Country country : Country.values()) {
            if (result.size() == pageSize) {
                break;
            }

            if (country.getDisplayName().toUpperCase().contains(term)) {
                matched++;
                if (matched > offset) {
                    result.add(country);
                }
            }
        }
        return result;
    }

    public class CountriesProvider extends TextChoiceProvider<Country> {

        @Override
        protected String getDisplayText(Country choice) {
            return choice.getDisplayName();
        }

        @Override
        protected Object getId(Country choice) {
            return choice.name();
        }

        @Override
        public void query(String term, int page, Response<Country> response) {
            response.addAll(queryMatches(term, page, 10));
            response.setHasMore(response.size() == 10);

        }

        @Override
        public Collection<Country> toChoices(Collection<String> ids) {
            ArrayList<Country> countries = new ArrayList<Country>();
            for (String id : ids) {
                countries.add(Country.valueOf(id));
            }
            return countries;
        }

    }
}
