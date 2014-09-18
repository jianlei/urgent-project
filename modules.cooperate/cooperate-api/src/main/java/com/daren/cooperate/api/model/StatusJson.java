package com.daren.cooperate.api.model;

/**
 * @类描述：JSON返回POST结果类
 * @创建人：xukexin
 * @创建时间：2014/9/5 14:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class StatusJson {

    private int result = -1;
    private String message = "";

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
