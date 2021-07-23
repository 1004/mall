package com.xky.mall.filter;

import com.xky.mall.common.Constants;
import com.xky.mall.model.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/22 10:33 上午
 * 普通用户
 */
public class UserFilter implements Filter {
    /**
     * 此处有线程风险
     */
    public static User currentUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        //登录逻辑校验
        currentUser = (User) session.getAttribute(Constants.USER_LOGIN_CACHE_KEY);
        if (currentUser == null) {
            HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            responseWrapper.setCharacterEncoding("UTF-8");
            responseWrapper.setContentType("application/json;charset=utf-8");
            PrintWriter writer = responseWrapper.getWriter();
            writer.write("{\n" +
                    "    \"code\": 1006,\n" +
                    "    \"msg\": \"用户未登录\",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
