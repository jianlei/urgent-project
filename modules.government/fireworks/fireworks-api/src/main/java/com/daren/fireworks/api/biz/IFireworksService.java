package com.daren.fireworks.api.biz;

import com.daren.core.api.biz.IBizService;
import com.daren.fireworks.entities.FireworksBean;

import java.util.List;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IFireworksService extends IBizService {
    List<FireworksBean> query(FireworksBean bean);
    List<FireworksBean> getFireworksBeanByLoginName(String loginName);
}
