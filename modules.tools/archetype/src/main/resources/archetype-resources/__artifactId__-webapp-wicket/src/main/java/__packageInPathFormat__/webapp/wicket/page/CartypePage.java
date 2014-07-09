package ${package}.webapp.wicket.page;


import com.daren.core.web.wicket.BasePage;
import com.daren.platform.couchdb.api.ICarInfoService;
import com.daren.platform.couchdb.model.CarInfo;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.time.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Cartype: sunlf
 * Date: 14-3-13
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class CartypePage extends BasePage {

    @Named
    @Inject
    @Reference(id = "carInfoService", serviceInterface = ICarInfoService.class)
    private ICarInfoService carInfoService;

    public CartypePage() {


        final Form<Cartype> form = new Form<Cartype>("add_cartype_form", new CompoundPropertyModel<Cartype>(new Cartype()));
        add(form);
        AjaxFormValidatingBehavior.addToAllFormComponents(form, "keydown", Duration.ONE_SECOND);

        TextField formName = new TextField("name");
        TextField formProductName = new TextField("productName");
        TextField formPrice = new TextField("price");

        form.add(formName);
        form.add(formProductName);
        form.add(formPrice);

        form.add(new AjaxButton("submit_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> submitForm) {
                Cartype cartype = (Cartype) getParent().getDefaultModelObject();
                if (cartype.getId() == null) {
                    CarInfo carInfo = new CarInfo();
                    carInfo.setName(cartype.getName());
                    carInfo.setPrice(cartype.getPrice());
                    carInfoService.addCarInfo(carInfo);
                } else {
                    CarInfo carInfo = carInfoService.findCarInfo(cartype.getId());
                    carInfo.setName(cartype.getName());
                    carInfo.setPrice(cartype.getPrice());
                    carInfoService.updateCarInfo(carInfo);
                }
//                salesmanBeanService.saveEntity(filledSalesmanBean);
                setResponsePage(CartypePage.class);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                System.out.println("onError");
            }
        });

        add(new Link("reset_from_1") {
            @Override
            public void onClick() {
                form.setModelObject(new Cartype());
            }
        });

        form.add(new Link("reset_from_2") {
            @Override
            public void onClick() {
                form.setModelObject(new Cartype());
            }
        });


        List<Cartype> distributorList = loadCartype();
        ListDataProvider<Cartype> listDataProvider = new ListDataProvider<Cartype>(distributorList);
        add(new DataView<Cartype>("cartypeRow", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Cartype> item) {
                final Cartype distributor;
                distributor = item.getModelObject();

                item.add(new Label("nameData", distributor.getName()));
                item.add(new Label("productNameData", distributor.getProductName()));
                item.add(new Label("createDate", distributor.getCreateDate()));
                item.add(new Label("priceData", distributor.getPrice()));
                item.add(new Label("version", distributor.getVersion()));
                item.add(new Label("state", distributor.getState()));
                item.add(new AjaxLink("editParameter") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        setResponsePage(CartypeParameters.class);
                    }
                });
                item.add(new AjaxLink("editConfiguration") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        setResponsePage(CartypeConfiguration.class);
                    }
                });
                item.add(new AjaxLink("editResources") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        setResponsePage(CartypeResources.class);
                    }
                });
                item.add(new Link("edit") {
                    @Override
                    public void onClick() {
                        Cartype cartype = (Cartype) getParent().getDefaultModelObject();
                        form.setModelObject(getCartype(cartype.getId()));
                    }
                });
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
                        Cartype cartype = (Cartype) getParent().getDefaultModelObject();
                        carInfoService.deleteCarInfo(cartype.getId());
                        setResponsePage(CartypePage.class);
                    }
                });
                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        });


    }

    public List<Cartype> loadCartype() {
        List<Cartype> userList = new ArrayList<Cartype>();
        List<CarInfo> carInfoList = carInfoService.getAllCarInfo();
        for (int i = 0; i < carInfoList.size(); i++) {
            CarInfo carInfo = carInfoList.get(i);
            Cartype cartype = new Cartype();
            cartype.setName(carInfo.getName());
            cartype.setPrice(carInfo.getPrice());
            cartype.setProductName("红旗H7");
            cartype.setId(carInfo.getId().toString());
            cartype.setCreateDate("2013-12-20");
            userList.add(cartype);
        }
        return userList;
    }


    public Cartype getCartype(String id) {
        Cartype cartype = new Cartype();
        List<Cartype> userList = new ArrayList<Cartype>();
        List<CarInfo> carInfoList = carInfoService.getAllCarInfo();
        for (int i = 0; i < carInfoList.size(); i++) {
            CarInfo carInfo = carInfoList.get(i);
            if (id.equals(carInfo.getId())) {
                cartype.setName(carInfo.getName());
                cartype.setPrice(carInfo.getPrice());
                cartype.setProductName("红旗H7");
                cartype.setId(carInfo.getId());
                cartype.setVersion("1." + i);
                cartype.setState(i / 3 > 0 ? "已发布" : "未发布");
            }
        }
        return cartype;
    }
}
