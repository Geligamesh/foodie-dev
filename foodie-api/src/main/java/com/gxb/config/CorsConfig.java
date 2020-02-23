package com.gxb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        //添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        //不介意使用*，不安全
        //http://localhost:8080
        config.addAllowedOrigin("*");
        //设置是否设置cookie信息
        config.setAllowCredentials(true);
        //设置允许请求的方式
        config.addAllowedMethod("*");
        //设置允许的header
        config.addAllowedHeader("*");
        //为url添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        //返回重新定义好的corsSource
        return new CorsFilter(source);
    }
}

