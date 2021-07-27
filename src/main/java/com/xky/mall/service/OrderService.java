package com.xky.mall.service;

import com.github.pagehelper.PageInfo;
import com.xky.mall.model.request.CreateOrderReq;
import com.xky.mall.model.vo.OrderVO;

public interface OrderService {
    String create(CreateOrderReq orderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer page, Integer pageSize);
}
