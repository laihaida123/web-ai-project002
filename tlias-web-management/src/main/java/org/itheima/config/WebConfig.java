package org.itheima.config;

import org.itheima.interceptor.DemoInterceptor;
import org.itheima.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private DemoInterceptor demoInterceptor;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns("/login");//放行登录请求
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
