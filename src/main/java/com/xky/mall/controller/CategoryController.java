package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.common.Constants;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.pojo.User;
import com.xky.mall.model.request.AddCategoryReq;
import com.xky.mall.service.CategoryService;
import com.xky.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/21 5:10 下午
 * 分类处理
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 保证必须登录
     * json 请求
     *
     * @param category
     * @param session
     * @return
     */
    @PostMapping("/admin/add")
    public CommonResponse<Object> addCatetory(@Valid @RequestBody AddCategoryReq category, HttpSession session) {
        //登录逻辑校验
        User user = (User) session.getAttribute(Constants.USER_LOGIN_CACHE_KEY);
        if (user == null) {
            return CommonResponse.error(MallExceptionEnum.USER_LOGIN_NEED);
        }
        //用户角色登录
        if (userService.checkAdminRole(user)) {
            // 然后进行插入
            categoryService.addCategory(category);
        } else {
            return CommonResponse.error(MallExceptionEnum.USER_LOGIN_NEED);
        }
        return CommonResponse.success();
    }

}
