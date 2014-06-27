package com.daren.admin.webapp.wicket.data;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.util.JNDIHelper;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：用户数据提供类
 * @创建人：sunlf
 * @创建时间：2014-04-28 下午7:43
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class UserProvider extends ListDataProvider<UserBeanImpl> {
    /*@Named
    @Inject
    @Reference(id = "userLoginService", serviceInterface = IUserBeanService.class)*/
    private IUserBeanService userBeanService;

    private List<UserBeanImpl> userBeanList = new ArrayList<UserBeanImpl>();

    public UserProvider() {


    }

    public void updateData() {
        userBeanList = userBeanService.getAllEntity();
    }

    @Override
    protected List<UserBeanImpl> getData() {
        try {
            userBeanService = (IUserBeanService) JNDIHelper.getJNDIServiceForName(IUserBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userBeanList = userBeanService.getAllEntity();
        return userBeanList;
    }

    /*@Override
    public Iterator<? extends UserBeanImpl> iterator(long first, long count) {

        return userBeanList.iterator();
    }*/

    /*@Override
    public long size() {
        return userBeanList.size();
    }*/

    /*@Override
    public IModel<UserBeanImpl> model(final UserBeanImpl object) {
        return new AbstractReadOnlyModel<UserBeanImpl>() {
            @Override
            public UserBeanImpl getObject() {
                return object;
            }
        };
    }*/

    @Override
    public void detach() {

    }
}
