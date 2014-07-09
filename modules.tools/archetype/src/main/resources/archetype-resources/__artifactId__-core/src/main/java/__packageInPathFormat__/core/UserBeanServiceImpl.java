package ${package}.core;

import ${package}.api.biz.IUserBeanService;
import ${package}.api.dao.IUserBeanDao;
import ${package}.api.persistence.IUserBean;
import ${package}.entities.UserBeanImpl;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public class UserBeanServiceImpl extends GenericBizServiceImpl implements IUserBeanService {
    private IUserBeanDao userBeanDao;

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
        super.init(userBeanDao, UserBeanImpl.class.getName());
    }

    public void init() {
        IUserBean user = new UserBeanImpl();
        user.setName("test");
        user.setPassword("hello");
        user = addUser(user);
        List<IUserBean> list = this.getAllUser();

        System.out.print("system is called " + list.size());
    }

    @Override

    public IUserBean addUser(IUserBean user) {
        return userBeanDao.saveUser(user);
    }

    @Override
    public void delUser(IUserBean user) {
        userBeanDao.removeUser(user.getId());
    }

    @Override
    public void saveUser(IUserBean user) {
        userBeanDao.saveUser(user);
    }

    public List<IUserBean> getAllUser() {
        return userBeanDao.getAll(UserBeanImpl.class.getName());
    }


}
