package com.daren.monitor.api.biz;


import com.daren.core.api.biz.IBizService;
import com.daren.monitor.entities.MonitorBean;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IMonitorBeanService extends IBizService {


    List<MonitorBean> queryMonitor(MonitorBean monitorBean);
}
