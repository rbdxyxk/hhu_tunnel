package com.suidao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class JacksonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public JacksonObjectMapper() {
        super();
        // 忽略 null值的json字符串
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        // 将时间戳转换为日期格式
//        this.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule()
                //serializer将java对象转换为json
                .addDeserializer(Date.class, new DateDeserializer())
                //将BigInterger类型转为字符串
                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                //将Long类型转为字符串
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(localDateTimeFormatter));

        this.registerModule(simpleModule);
    }
}
