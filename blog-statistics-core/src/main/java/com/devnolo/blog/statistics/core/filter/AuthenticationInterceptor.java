package com.devnolo.blog.statistics.core.filter;

import com.devnolo.blog.statistics.core.strategy.AuthenticationStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 * @author qingsong
 * @date 2022年11月26日
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /** 授权策略 */
    @Resource
    private AuthenticationStrategy authenticationStrategy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getHeader("Statistics-Key");
        String secret = request.getHeader("Statistics-Secret");
        return authenticationStrategy.authentication(key, secret);
    }
}
