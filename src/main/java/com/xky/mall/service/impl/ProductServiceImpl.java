package com.xky.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.dao.ProductMapper;
import com.xky.mall.model.pojo.Product;
import com.xky.mall.model.request.AddProductReq;
import com.xky.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        if (oldProduct != null) {
            throw new MallException(MallExceptionEnum.PRODUCT_NAME_REPEAT);
        }
        Product product = new Product();
        BeanUtils.copyProperties(productReq, product);
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.PRODUCT_ADD_F);
        }
    }

    @Override
    public void updateProduct(Product product) {
        if (StringUtils.hasLength(product.getName())) {
            Product oldProduct = productMapper.selectByName(product.getName());
            if (oldProduct != null && !oldProduct.getId().equals(product.getId())) {
                throw new MallException(MallExceptionEnum.PRODUCT_NAME_REPEAT);
            }
        }
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.PRODUCT_UPDATE_F);
        }
    }

    @Override
    public void deleteProduct(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null){
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0){
            throw new MallException(MallExceptionEnum.PRODUCT_DELETE_F);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids,sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer page, Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<Product> products = productMapper.listForAdmin();
        return PageInfo.of(products);
    }

    @Override
    public Product selectDetail(Integer id){
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }
}
