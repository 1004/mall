package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/23 6:11 下午
 * 购物车
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public CommonResponse add(@RequestParam("productId") Integer productId, @RequestParam("count") Integer count) {
        cartService.add(productId, count);
        return CommonResponse.success();
    }
}
