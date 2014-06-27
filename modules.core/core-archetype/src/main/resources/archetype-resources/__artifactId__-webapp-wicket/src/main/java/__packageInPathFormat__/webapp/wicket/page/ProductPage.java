package ${package}.webapp.wicket.page;

import com.daren.core.web.wicket.BasePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：产品页面
 * @创建人：sunlf
 * @创建时间：2014-03-29 下午12:00
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ProductPage extends BasePage {
    public ProductPage() {
        List<Cartype> distributorList = loadCartype();//distributorBeanService.getAllEntity();
        ListDataProvider<Cartype> listDataProvider = new ListDataProvider<Cartype>(distributorList);
        add(new DataView<Cartype>("productRow", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Cartype> item) {
                final Cartype distributor;
                distributor = item.getModelObject();

                item.add(new Label("productName", distributor.getProductName()));
                item.add(new Label("brandName", distributor.getBrandName()));
                item.add(new Label("createDate", distributor.getCreateDate()));
                item.add(new Label("version", distributor.getVersion()));
                item.add(new Label("state", distributor.getState()));

                item.add(new AjaxLink("edit") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                    }
                });
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
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
        Cartype user5 = new Cartype();
        user5.setProductName("H7");
        user5.setBrandName("红旗");
        user5.setCreateDate("2013-12-23");
        user5.setVersion("1.3");
        user5.setState("预发布");
        userList.add(user5);
        return userList;
    }
}