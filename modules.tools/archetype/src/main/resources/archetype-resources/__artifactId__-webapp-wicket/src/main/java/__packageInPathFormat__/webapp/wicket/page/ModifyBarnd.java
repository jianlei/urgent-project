package ${package}.webapp.wicket.page;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Administrator on 14-3-25.
 */
public class ModifyBarnd extends Panel {
    TextField name;
    Cartype cartype = new Cartype();

    public ModifyBarnd(String id, Cartype myCartype) {
        super(id);
        cartype = myCartype;
        Form form = new Form<Cartype>("modify_brand_form", new CompoundPropertyModel<Cartype>(cartype)) {
            @Override
            protected void onSubmit() {
                cartype.setName(name.getValue());
//                distributorBeanService.addEntity(distributor);
                setResponsePage(BrandPage.class);
            }
        };
        add(form);

        name = new TextField("name");

        form.add(name);
    }
}
