package com.daren.fireworks.core.biz;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.api.dao.IFireworksBeanDao;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksServiceImpl extends GenericBizServiceImpl implements IFireworksService {
    IFireworksBeanDao fireworksBeanDao;
}
