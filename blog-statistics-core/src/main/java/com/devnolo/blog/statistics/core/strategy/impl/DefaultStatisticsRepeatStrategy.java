package com.devnolo.blog.statistics.core.strategy.impl;

import com.devnolo.blog.statistics.core.strategy.StatisticsRepeatStrategy;
import com.devnolo.blog.statistics.core.to.BlogStatisticsBrowseTO;

/**
 * 默认的统计重复校验策略
 *
 * @author qingsong
 * @date 2022年11月26日
 */
public class DefaultStatisticsRepeatStrategy implements StatisticsRepeatStrategy {

    @Override
    public boolean checkStatisticsRepeat(BlogStatisticsBrowseTO browse) {
        return false;
    }
}
