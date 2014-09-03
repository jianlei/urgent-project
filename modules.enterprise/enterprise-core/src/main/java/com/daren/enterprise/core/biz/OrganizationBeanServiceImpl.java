package com.daren.enterprise.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.api.dao.IOrganizationBeanDao;
import com.daren.enterprise.core.util.OrgnizationConst;
import com.daren.enterprise.entities.OrganizationBean;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监管机构业务逻辑实现类
 * Created by xukexin on 2014/8/26.
 */
public class OrganizationBeanServiceImpl extends GenericBizServiceImpl implements IOrganizationBeanService {

    private IOrganizationBeanDao organizationBeanDao;

    public void setOrganizationBeanDao(IOrganizationBeanDao organizationBeanDao) {
        this.organizationBeanDao = organizationBeanDao;
        super.init(organizationBeanDao, OrganizationBean.class.getName());
    }

    @Override
    public List<OrganizationBean> queryOrganization(OrganizationBean organizationBean) {
        return organizationBeanDao.find("select a from OrganizationBean a where a.mc LIKE ?1", "%" + organizationBean.getMc() + "%");
    }

    @Override
    public Map<String, String> getAllBeansToHashMap() {
        Map<String, String> hashMap = new HashMap<>();
        List<OrganizationBean> list = organizationBeanDao.getAll(OrganizationBean.class.getName());
        if (null != list && list.size() > 0) {
            for (OrganizationBean bean : list) {
                hashMap.put(bean.getJgdm() + "", bean.getMc());
            }
        }
        return hashMap;
    }

    /**
     * 按名称模糊查询监管机构（带分页）
     * @param term
     * @param page
     * @param page_size
     * @return
     */
    @Override
    public List<OrganizationBean> findByName(String term, int page, int page_size) {
        return organizationBeanDao.findbyPage(
                "select t from OrganizationBean t where t.mc like '%"+term+"%'",page,page_size);
    }

    @Override
    public OrganizationBean getByJgdm(String id) {
      return   organizationBeanDao.findUnique("select a from OrganizationBean a where a.jgdm=?1", id);
    }

    /**
     * 根据机构代码获取下级部分的集合
     * @param jgdm
     * @param page
     * @param page_size
     * @return
     */
    @POST
    @Path("/getorglist")
    @Consumes("application/json;charset=utf-8")
    @Override
    public List<OrganizationBean> getOrgListByJgdm(String jgdm, Integer page, Integer page_size) {
        if(jgdm!=null){
            jgdm = OrgnizationConst.JILIN_JGDM;
        }
        if(page!=null){
            page = 0;
        }else{
            page = page-1;
        }
        if(page_size!=null){
            page_size = 15;
        }
        return organizationBeanDao.findbyPage("select t from OrganizationBean t " +
                "where t.zfbj='0' and t.sjjgdm = '"+jgdm+"'",page,page_size);
    }

}
