package com.xky.mall.filter;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/20 5:44 下午
 * 所有异常的捕获处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        logger.error("Default Exception", e);
        return CommonResponse.error(MallExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 处理业务异常
     *
     * @param mallException
     * @return
     */
    @ExceptionHandler(MallException.class)
    @ResponseBody
    public Object handleMallException(MallException mallException) {
        logger.error("mall Exception", mallException);
        return CommonResponse.error(mallException.getCode(), mallException.getMsg());
    }
}
