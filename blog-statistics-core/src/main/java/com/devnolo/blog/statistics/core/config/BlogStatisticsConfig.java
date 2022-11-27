package com.devnolo.blog.statistics.core.config;

import com.devnolo.blog.statistics.core.repository.BlogStatisticsRepository;
import com.devnolo.blog.statistics.core.repository.impl.DefaultBlogStatisticsRepository;
import com.devnolo.blog.statistics.core.strategy.AuthenticationStrategy;
import com.devnolo.blog.statistics.core.strategy.StatisticsRepeatStrategy;
import com.devnolo.blog.statistics.core.strategy.impl.DefaultAuthenticationStrategy;
import com.devnolo.blog.statistics.core.strategy.impl.DefaultStatisticsRepeatStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * 配置文件：扫描整个项目
 * @author qingsong
 * @date 2022年11月26日
 */
@Configuration
@EnableScheduling
@ComponentScan("com.devnolo.blog.statistics.core")
public class BlogStatisticsConfig {
    @Resource
    private BlogStatisticsConfiguration blogStatisticsConfiguration;

    @Bean
    @ConditionalOnMissingBean(StatisticsRepeatStrategy.class)
    public BlogStatisticsRepository blogStatisticsRepository() {
        return new DefaultBlogStatisticsRepository();
    }

    @Bean
    @ConditionalOnMissingBean(StatisticsRepeatStrategy.class)
    public StatisticsRepeatStrategy statisticsRepeatStrategy() {
        return new DefaultStatisticsRepeatStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationStrategy.class)
    public AuthenticationStrategy authenticationStrategy() {
        DefaultAuthenticationStrategy authenticationStrategy = new DefaultAuthenticationStrategy();
        authenticationStrategy.setBlogStatisticsConfiguration(blogStatisticsConfiguration);
        return authenticationStrategy;
    }
}
