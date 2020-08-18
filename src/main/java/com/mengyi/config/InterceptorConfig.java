package com.mengyi.config;

import com.mengyi.interceptor.AuthorizationInterceptor;
import com.mengyi.interceptor.UVStatisticInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:26
 * 配置拦截器
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(uvStatisticInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Bean
    public UVStatisticInterceptor uvStatisticInterceptor() {
        return new UVStatisticInterceptor();
    }
}
