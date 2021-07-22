package com.xky.mall.filter;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.common.Constants;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.pojo.User;
import com.xky.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/22 10:33 上午
 * 后台管理员权限校验
 */
public class AdminFilter implements Filter {
    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        //登录逻辑校验
        User user = (User) session.getAttribute(Constants.USER_LOGIN_CACHE_KEY);
        if (user == null || !userService.checkAdminRole(user)) {
            HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            responseWrapper.setCharacterEncoding("UTF-8");
            responseWrapper.setContentType("application/json;charset=utf-8");
            PrintWriter writer = responseWrapper.getWriter();
            writer.write("{\n" +
                    "    \"code\": 1006,\n" +
                    "    \"msg\": \"用户未登录或无权限\",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
