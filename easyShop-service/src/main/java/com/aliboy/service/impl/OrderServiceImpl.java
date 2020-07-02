package com.aliboy.service.impl;

import com.aliboy.common.utils.DateUtil;
import com.aliboy.common.utils.UUIDGenerator;
import com.aliboy.mapper.GoodsMapper;
import com.aliboy.mapper.OrderGoodsMapper;
import com.aliboy.mapper.OrderMapper;
import com.aliboy.mapper.ProductMapper;
import com.aliboy.model.dto.SubmitOrderBO;
import com.aliboy.model.entity.*;
import com.aliboy.model.enums.OrderStatusEnum;
import com.aliboy.model.vo.OrderVO;
import com.aliboy.service.AddressService;
import com.aliboy.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-01 22:55
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        Integer userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String productIds = submitOrderBO.getProductIds();
        Integer couponId = submitOrderBO.getCouponId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String remark = submitOrderBO.getRemark();

        // 包邮费用设置为0
        BigDecimal shippingFee = new BigDecimal(0);
        // 优惠券设置为0
        BigDecimal couponPrice = new BigDecimal(0);

        String orderId = sid.next();

        UserAddress address = addressService.queryUserAddressById(Integer.parseInt(addressId));

        // 1. 新订单数据保存
        Order newOrder = new Order();
        newOrder.setId(orderId);
        newOrder.setOrderSn(orderId);
        newOrder.setUserId(userId);

        newOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        newOrder.setShippingStatus(false);
        newOrder.setPayStatus(false);
        newOrder.setCommentStatus(false);

        newOrder.setCountry(address.getCountry());
        newOrder.setProvince(address.getProvince());
        newOrder.setCity(address.getCity());
        newOrder.setDistrict(address.getDistrict());
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail());

//        newOrder.setPayId(payMethod);
//        newOrder.setPayName(payMethod);

        newOrder.setShippingFee(shippingFee);

        // 2. 循环根据productIds保存订单商品信息表
        String[] productIdArr = productIds.split(",");
        /**
         * 商品原价累计
         */
        BigDecimal goodsPrice = new BigDecimal(0);

        for (String productId : productIdArr) {

            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;

            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            Product product = productMapper.selectByPrimaryKey(productId);
            goodsPrice = goodsPrice.add(product.getRetailPrice().multiply(new BigDecimal(buyCounts)));

            // 2.2 根据商品id，获得商品信息以及商品图片
            Integer goodsId = product.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

            // 2.3 循环保存子订单数据到数据库
            OrderGoods subOrderItem = new OrderGoods();
            subOrderItem.setOrderId(orderId);
            subOrderItem.setGoodsId(goodsId);
            subOrderItem.setGoodsName(goods.getName());
            subOrderItem.setGoodsSn(goods.getGoodsSn());
            subOrderItem.setProductId(Integer.parseInt(productId));
            subOrderItem.setNumber(buyCounts);
            subOrderItem.setMarketPrice(product.getRetailPrice());
            subOrderItem.setRetailPrice(product.getRetailPrice());
            subOrderItem.setListPicUrl(goods.getListPicUrl());
            subOrderItem.setGoodsSpecificationIds(product.getGoodsSpecificationIds());
            subOrderItem.setGoodsSpecificationValues(product.getGoodsSpecificationValues());
            orderGoodsMapper.insert(subOrderItem);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
//            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        newOrder.setGoodsPrice(goodsPrice);
        newOrder.setOrderPrice(goodsPrice);
        newOrder.setActualPrice(goodsPrice.subtract(couponPrice));

        newOrder.setCreatedTime(new Date());
        newOrder.setRemark(remark);
        orderMapper.insert(newOrder);




        // 4. 构建商户订单，用于传给支付中心
//        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
//        merchantOrdersVO.setMerchantOrderId(orderId);
////        merchantOrdersVO.setMerchantUserId(userId);
//        merchantOrdersVO.setAmount(realPayAmount + postAmount);
//        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
//        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

//        OrderStatus paidStatus = new OrderStatus();
//        paidStatus.setOrderId(orderId);
//        paidStatus.setOrderStatus(orderStatus);
//        paidStatus.setPayTime(new Date());
//
//        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        Order queryOrder = new Order();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<Order> list = orderMapper.select(queryOrder);
        for (Order order : list) {
            // 获得订单创建时间
            Date createdTime = order.getCreatedTime();
            // 和当前时间进行对比
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                // 超过1天，关闭订单
                doCloseOrder(order.getId());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        Order closeOrder = new Order();
        closeOrder.setId(orderId);
        closeOrder.setOrderStatus(OrderStatusEnum.CLOSE.type);
        closeOrder.setCloseTime(new Date());
        orderMapper.updateByPrimaryKeySelective(closeOrder);
    }
}
