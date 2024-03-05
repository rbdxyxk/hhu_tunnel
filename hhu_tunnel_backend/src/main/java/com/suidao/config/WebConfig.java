package com.suidao.config;

import com.suidao.base.SysConf;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + SysConf.uploadurl);
        super.addResourceHandlers(registry);
    }
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // 设置对象转换器，底层使用jackson将java对象转换为json
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转换器添加到mvc框架的转换器集合中
        converters.add(0, converter);

    }
}
