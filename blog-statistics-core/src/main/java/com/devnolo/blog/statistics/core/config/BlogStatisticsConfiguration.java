package com.devnolo.blog.statistics.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认授权配置类
 * @author qingsong
 * @date 2022年11月26日
 */
@Component
@ConfigurationProperties(prefix = "blog.statistics")
public class BlogStatisticsConfiguration {

    /**
     * 授权配置
     */
    private String authentication;

    /**
     * 数据文件配置
     */
    private String dataName = "default.data";

    /**
     * 数据文件配置
     */
    private String dataDir = null;

    private final Map<String, String> authenticationMap = new HashMap<>(Map.of("admin", "g5ldqbpFYlSp5jm"));

    public void setAuthentication(String authentication) {
        String[] split = authentication.split(";");
        for (String item : split) {
            try {
                String[] auth = item.split(":");
                if (auth.length != 2) {
                    continue;
                }
                authenticationMap.put(auth[0], auth[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.authentication = authentication;
    }

    public String getAuthentication() {
        return authentication;
    }

    public Object getSecret(String key) {
        return authenticationMap.get(key);
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataName() {
        return dataName;
    }
}
