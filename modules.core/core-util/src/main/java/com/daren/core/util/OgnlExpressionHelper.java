package com.daren.core.util;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.util.Map;


/**
 * <b>
 * Title:</b> 主题
 * <br><b>
 * Description:</b> 类功能描述
 * <br><b>
 * Copyright:</b>  Copyright (c) 2013
 * <br><b>
 * Company:</b> 长春丽明科技开发有限公司
 * User: dell
 * Date: 13-10-15
 * Time: 下午3:17
 *
 * @version 1.0
 */
public class OgnlExpressionHelper {
    /**
     * 获得表达式的返回值
     *
     * @param expressionString 传入的表达式
     * @param valueMap         传入的表达式的值，
     *                         key对应表达式里面的变量，value对应变量的值
     * @return 计算结果
     */
    public Object returnValue(String expressionString, Map valueMap) {
        OgnlContext context = new OgnlContext();
        Object expression;
        try {
            expression = Ognl.parseExpression(expressionString);
            return Ognl.getValue(expression, context, valueMap);
        } catch (OgnlException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
