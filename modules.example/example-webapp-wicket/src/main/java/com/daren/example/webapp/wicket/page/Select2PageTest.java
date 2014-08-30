package com.daren.example.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.entities.OrganizationBean;
import com.daren.enterprise.webapp.component.form.EnterpriseSelect2Choice;
import com.daren.enterprise.webapp.component.form.OrganizationSelectChoice;
import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.TextChoiceProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
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
    public static final int PAGE_SIZE = 10;
    @Inject
    IUserBeanService userBeanService;

    private OrganizationBean organizationBean = new OrganizationBean();
    private List<UserBean> result = new ArrayList<UserBean>();

    public Select2PageTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        Form<?> form = new Form<Void>("single");
        add(form);
        EnterpriseSelect2Choice<EnterpriseBean> user = new EnterpriseSelect2Choice<EnterpriseBean>("country");
        user.getSettings().setMinimumInputLength(2);
        form.add(user);
        //监管机构
        String name = "organization";
        OrganizationSelectChoice<OrganizationBean> listSites = new OrganizationSelectChoice<OrganizationBean>(name, Model.of(organizationBean)) {
            @Override
            public void setId(OrganizationBean bean, String input) {
                bean.getJgdm();
            }

            @Override
            public String getId(OrganizationBean choice) {
                return choice.getJgdm();
            }

            @Override
            public String getDisplayText(OrganizationBean choice) {
                return organizationBean.getMc();//企业名称
            }
        };
        listSites.getSettings().setMinimumInputLength(2);
        //user.getSettings().setMinimumInputLength(2);
        form.add(listSites);
    }

    private List<UserBean> queryMatches(String term, int page, int pageSize) {
        result = userBeanService.queryUser(term, page, pageSize);
        return result;
    }

    public class UserProvider extends TextChoiceProvider<UserBean> {

        @Override
        protected String getDisplayText(UserBean choice) {
            return choice.getName();
        }

        @Override
        protected Object getId(UserBean choice) {
            return choice.getId();
        }

        @Override
        public void query(String term, int page, Response<UserBean> response) {
            response.addAll(queryMatches(term, page, PAGE_SIZE));
            response.setHasMore(response.size() == PAGE_SIZE);
        }

        @Override
        public Collection<UserBean> toChoices(Collection<String> ids) {
            ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
            for (String id : ids) {
                userBeans.add((UserBean) userBeanService.getEntity(new Long(id)));
            }
            return userBeans;
        }


    }
}
