package com.devnolo.blog.statistics.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

/**
 * 启动类
 * @author qingsong
 * @date 2022年11月26日
 */
@SpringBootApplication
public class BlogStatisticsWebApplication {
    public static void main(String[] args) {
        ApplicationHome home = new ApplicationHome();
        System.out.println(home.getDir().getAbsolutePath());
        SpringApplication.run(BlogStatisticsWebApplication.class, args);
    }
}
