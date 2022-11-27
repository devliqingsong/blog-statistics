package com.devnolo.blog.statistics.core.repository;

import com.devnolo.blog.statistics.core.to.BlogStatisticsBrowseTO;

import java.util.List;
import java.util.Map;

/**
 * 博客统计存储层
 * @author qingsong
 * @date 2022年11月26日
 */
public interface BlogStatisticsRepository {

    /**
     * 触发一次浏览
     */
    void statistics(BlogStatisticsBrowseTO browse);

    /**
     * 查询浏览数量
     */
    Map<String, Long> query(List<String> blogUrlArr);
}
