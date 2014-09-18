package com.daren.competency.core;

import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.api.dao.ICompetencyBeanDao;
import com.daren.competency.entities.CompetencyBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * Created by Administrator on 2014/9/10.
 */
public class CompetencyServiceImpl extends GenericBizServiceImpl implements ICompetencyService {
    ICompetencyBeanDao competencyBeanDao;

    public void setCompetencyBeanDao(ICompetencyBeanDao competencyBeanDao) {
        this.competencyBeanDao = competencyBeanDao;
        super.init(competencyBeanDao, CompetencyBean.class.getName());
    }

    @Override
    public List<CompetencyBean> getCompetencyByPhone(String phone) {
        return competencyBeanDao.find("select c from CompetencyBean c where c.phone=?1", phone);
    }
}
