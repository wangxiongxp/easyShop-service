package com.aliboy.service;

import com.aliboy.model.dto.SubmitOrderBO;
import com.aliboy.model.vo.OrderVO;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-04 12:36
 */
public interface OrderService {
    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();

}
