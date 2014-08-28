package com.daren.core.util.jpa.page;

import java.io.Serializable;

/**
 * User: tangtao
 * Date: 13-9-12
 * Time: 下午11:04
 */
public class Condition implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String field;

    public String getField() {
        return field;
    }

}
