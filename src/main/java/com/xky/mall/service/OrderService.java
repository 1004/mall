package com.xky.mall.service;

import com.xky.mall.model.request.CreateOrderReq;

public interface OrderService {
    String create(CreateOrderReq orderReq);
}
