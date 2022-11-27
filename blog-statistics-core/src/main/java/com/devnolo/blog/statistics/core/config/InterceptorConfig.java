package com.devnolo.blog.statistics.core.config;

import com.devnolo.blog.statistics.core.filter.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置文件：注册拦截器（Interceptor）
 * @author qingsong
 * @date 2022年11月26日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册去重拦截器
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/statistics/**");         // 路径拦截
    }
}
