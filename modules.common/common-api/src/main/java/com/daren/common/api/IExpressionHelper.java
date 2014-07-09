package com.daren.common.api;


import java.util.Map;

/**
 * <b>
 * Title:</b> 主题
 * <br><b>
 * Description:</b> 类功能描述
 * <br><b>
 * Copyright:</b>  Copyright (c) 2013
 * <br><b>
 * Date: 13-10-15
 * Time: 下午3:17
 *
 * @version 1.0
 */
public interface IExpressionHelper {
    /**
     * 获得表达式的返回值
     *
     * @param expressionString 传入的表达式
     * @param valueMap         传入的表达式的值，
     *                         key对应表达式里面的变量，value对应变量的值
     * @return 计算结果
     */
    public Object returnValue(String expressionString, Map valueMap);

}
