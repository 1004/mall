package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.common.Constants;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.pojo.Category;
import com.xky.mall.model.pojo.User;
import com.xky.mall.model.request.AddCategoryReq;
import com.xky.mall.model.request.UpdataCategoryReq;
import com.xky.mall.service.CategoryService;
import com.xky.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("后台添加目录")
    @PostMapping("/admin/add")
    public CommonResponse<Object> addCatetory(@ApiParam("数据") @Valid @RequestBody AddCategoryReq category, HttpSession session) {
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

    @ApiOperation("后台修改目录")
    @PostMapping("/admin/updata")
    public CommonResponse<Object> updataCategory(@Valid @RequestBody UpdataCategoryReq categoryReq, HttpSession session) {
        //登录逻辑校验
        User user = (User) session.getAttribute(Constants.USER_LOGIN_CACHE_KEY);
        if (user == null) {
            return CommonResponse.error(MallExceptionEnum.USER_LOGIN_NEED);
        }
        //用户角色登录
        if (userService.checkAdminRole(user)) {
            // 然后进行插入
            Category category = new Category();
            BeanUtils.copyProperties(categoryReq, category);
            categoryService.updataCategory(category);
        } else {
            return CommonResponse.error(MallExceptionEnum.USER_LOGIN_NEED);
        }
        return CommonResponse.success();
    }

    @ApiOperation("后台删除分类")
    @GetMapping("/admin/delete")
    public CommonResponse<Object> deleteCategory(Integer id) {
        return CommonResponse.success();
    }

}
