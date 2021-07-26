package com.xky.mall.service;

import com.xky.mall.model.vo.CartVO;

import java.util.List;

public interface CartService {
    List<CartVO> add(Integer productId, Integer count);

    List<CartVO> list();

    List<CartVO> update(Integer productId, Integer count);

    List<CartVO> delete(Integer productId);

    List<CartVO> check(Integer productId, Integer checked);

    List<CartVO> checkAll(Integer checked);
}
