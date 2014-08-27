package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.util.DateUtil;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AccidentPage extends BasePanel {

    AccidentDataProvider provider = new AccidentDataProvider();
    @Inject
    private IAccidentBeanService accidentBeanService;

    @Inject
    private IDictBeanService iDictBeanService;

    //列表回显示使用
    Map<String, String> accidentLevelMap;
    Map<String, String> accidentTypeMap;


    public AccidentPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        initDataView();
        accidentLevelMap = iDictBeanService.getDictMap(IDictConstService.ACCIDENT_LEVEL);
        accidentTypeMap = iDictBeanService.getDictMap(IDictConstService.ACCIDENT_TYPE);
    }

    /**
     * 处理查询页面
     *
     * @param table
     * @param
     */
    private Form createQuery(final WebMarkupContainer table, final AccidentDataProvider provider) {
        //处理查询
        Form<AccidentBean> myform = new Form<>("form", new CompoundPropertyModel<>(new AccidentBean()));
        TextField textField = new TextField("accidentTitle");
        myform.add(textField.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean userBean = (AccidentBean) form.getModelObject();
                provider.setAccidentBean(userBean);
                target.add(table);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                AccidentBean userBean = (AccidentBean) form.getModelObject();
                provider.setAccidentBean(userBean);
                target.add(table);
            }
        };

        AjaxButton addButton = new AjaxButton("add") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                addButtonOnClick(new AccidentBean(),target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                addButtonOnClick(new AccidentBean(), target);
            }
        };
        myform.add(addButton);
        myform.add(findButton);
        return myform;
    }

    private void initDataView() {
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        final DataView<AccidentBean> listView = new DataView<AccidentBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<AccidentBean> item) {
                final AccidentBean accidentBean = item.getModelObject();

                AjaxLink ajaxLink = new AjaxLink("accidentTitleLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        accidentTitleLinkOnClick(accidentBean,target);
                    }
                };
                item.add(new Label("accidentTitle", accidentBean.getAccidentTitle()));
                item.add(ajaxLink);
                item.add(new Label("accidentLevel", accidentLevelMap.get(accidentBean.getAccidentLevel())));
                item.add(new Label("accidentType", accidentTypeMap.get(accidentBean.getAccidentType())));
                item.add(new Label("accidentTime", DateUtil.convertDateToString(accidentBean.getAccidentTime(),DateUtil.longSdf) ));
                addDeleteLink(item, "delete", accidentBean, table);
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView) {
        };
        table.add(pagingNavigator);
        table.add(listView);
        add(createQuery(table, provider));
    }

    protected void accidentTitleLinkOnClick(AccidentBean accidentBean,AjaxRequestTarget target) {}

    protected void addButtonOnClick(AccidentBean accidentBean,AjaxRequestTarget target) {}

    private void addDeleteLink(Item<AccidentBean> item, String linkName, final AccidentBean accidentBean, final WebMarkupContainer table) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener listener = new AjaxCallListener();
                listener.onPrecondition("if(!confirm('确定要删除吗')){return false;}");
                attributes.getAjaxCallListeners().add(listener);
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                accidentBeanService.deleteEntity(accidentBean.getId());
                target.add(table);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    class AccidentDataProvider extends ListDataProvider<AccidentBean> {
        private AccidentBean accidentBean = null;

        public void setAccidentBean(AccidentBean accidentBean) {
            this.accidentBean = accidentBean;
        }

        @Override
        protected List<AccidentBean> getData() {
            if (accidentBean == null || null == accidentBean.getAccidentTitle() || "".equals(accidentBean.getAccidentTitle().trim()))
                return accidentBeanService.getAllEntity();
            else {
                return accidentBeanService.getAllEntity();
            }
        }
    }
}
