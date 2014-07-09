package ${package}.api.biz;

import com.daren.core.api.biz.IBizService; ;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUserBeanService extends IBizService {
    public IUserBean addUser(IUserBean user);

    public void delUser(IUserBean user);

    public void saveUser(IUserBean user);

    public List<IUserBean> getAllUser();
}
