package com.xky.mall.service;

import com.xky.mall.model.pojo.Product;
import com.xky.mall.model.request.AddProductReq;

public interface ProductService {
    void addProduct(AddProductReq productReq);

    void updateProduct(Product product);

    void deleteProduct(Integer id);
}
