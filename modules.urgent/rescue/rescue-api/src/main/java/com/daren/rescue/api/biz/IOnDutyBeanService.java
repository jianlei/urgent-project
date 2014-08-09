package com.daren.rescue.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.rescue.entities.OnDutyBean;

import java.util.List;

/**
 * Created by Administrator on 2014/8/8.
 */
public interface IOnDutyBeanService extends IBizService {
    public void deleteOnDutyBeanById(long id);

    public List<OnDutyBean> getOnDutyBeanByAttach(long id);
}
