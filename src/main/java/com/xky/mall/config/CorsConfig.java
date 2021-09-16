package com.xky.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/8/17 9:45 上午
 * @Desc 解决跨域问题，不同的ip或者端口进行访问
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://127.0.0.1:8080");  //运行的主机
        config.setAllowCredentials(true); //验证相关
        config.addAllowedHeader("*"); //允许所有的header
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",config);//为所有的请求都用这个配置
        return new CorsFilter(corsConfigurationSource);
    }
}
