package com.daren.equipment.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import com.daren.core.web.wicket.navigator.CustomerPagingNavigator;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.entities.EquipmentBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EquipmentPage extends BasePanel {

    @Inject
    private IEquipmentBeanService equipmentBeanService;

    EquipmentDataProvider provider = new EquipmentDataProvider();
    
    

    public EquipmentPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<EquipmentBean> listView = new DataView<EquipmentBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<EquipmentBean> item) {
                final EquipmentBean equipmentBean = item.getModelObject();
                item.add(getToCreatePageLink("check_name", equipmentBean).add(new Label("name", equipmentBean.getName()))
                );
                item.add(new Label("equipmentType", equipmentBean.getEquipmentType()));
                item.add(new Label("unitName", equipmentBean.getUnitName()));
                item.add(new Label("registrationType", equipmentBean.getRegistrationType()));
                item.add(new Label("principal", equipmentBean.getPrincipal()));
                item.add(new Label("mobilePhone", equipmentBean.getMobilePhone()));
                item.add(new Label("amount", equipmentBean.getAmount()));


                AjaxLink alink = new AjaxLink("del") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('确定要删除么?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        equipmentBeanService.deleteEntity(equipmentBean.getId());
                        target.add(table);
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);

        createQuery(table, provider,id,wmc);
    }

    private AjaxLink getToCreatePageLink(String wicketId, final EquipmentBean equipmentBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(equipmentBean,target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(EquipmentBean equipmentBean,AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final EquipmentDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<EquipmentBean> equipmentBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new EquipmentBean()));
        TextField textField = new TextField("name");

        equipmentBeanForm.add(textField.setOutputMarkupId(true));
        equipmentBeanForm.add(getToCreatePageLink("create", null));
        add(equipmentBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", equipmentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EquipmentBean equipmentBean = (EquipmentBean) form.getModelObject();
                provider.setEquipmentBean(equipmentBean);
                target.add(table);
            }
        };
        equipmentBeanForm.add(findButton);
    }


    class EquipmentDataProvider extends ListDataProvider<EquipmentBean> {
        private EquipmentBean equipmentBean = null;

        public void setEquipmentBean(EquipmentBean equipmentBean) {
            this.equipmentBean = equipmentBean;
        }

        @Override
        protected List<EquipmentBean> getData() {
            if (equipmentBean == null || null == equipmentBean.getName() || "".equals(equipmentBean.getName().trim()))
                return equipmentBeanService.getAllEntity();
            else {
                return equipmentBeanService.getAllEntity();
            }
        }
    }
}