package com.devnolo.blog.statistics.core.repository.impl;

import com.devnolo.blog.statistics.core.config.BlogStatisticsConfiguration;
import com.devnolo.blog.statistics.core.repository.BlogStatisticsRepository;
import com.devnolo.blog.statistics.core.to.BlogStatisticsBrowseTO;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认博客统计存储层
 * @author qingsong
 * @date 2022年11月26日
 */
public class DefaultBlogStatisticsRepository implements BlogStatisticsRepository, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private BlogStatisticsConfiguration blogStatisticsConfiguration;

    // 后期优化：H2、Redis.....
    private volatile boolean initData = false;
    private final Object initDataFlag = new Object();
    private final Object saveDataFlag = new Object();
    private final Map<String, AtomicLong> dataMap = new ConcurrentHashMap<>();

    private static final String SPLIT_CHAR = "#:#";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String file = blogStatisticsConfiguration.getFile();
        if (!StringUtils.hasText(file)) {
            throw new Error("Param blog.statistics.file Is Empty");
        }
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData() throws IOException {
        if (!initData) {
            synchronized (initDataFlag) {
                if (!initData) {
                    // 根据配置文件指定的缓存文件加载启动时的数据
                    String file = blogStatisticsConfiguration.getFile();
                    // 读取文件，加载初始化、启动数据
                    try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (null == line) break;
                            if (!StringUtils.hasText(line)) continue;
                            String[] split = line.split(SPLIT_CHAR);
                            if (2 != split.length) continue;
                            dataMap.put(split[0], new AtomicLong(Long.parseLong(split[1])));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                initData = true;
            }
        }
    }

    @PreDestroy
    public void destroy() {
        saveData();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveData() {
        synchronized (saveDataFlag) {
            // 根据配置文件指定的缓存文件加载启动时的数据
            String file = blogStatisticsConfiguration.getFile();
            File dataBackFile = new File(file + System.currentTimeMillis() + ".back");
            writeData(dataBackFile);
            File oldFile = new File(file);
            if (oldFile.exists()) oldFile.delete();
            dataBackFile.renameTo(oldFile);
        }
    }

    private void writeData(File dataBackFile) {
        // 读取文件，加载初始化、启动数据
        try (FileWriter fileWriter = new FileWriter(dataBackFile); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : dataMap.keySet()) {
                stringBuilder
                        .append(key)
                        .append(SPLIT_CHAR)
                        .append(dataMap.get(key).longValue());
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
                stringBuilder.setLength(0);
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statistics(BlogStatisticsBrowseTO browse) {
        AtomicLong atomicLong = dataMap.computeIfAbsent(browse.blogUrl, k -> new AtomicLong(0));
        atomicLong.addAndGet(1);
    }

    @Override
    public Map<String, Long> query(List<String> blogUrlArr) {
        Map<String, Long> result = new HashMap<>();
        for (String item : blogUrlArr) {
            AtomicLong browse = dataMap.getOrDefault(item, null);
            result.put(item, null == browse ? 0L : browse.longValue());
        }
        return result;
    }
}
