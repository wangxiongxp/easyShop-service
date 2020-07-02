package com.aliboy.service.impl.center;

import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.mapper.OrderMapper;
import com.aliboy.model.dto.center.MyOrdersVO;
import com.aliboy.model.dto.center.OrderStatusCountsVO;
import com.aliboy.model.entity.Order;
import com.aliboy.service.center.MyOrdersService;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    public OrderMapper orderMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = Lists.newArrayList();
//        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);

        return setPagedGrid(list, page);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

//        OrderStatus updateOrder = new OrderStatus();
//        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
//        updateOrder.setDeliverTime(new Date());
//
//        Example example = new Example(OrderStatus.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("orderId", orderId);
//        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
//
//        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public Order queryMyOrder(Integer userId, String orderId) {

        Order order = new Order();
        order.setUserId(userId);
        order.setId(orderId);
        order.setIsDeleted(Boolean.FALSE);

        return orderMapper.selectOne(order);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

//        OrderStatus updateOrder = new OrderStatus();
//        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
//        updateOrder.setSuccessTime(new Date());
//
//        Example example = new Example(OrderStatus.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("orderId", orderId);
//        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
//
//        int result = orderStatusMapper.updateByExampleSelective(updateOrder, example);

        return true;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        Order updateOrder = new Order();
        updateOrder.setIsDeleted(Boolean.TRUE);

        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = orderMapper.updateByExampleSelective(updateOrder, example);

        return result == 1 ? true : false;
    }

    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

//        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
//        int waitPayCounts = orderMapper.getMyOrderStatusCounts(map);
//
//        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
//        int waitDeliverCounts = orderMapper.getMyOrderStatusCounts(map);
//
//        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
//        int waitReceiveCounts = orderMapper.getMyOrderStatusCounts(map);
//
//        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
//        map.put("isComment", YesOrNo.NO.type);
//        int waitCommentCounts = orderMapper.getMyOrderStatusCounts(map);
//
//        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts,
//                waitDeliverCounts,
//                waitReceiveCounts,
//                waitCommentCounts);
        return null;
    }

    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
//        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);
//        List<Order> list = ordersMapperCustom.getMyOrderTrend(map);
        List<Order> list = Lists.newArrayList();

        return setPagedGrid(list, page);
    }

}
