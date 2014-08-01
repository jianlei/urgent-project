package com.daren.majorhazardsource.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.majorhazardsource.entities.MajorHazardSourceBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IMajorHazardSourceBeanService extends IBizService {

    List<MajorHazardSourceBean> queryMajorHazardSource(MajorHazardSourceBean enterpriseBean);
}
