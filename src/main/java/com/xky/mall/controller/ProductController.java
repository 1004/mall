package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/23 11:11 上午
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/detail")
    public CommonResponse productDetail(@RequestParam Integer id) {
        return CommonResponse.success(productService.selectDetail(id));
    }
}
