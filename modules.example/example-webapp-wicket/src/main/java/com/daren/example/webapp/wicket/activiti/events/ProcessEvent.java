package com.daren.example.webapp.wicket.activiti.events;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/15
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProcessEvent implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.print(execution.getCurrentActivityName());
    }
}
