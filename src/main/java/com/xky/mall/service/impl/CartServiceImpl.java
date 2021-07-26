package com.xky.mall.service.impl;

import com.xky.mall.common.Constants;
import com.xky.mall.config.UserFilterConfig;
import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.filter.UserFilter;
import com.xky.mall.model.dao.CartMapper;
import com.xky.mall.model.dao.ProductMapper;
import com.xky.mall.model.pojo.Cart;
import com.xky.mall.model.pojo.Product;
import com.xky.mall.model.pojo.User;
import com.xky.mall.model.vo.CartVO;
import com.xky.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/23 6:18 下午
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<CartVO> add(Integer productId, Integer count) {
        //校验
        validProduct(productId, count);
        User user = UserFilter.currentUser;
        //是否已经在购物车了
        Cart cart = cartMapper.selectByUserIdAndProId(productId, user.getId());
        if (cart == null) {
            //不在购物车， 新增当前记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setUserId(user.getId());
            cart.setSelected(Constants.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //在购物车，只需要数量+1
            Cart updateCart = new Cart();
            updateCart.setId(cart.getId());
            updateCart.setQuantity(cart.getQuantity() + 1);
            updateCart.setSelected(Constants.Cart.CHECKED);
            updateCart.setProductId(productId);
            updateCart.setUserId(user.getId());
            cartMapper.updateByPrimaryKeySelective(updateCart);
        }
        return list();
    }

    /**
     * 校验商品
     *
     * @param productId
     */
    private void validProduct(Integer productId, Integer count) {
//        ①：查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            throw new MallException(MallExceptionEnum.CART_PRO_NOT_EXIST);
        }
        if (product.getStatus() == Constants.SaleStatus.NOT_SALE) {
            throw new MallException(MallExceptionEnum.CART_PRO_HAS_SALE);
        }
        //库存不足
        if (product.getStock() < count) {
            throw new MallException(MallExceptionEnum.CART_PRO_NO_ENOUGH);
        }
    }

    /**
     * 查询
     *
     * @return
     */
    @Override
    public List<CartVO> list() {
        List<CartVO> cartVOS = cartMapper.selectList(UserFilter.currentUser.getId());
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getQuantity() * cartVO.getProductPrice());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> update(Integer productId, Integer count) {
        //校验
        validProduct(productId, count);
        User user = UserFilter.currentUser;
        //是否已经在购物车了
        Cart cart = cartMapper.selectByUserIdAndProId(productId, user.getId());
        if (cart == null) {
            //不在购物车， 抛出异常
            throw new MallException(MallExceptionEnum.CART_PRO_NOT_EXIST);
        } else {
            //在购物车，只需要数量+1
            Cart updateCart = new Cart();
            updateCart.setId(cart.getId());
            updateCart.setQuantity(count);
            updateCart.setSelected(Constants.Cart.CHECKED);
            updateCart.setProductId(productId);
            updateCart.setUserId(user.getId());
            cartMapper.updateByPrimaryKeySelective(updateCart);
        }
        return list();
    }


    @Override
    public List<CartVO> delete(Integer productId) {
        User user = UserFilter.currentUser;
        //是否已经在购物车了
        Cart cart = cartMapper.selectByUserIdAndProId(productId, user.getId());
        if (cart == null) {
            throw new MallException(MallExceptionEnum.CART_DELETE_F);
        }
        int count = cartMapper.deleteByPrimaryKey(cart.getId());
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CART_DELETE_F);
        }
        return list();
    }

}
