package com.devnolo.blog.statistics.core.strategy;

/**
 * 授权校验
 * @author qingsong
 * @date 2022年11月26日
 */
public interface AuthenticationStrategy {

    /**
     * 授权认证
     * @param key 授权 Key
     * @param secret 授权 secret
     * @return 是否可访问
     */
    boolean authentication(String key, String secret);
}
