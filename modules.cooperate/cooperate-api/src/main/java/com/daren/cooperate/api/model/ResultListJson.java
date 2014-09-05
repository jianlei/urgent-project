package com.daren.cooperate.api.model;

import java.util.List;

/**
 * @类描述：JSON返回GET List结果类
 * @创建人：xukexin
 * @创建时间：2014/9/5 15:04
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ResultListJson {

    private StatusJson statusJson = new StatusJson();
    private List list;

    public StatusJson getStatusJson() {
        return statusJson;
    }

    public void setStatusJson(StatusJson statusJson) {
        this.statusJson = statusJson;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
