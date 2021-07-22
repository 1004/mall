package com.xky.mall.service.impl;

import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.dao.ProductMapper;
import com.xky.mall.model.pojo.Product;
import com.xky.mall.model.request.AddProductReq;
import com.xky.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/22 7:41 下午
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(AddProductReq productReq) {
        Product oldProduct = productMapper.selectByName(productReq.getName());
        if (oldProduct != null){
            throw new MallException(MallExceptionEnum.PRODUCT_NAME_REPEAT);
        }
        Product product = new Product();
        BeanUtils.copyProperties(productReq,product);
        int count = productMapper.insertSelective(product);
        if (count == 0){
            throw new MallException(MallExceptionEnum.PRODUCT_ADD_F);
        }
    }
}
