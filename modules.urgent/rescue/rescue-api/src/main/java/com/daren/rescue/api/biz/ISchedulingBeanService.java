package com.daren.rescue.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.rescue.entities.SchedulingBean;

import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
public interface ISchedulingBeanService extends IBizService {
    public void deleteSchedulingBeanById(long id);

    public List<SchedulingBean> getOnSchedulingBeanByAttach(long id);
}
