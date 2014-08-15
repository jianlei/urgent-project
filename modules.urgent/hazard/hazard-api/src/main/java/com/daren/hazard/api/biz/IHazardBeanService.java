package com.daren.hazard.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.hazard.entities.HazardBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IHazardBeanService extends IBizService {

    List<HazardBean> queryHazardSource(HazardBean hazardBean);
}
