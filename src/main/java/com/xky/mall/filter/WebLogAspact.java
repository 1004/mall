package com.xky.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/20 10:17 上午
 * <p>
 * 请求信息的记录
 */
@Aspect //切面
@Component //加入ioc容器管理
public class WebLogAspact {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspact.class);

    /**
     * 切点
     */
    @Pointcut("execution(public * com.xky.mall.controller.*.*(..))")
    public void webLog() {

    }

    /**
     * 通知
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("URL: " + request.getRequestURL().toString());
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null){
            StringBuffer params = new StringBuffer();
            String pk = "";
            for (Map.Entry<String,String[]> entry:parameterMap.entrySet()){
                pk = entry.getKey()+"=";
                for (String s:entry.getValue()){
                    pk+=(s+",");
                }
                if (pk.endsWith(",")){
                    pk = pk.substring(0,pk.length()-1);
                }
                pk +="&";
                params.append(pk);
            }
            String paramsStr = params.toString();
            if (params.toString().endsWith("&")){
                paramsStr = params.toString().substring(0,params.toString().length()-1);
            }
            logger.info("HTTP-REQUEST-PARAMS: " + paramsStr);
        }
        logger.info("HTTP-METHOD: " + request.getMethod());
        logger.info("IP: " + request.getRemoteAddr());
        logger.info("CLASS-METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "webLog()", returning = "res")
    public void doReturn(Object res) throws JsonProcessingException {
        logger.info("RESPONSE: " + new ObjectMapper().writeValueAsString(res));
    }
}
