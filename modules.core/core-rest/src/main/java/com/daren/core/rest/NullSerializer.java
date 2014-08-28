package com.daren.core.rest;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * @类描述：用于json序列化,把null String 转换成空串
 * @创建人： sunlingfeng
 * @创建时间：2014/8/27
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class NullSerializer extends JsonSerializer<Object>
{
    public void serialize(Object value, JsonGenerator jgen,
                          SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        // any JSON value you want...
        jgen.writeString("");
    }}
