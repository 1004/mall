package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.model.request.CreateOrderReq;
import com.xky.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/26 3:22 下午
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public CommonResponse create(@Valid @RequestBody CreateOrderReq orderReq) {
        return CommonResponse.success(orderService.create(orderReq));
    }

    @GetMapping("/detail")
    public CommonResponse detail(@RequestParam String orderNo){
        return CommonResponse.success(orderService.detail(orderNo));
    }
}
