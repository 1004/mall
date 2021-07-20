package com.xky.mall.controller;

import com.xky.mall.model.pojo.User;
import com.xky.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
