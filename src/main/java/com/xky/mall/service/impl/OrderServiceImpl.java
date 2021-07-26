package com.xky.mall.service.impl;

import com.xky.mall.common.Constants;
import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.filter.UserFilter;
import com.xky.mall.model.dao.CartMapper;
import com.xky.mall.model.dao.OrderItemMapper;
import com.xky.mall.model.dao.OrderMapper;
import com.xky.mall.model.dao.ProductMapper;
import com.xky.mall.model.pojo.Order;
import com.xky.mall.model.pojo.OrderItem;
import com.xky.mall.model.pojo.Product;
import com.xky.mall.model.request.CreateOrderReq;
import com.xky.mall.model.vo.CartVO;
import com.xky.mall.service.OrderService;
import com.xky.mall.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/26 3:48 下午
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 创建订单
     *
     * @param orderReq 订单号
     * @return
     */
    @Override
    public String create(CreateOrderReq orderReq) {
        //①拿到用户id
        Integer userId = UserFilter.currentUser.getId();
        //② 获取购物车中的选中的商品 并对商品做校验
        List<CartVO> cartVOS = selectCartVos(userId);
        //③ 判断商品是否存在，上下架状态， 库存
        validProduct(cartVOS);
        //④ 将商品转为中间表的item对象
        List<OrderItem> orderItems = cartVOListToOrderItems(cartVOS);
        //⑤ 扣库存
        deleteStock(orderItems);
        //⑥ 清除购物车中已选择的商品
        clearCarSelectP(cartVOS);
        //⑦生成订单对象,并保存
        Order order = generateOrder(orderReq, orderItems, userId);
        //⑨ 插入商品和订单关系表 order_item
        String orderNo = orderItemInsert(orderItems, order);
        //10 返回订单编码
        return orderNo;
    }

    private List<CartVO> selectCartVos(Integer userId) {
        List<CartVO> localVOs = cartMapper.selectList(userId);
        List<CartVO> selectVOs = new ArrayList<>();
        for (int i = 0; i < localVOs.size(); i++) {
            CartVO cartVO = localVOs.get(i);
            if (cartVO.getSelected() == Constants.Cart.CHECKED) {
                selectVOs.add(cartVO);
            }
        }
        if (CollectionUtils.isEmpty(selectVOs)) {
            throw new MallException(MallExceptionEnum.ORDER_NO_SELECT);
        }
        return selectVOs;
    }

    /**
     * 校验商品
     */
    private void validProduct(List<CartVO> cartVOS) {
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            if (product == null) {
                throw new MallException(MallExceptionEnum.CART_PRO_NOT_EXIST);
            }
            if (product.getStatus() == Constants.SaleStatus.NOT_SALE) {
                throw new MallException(MallExceptionEnum.CART_PRO_HAS_SALE);
            }
            //库存不足
            if (product.getStock() < cartVO.getQuantity()) {
                throw new MallException(MallExceptionEnum.CART_PRO_NO_ENOUGH);
            }
        }
    }

    private List<OrderItem> cartVOListToOrderItems(List<CartVO> cartVOS) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setUnitPrice(cartVO.getProductPrice());
            orderItem.setTotalPrice(cartVO.getProductPrice() * cartVO.getQuantity());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    //扣库存
    private void deleteStock(List<OrderItem> orderItems) {
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int quantity = product.getStock() - orderItem.getQuantity();
            if (quantity < 0) {
                throw new MallException(MallExceptionEnum.ORDER_NO_ENOUGH);
            }
            product.setStock(quantity);
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    //清空选择的购物车
    private void clearCarSelectP(List<CartVO> cartVOS) {
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    private Order generateOrder(CreateOrderReq orderReq, List<OrderItem> orderItems, Integer userId) {
        Order order = new Order();
        order.setOrderNo(OrderCodeFactory.getOrderCode(Long.valueOf(userId + "")));
        order.setUserId(userId);
        order.setTotalPrice(getTotalPrice(orderItems));
        order.setOrderStatus(Constants.OrderStatusEnum.NOT_PAY.getCode());
        order.setReceiverName(orderReq.getReceiverName());
        order.setReceiverAddress(orderReq.getReceiverAddress());
        order.setReceiverMobile(orderReq.getReceiverMobile());
        order.setPostage(0);
        order.setPaymentType(1); //支付方式
        int count = orderMapper.insertSelective(order);
        return order;
    }

    private Integer getTotalPrice(List<OrderItem> orderItems) {
        Integer total = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            total += orderItems.get(i).getTotalPrice();
        }
        return total;
    }

    private String orderItemInsert(List<OrderItem> orderItems, Order order) {
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        }
        return order.getOrderNo();
    }
}
