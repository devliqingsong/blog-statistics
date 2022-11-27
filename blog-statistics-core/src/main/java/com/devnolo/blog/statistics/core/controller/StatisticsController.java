package com.devnolo.blog.statistics.core.controller;

import com.devnolo.blog.statistics.core.repository.BlogStatisticsRepository;
import com.devnolo.blog.statistics.core.strategy.StatisticsRepeatStrategy;
import com.devnolo.blog.statistics.core.to.BlogStatisticsBrowseTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 统计博客浏览量
 * @author qingsong
 * @date 2022年11月26日
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    /** 重复策略 */
    @Resource
    private StatisticsRepeatStrategy statisticsRepeatStrategy;
    /** 重复策略 */
    @Resource
    private BlogStatisticsRepository blogStatisticsRepository;

    /**
     * 触发一次浏览
     */
    @PostMapping("/browse")
    public void browse(@RequestBody BlogStatisticsBrowseTO browse) {
        if (statisticsRepeatStrategy.checkStatisticsRepeat(browse))
            return;
        blogStatisticsRepository.statistics(browse);
    }

    /**
     * 查询浏览数量
     */
    @PostMapping("/query")
    public Map<String, Long> query(@RequestBody String[] blogUrlArr) {
        return blogStatisticsRepository.query(List.of(blogUrlArr));
    }
}
