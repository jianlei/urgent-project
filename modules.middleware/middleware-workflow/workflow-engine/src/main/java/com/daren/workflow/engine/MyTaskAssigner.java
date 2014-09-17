package com.daren.workflow.engine;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class MyTaskAssigner implements TaskListener, JavaDelegate {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.print(delegateTask.getName());
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }
}
