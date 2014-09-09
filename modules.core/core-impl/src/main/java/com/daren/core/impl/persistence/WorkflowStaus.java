package com.daren.core.impl.persistence;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public enum WorkflowStaus {
    ACTIVE("处理中"), FINISH("结束");

    private String statusCode;

    private WorkflowStaus(String s) {
        statusCode = s;
    }

    public String getStatusCode() {
        return statusCode;
    }

}
