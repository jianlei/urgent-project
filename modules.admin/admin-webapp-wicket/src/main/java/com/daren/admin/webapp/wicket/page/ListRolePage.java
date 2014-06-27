package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.core.web.wicket.BasePage;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：角色列表页面
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:46
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ListRolePage extends BasePage {
    //注入角色业务服务
    @Named
    @Inject
    @Reference(id = "roleCodeBeanService", serviceInterface = IRoleBeanService.class)
    private IRoleBeanService roleBeanService;


    public ListRolePage() {
        /*try {
            roleCodeBeanService = (IRoleBeanService) JNDIHelper.getJNDIServiceForName(IRoleBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        List<RoleBeanImpl> roleCodeList = roleBeanService.getAllEntity();
        ListDataProvider<RoleBeanImpl> listDataProvider = new ListDataProvider<RoleBeanImpl>(roleCodeList);
        add(new DataView<RoleBeanImpl>("fileRows", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<RoleBeanImpl> item) {
                final RoleBeanImpl roleBean;
                roleBean = item.getModelObject();

                item.add(new Label("name", roleBean.getName()));
                item.add(new Label("permissions", roleBean.getRemark()));
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

    private List<Role> AllEntity() {
        List<Role> roleList = new ArrayList<Role>();
        Role role = new Role();

        role.setName("系统管理员");
        role.setPermissions("全部；");

        Role role2 = new Role();

        role2.setName("销售顾问");
        role2.setPermissions("业务管理；客户管理；");

        roleList.add(role);
        roleList.add(role2);
        return roleList;
    }

    class Role implements Serializable {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        String permissions;
    }
}
