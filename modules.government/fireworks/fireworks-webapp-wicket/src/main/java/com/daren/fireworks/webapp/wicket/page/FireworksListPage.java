package com.daren.fireworks.webapp.wicket.page;

import com.daren.core.web.component.government.WindowGovernmentPage;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.component.office.WindowOfficePage;
import com.daren.core.web.wicket.BasePanel;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.entities.FireworksBean;
import com.daren.workflow.webapp.wicket.page.WorkflowBasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.FormService;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    FireworksDataProvider provider = new FireworksDataProvider();

    @Inject
    private IFireworksService fireworksService;

    public FireworksListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        //初始化dialogWrapper
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog", ""));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        DataView<FireworksBean> listView = new DataView<FireworksBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<FireworksBean> item) {
                final FireworksBean fireworksBean = item.getModelObject();
                item.add(new Label("name", fireworksBean.getName()));
                item.add(new Label("head", fireworksBean.getHead()));
                item.add(new Label("phone", fireworksBean.getPhone()));
                item.add(new Label("address", fireworksBean.getAddress()));
                item.add(new Label("economicsType", fireworksBean.getEconomicsType()));
                item.add(new Label("storageAddress", fireworksBean.getStorageAddress()));
                item.add(new Label("scope", fireworksBean.getScope()));
                item.add(getToCreatePageLink("check_name", fireworksBean));
                item.add(getToUploadPageLink("upload", fireworksBean));

            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final FireworksBean fireworksBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(fireworksBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final FireworksBean fireworksBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(fireworksBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final FireworksBean fireworksBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", fireworksBean);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(FireworksBean fireworksBean, AjaxRequestTarget target) {}

    private void createDialog(AjaxRequestTarget target, final String title, FireworksBean fireworksBean) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", title, fireworksBean.getId(), "upload", "fireworks") {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final FireworksDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<FireworksBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new FireworksBean()));
        TextField textField = new TextField("name");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                FireworksBean fireworksBean = (FireworksBean) form.getModelObject();
                provider.setFireworksBean(fireworksBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }


    class FireworksDataProvider extends ListDataProvider<FireworksBean> {
        private FireworksBean fireworksBean = null;
        public void setFireworksBean(FireworksBean fireworksBean) {
            this.fireworksBean = fireworksBean;
        }
        @Override
        protected List<FireworksBean> getData() {
            if (fireworksBean == null || null == fireworksBean.getName() || "".equals(fireworksBean.getName().trim()))
                return fireworksService.getAllEntity();
            else {
                return fireworksService.getAllEntity();
            }
        }
    }
}