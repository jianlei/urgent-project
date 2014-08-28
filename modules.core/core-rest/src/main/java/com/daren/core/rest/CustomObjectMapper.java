package com.daren.core.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.StdSerializerProvider;

/**
 * @类描述：客户号Jackson的Mapper
 * @创建人： sunlingfeng
 * @创建时间：2014/8/27
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper(){
//        super();
        StdSerializerProvider sp = new StdSerializerProvider();
        sp.setNullValueSerializer(new NullSerializer());
        this.setSerializerProvider(sp);
//        this.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
//        this.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);

    }
}
