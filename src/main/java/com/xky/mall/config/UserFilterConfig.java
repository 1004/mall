package com.xky.mall.config;

import com.xky.mall.filter.UserFilter;
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
public class UserFilterConfig {

    /**
     * 将过滤器注册到IOC
     *
     * @return
     */
    @Bean
    public UserFilter userFilter() {
        return new UserFilter();
    }

    /**
     * 对过滤器的行为加入配置,注册过滤器
     * 同时也注入到IOC
     *
     * @return
     */
    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConfig() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(userFilter());
        //设置拦截规则
        registrationBean.addUrlPatterns("/cart/*");
        registrationBean.addUrlPatterns("/order/*");
        registrationBean.setName("userFilterConf");
        return registrationBean;
    }
}
