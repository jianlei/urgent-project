package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

import javax.persistence.Query;
import java.util.List;

/**
 * @类描述：角色dao实现类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午6:32
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RoleBeanDaoOpenjpa extends GenericOpenJpaDao<RoleBeanImpl, Long> implements IRoleBeanDao {
    @Override
    public List<String> getRoleNameList() {
        /*List<RoleBeanImpl> roleBeanList=super.getAll(RoleBeanImpl.class.getName());
        if(roleBeanList!=null){
            roleNameList=new ArrayList<String>();
            for(RoleBeanImpl roleBean:roleBeanList){
                roleNameList.add(roleBean.getName());
            }
        }*/
        final Query query = entityManager.createQuery("select c.name from RoleBeanImpl c ");
        final List<String> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public RoleBeanImpl getRole(String roleName) {
        RoleBeanImpl roleBean = this.findUnique("select u from RoleBeanImpl u where u.name=?1", roleName);
        return roleBean;
    }
}
