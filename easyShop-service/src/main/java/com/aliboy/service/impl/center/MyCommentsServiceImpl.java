package com.aliboy.service.impl.center;

import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.mapper.OrderGoodsMapper;
import com.aliboy.mapper.OrderMapper;
import com.aliboy.model.dto.center.OrderItemsCommentBO;
import com.aliboy.model.entity.Order;
import com.aliboy.model.entity.OrderGoods;
import com.aliboy.service.center.MyCommentsService;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    public OrderGoodsMapper orderGoodsMapper;

    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderGoods> queryPendingComment(String orderId) {
        OrderGoods query = new OrderGoods();
        query.setOrderId(orderId);
        return orderGoodsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, Integer userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
//        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Order order = new Order();
        order.setId(orderId);
//        order.setIsComment(YesOrNo.YES.type);
        orderMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
//        OrderStatus orderStatus = new OrderStatus();
//        orderStatus.setOrderId(orderId);
//        orderStatus.setCommentTime(new Date());
//        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
//        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setPagedGrid(Lists.newArrayList(), page);
    }

}
