package com.suidao.config;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.suidao.utils.DatePatternUtil;

/**
 * java.util.Date类型反序列化配置
 *

 */
public class DateDeserializer extends StdDeserializer<Date> {

    /**
     * 构造方法
     *
     */
    public DateDeserializer() {
        this(null);
    }

    /**
     * 构造方法
     *
     * @param vc Class
     */
    public DateDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
     *      com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return DatePatternUtil.getPatternDate(p.getValueAsString());
    }
}