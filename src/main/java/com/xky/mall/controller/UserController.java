package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.pojo.User;
import com.xky.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/19 11:54 上午
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/select")
    public User selectUser(@RequestParam("id") int id) {
        return userService.selectByPrimaryKey(id);
    }

    /**
     * 注册
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/register")
    public CommonResponse<Object> register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return CommonResponse.error(MallExceptionEnum.USER_NEED_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.error(MallExceptionEnum.USER_NEED_PWD);
        }
        if (password.length() < 8) {
            return CommonResponse.error(MallExceptionEnum.USER_PWD_LENGTH);
        }
        userService.regist(userName, password);
        return CommonResponse.success();
    }
}
