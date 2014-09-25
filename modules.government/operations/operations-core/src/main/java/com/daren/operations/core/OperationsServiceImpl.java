package com.daren.operations.core;

import com.daren.core.impl.biz.GenericBizServiceImpl;
import com.daren.operations.api.biz.IOperationsService;
import com.daren.operations.api.dao.IOperationsBeanDao;
import com.daren.operations.entities.OperationsBean;

import java.util.List;

/**
 * Created by Administrator on 2014/9/10.
 */
public class OperationsServiceImpl extends GenericBizServiceImpl implements IOperationsService {
    IOperationsBeanDao operationsBeanDao;

    public void setOperationsBeanDao(IOperationsBeanDao operationsBeanDao) {
        this.operationsBeanDao = operationsBeanDao;
        super.init(operationsBeanDao, OperationsBean.class.getName());
    }

    @Override
    public List<OperationsBean> getOperationsByLoginName(String loginName) {
        return operationsBeanDao.find("select o from OperationsBean o where o.loginName=?1", loginName);
    }
}
