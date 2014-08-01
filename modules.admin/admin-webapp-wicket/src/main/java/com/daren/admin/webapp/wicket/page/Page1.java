package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.navigator.CustomePagingNavigator;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/5 20:36
 * 修改人:    sunlf
 * 修改时间:  2014/7/5 20:36
 * 修改备注:  [说明本次修改内容]
 */
public class Page1 extends BasePanel {
    private static final int pageNum = 3;
    UserDataProvider provider = new UserDataProvider();
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;

    public Page1(String id, WebMarkupContainer wmc) {
        super(id, wmc);


        final WebMarkupContainer webMarkupContainer = wmc;
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));

        final DataView<UserBean> listView = new DataView<UserBean>("rows", provider, pageNum) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<UserBean> item) {
                final UserBean row = item.getModelObject();

                item.add(new Label("col1", row.getName()));
                item.add(new Label("col2", row.getLoginName()));
                item.add(new Label("col3", row.getPhone()));

                AjaxLink alink = new AjaxLink("delete") {
                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        AjaxCallListener listener = new AjaxCallListener();
                        listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
                        attributes.getAjaxCallListeners().add(listener);
                    }

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        /*target.appendJavaScript("window.open('http://www.cnn.com/2011/WORLD/africa/02/23"
                                + "/libya.protests/index.html?hpt="+row.getId()
     *//* you probably want to encode this first *//*+"')");*/
                        userBeanService.delUser(row);
                        target.add(table);
                    }
                };
//                alink.add(new Label("linklabel", "删除").setOutputMarkupId(true));
                item.add(alink.setOutputMarkupId(true));
            }
        };

        CustomePagingNavigator pagingNavigator = new CustomePagingNavigator("navigator", listView) {
        };
        table.add(pagingNavigator);
//        table.setVersioned(false);

        table.add(listView);
        createQuery(table, provider);
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final UserDataProvider provider) {
        //处理查询
        Form<UserBean> myform = new Form<>("form", new CompoundPropertyModel<>(new UserBean()));
        TextField textField = new TextField("name");

        myform.add(textField.setOutputMarkupId(true));
        add(myform.setOutputMarkupId(true));

        AjaxButton findButton = new AjaxButton("find", myform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                UserBean userBean = (UserBean) form.getModelObject();
                provider.setUserBean(userBean);
                target.add(table);
            }
        };
        myform.add(findButton);
    }

    class UserDataProvider extends ListDataProvider<UserBean> {
        private UserBean userBean = null;

        public void setUserBean(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected List<UserBean> getData() {
            if (userBean == null)
                return userBeanService.getAllUser();
            else {
                return userBeanService.queryUser(userBean);
            }
        }
    }
}


