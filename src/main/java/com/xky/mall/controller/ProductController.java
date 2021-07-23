package com.xky.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xky.mall.common.CommonResponse;
import com.xky.mall.model.request.SelectProductReq;
import com.xky.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("前台查询商品，支持搜索，排序，分类下所有商品")
    @PostMapping("/query")
    public CommonResponse queryProduct(@RequestBody SelectProductReq productReq) {
        PageInfo pageInfo = productService.listProduct(productReq);
        return CommonResponse.success(pageInfo);
    }
}
