package com.daren.admin.api.biz;

import com.daren.admin.entities.PermissionBeanImpl;
import com.daren.core.api.biz.IBizService;

import java.util.List;

/**
 * @类描述：权限服务接口类
 * @创建人：sunlf
 * @创建时间：2014-05-14 上午11:19
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public interface IPermissionBeanService extends IBizService {
    /**
     * 获得root权限信息
     * @return
     */
    PermissionBeanImpl getRootPermission();

    List<PermissionBeanImpl> getChildPermission(PermissionBeanImpl permissionBean);
}
