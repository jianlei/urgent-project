package com.daren.monitor.webapp.wicket.page;

import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.monitor.api.biz.ISingleMonitorBeanService;
import com.daren.monitor.entities.SingleMonitorBean;
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


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SingleMonitorListPage extends BasePanel {

    MajorSingleMonitorSourceDataProvider provider = new MajorSingleMonitorSourceDataProvider();

    @Inject
    private ISingleMonitorBeanService singleMonitorBeanService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public SingleMonitorListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
        final WebMarkupContainer table = new WebMarkupContainer("table");

        add(table.setOutputMarkupId(true));

        DataView<SingleMonitorBean> listView = new DataView<SingleMonitorBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<SingleMonitorBean> item) {
                final SingleMonitorBean singleMonitorBean = item.getModelObject();
                item.add(new Label("name", singleMonitorBean.getName()));
                item.add(new Label("streamingMediaIP", singleMonitorBean.getStreamingMediaIP()));
                item.add(new Label("streamingMediaPort", singleMonitorBean.getStreamingMediaPort()));
                item.add(new Label("channel", singleMonitorBean.getChannel()));
                EnterpriseBean enterpriseBean = enterpriseBeanService.getByQyid(singleMonitorBean.getAffiliation());
                if (null != enterpriseBean) {
                    item.add(new Label("affiliation", enterpriseBean.getQymc()));//企业名称
                } else {
                    item.add(new Label("affiliation", "未绑定"));//企业名称
                }

                /*item.add(new Label("admin", singleMonitorBean.getAdmin()));//企业名称
                item.add(new Label("password", singleMonitorBean.getPassword()));//企业名称*/
                item.add(getToCreatePageLink("check_name", singleMonitorBean));

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
                        singleMonitorBeanService.deleteEntity(singleMonitorBean.getId());
                        target.add(table);
                    }
                };
                item.add(alink.setOutputMarkupId(true));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final SingleMonitorBean singleMonitorBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(singleMonitorBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final SingleMonitorBean singleMonitorBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(singleMonitorBean, target);
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(SingleMonitorBean singleMonitorBean, AjaxRequestTarget target) {

    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final MajorSingleMonitorSourceDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<SingleMonitorBean> majorSingleMonitorSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new SingleMonitorBean()));
        TextField textField = new TextField("name");

        majorSingleMonitorSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorSingleMonitorSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorSingleMonitorSourceBeanForm.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", majorSingleMonitorSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                SingleMonitorBean singleMonitorBean = (SingleMonitorBean) form.getModelObject();
                provider.setSingleMonitorBean(singleMonitorBean);
                target.add(table);
            }
        };
        majorSingleMonitorSourceBeanForm.add(findButton);
    }


    class MajorSingleMonitorSourceDataProvider extends ListDataProvider<SingleMonitorBean> {
        private SingleMonitorBean singleMonitorBean = null;

        public void setSingleMonitorBean(SingleMonitorBean singleMonitorBean) {
            this.singleMonitorBean = singleMonitorBean;
        }

        @Override
        protected List<SingleMonitorBean> getData() {
            if (singleMonitorBean == null || null == singleMonitorBean.getName() || "".equals(singleMonitorBean.getName().trim()))
                return singleMonitorBeanService.getAllEntity();
            else {
                return singleMonitorBeanService.querySingleMonitor(singleMonitorBean);
            }
        }
    }
}
