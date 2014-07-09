package ${package}.api.dao;

import ${package}.api.persistence.IUserBean;
import com.daren.core.api.persistence.IGenericDao;

import java.util.List;

/**
 * Created by dell on 14-1-16.
 */

public interface IUserBeanDao extends IGenericDao<IUserBean, Long> {
    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    List<IUserBean> getUserList();

    /**
     * Saves a user's information.
     *
     * @param user the object to be saved
     * @return the persisted User object
     */
    IUserBean saveUser(IUserBean user);

    /**
     * 删除一个用户
     *
     * @param userId 用户ID
     */
    void removeUser(Long userId);

    IUserBean getUser(Long userId);

    /**
     * 通过用户名获得用户对象
     *
     * @param username 用户名
     * @return
     */
    public IUserBean getUser(String username);
}
