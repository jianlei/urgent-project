package com.daren.regulation.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.regulation.entities.RegulationBean;

import java.util.List;

/**
 * 法律法规
 * Created by dell on 14-1-17.
 */
public interface IRegulationBeanService extends IBizService {
    List<RegulationBean> query(RegulationBean dictBean);
}
