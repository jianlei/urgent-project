package com.daren.admin.api.biz;

import com.daren.admin.entities.AboutBean;
import com.daren.core.api.biz.IBizService;

/**
 * 项目名称:  urgent-project
 * 类描述:    系统版本服务接口类
 * 创建人:    sunlf
 * 创建时间:  2014/8/7 16:01
 * 修改人:    sunlf
 * 修改时间:  2014/8/7 16:01
 * 修改备注:  [说明本次修改内容]
 */
public interface IAboutBeanService extends IBizService {
    /**
     * 获得系统版本信息
     *
     * @return
     */
    AboutBean getSysInfo();
}
