package com.xky.mall.controller;

import com.xky.mall.common.CommonResponse;
import com.xky.mall.model.dao.OrderMapper;
import com.xky.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/27 2:34 下午
 */
@RestController
@RequestMapping("/order/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("管理员查询所有订单")
    @GetMapping("/list")
    public CommonResponse listForAll(Integer page,Integer pageSize){
        return CommonResponse.success(orderService.listForAdmin(page,pageSize));
    }
}
