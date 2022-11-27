package com.devnolo.blog.statistics.core.strategy;

import com.devnolo.blog.statistics.core.to.BlogStatisticsBrowseTO;

/**
 * 校验重复统计（短时间多次页面刷新等）
 * @author qingsong
 * @date 2022年11月26日
 */
public interface StatisticsRepeatStrategy {

    /**
     * 判断是否为重复提交的请求
     * @param browse 统计浏览数据对象
     * @return 是否为重复提交
     */
    boolean checkStatisticsRepeat(BlogStatisticsBrowseTO browse);
}
