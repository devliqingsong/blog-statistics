package com.devnolo.blog.statistics.core.strategy.impl;

import com.devnolo.blog.statistics.core.config.BlogStatisticsConfiguration;
import com.devnolo.blog.statistics.core.strategy.AuthenticationStrategy;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 默认的授权校验策略
 *
 * @author qingsong
 * @date 2022年11月26日
 */
public class DefaultAuthenticationStrategy implements AuthenticationStrategy {

    @Resource
    private BlogStatisticsConfiguration blogStatisticsConfiguration;

    public void setBlogStatisticsConfiguration(BlogStatisticsConfiguration blogStatisticsConfiguration) {
        this.blogStatisticsConfiguration = blogStatisticsConfiguration;
    }

    @Override
    public boolean authentication(String key, String secret) {
        if (!StringUtils.hasText(secret) || !StringUtils.hasText(secret))
            return false;

        return Optional.ofNullable(blogStatisticsConfiguration.getSecret(key)).map(item -> item.equals(secret)).orElse(false);
    }
}
