package com.xky.mall.config;

import com.xky.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/22 10:42 上午
 * 过滤器的配置，注册过滤器
 */
@Configuration
public class AdminFilterConfig {

    /**
     * 将过滤器注册到IOC
     *
     * @return
     */
    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    /**
     * 对过滤器的行为加入配置,注册过滤器
     * 同时也注入到IOC
     *
     * @return
     */
    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(adminFilter());
        registrationBean.addUrlPatterns("/category/admin/*");
        registrationBean.addUrlPatterns("/product/admin/*");
        registrationBean.addUrlPatterns("/order/admin/*");
        registrationBean.setName("adminFilterConf");
        return registrationBean;
    }
}
