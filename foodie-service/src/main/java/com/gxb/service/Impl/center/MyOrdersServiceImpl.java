package com.gxb.service.Impl.center;

import com.github.pagehelper.PageHelper;
import com.gxb.enums.OrderStatusEnum;
import com.gxb.enums.YesOrNo;
import com.gxb.mapper.OrderStatusMapper;
import com.gxb.mapper.OrdersMapper;
import com.gxb.mapper.OrdersMapperCustom;
import com.gxb.pojo.OrderStatus;
import com.gxb.pojo.Orders;
import com.gxb.pojo.vo.MyOrdersVO;
import com.gxb.pojo.vo.OrderStatusCountsVO;
import com.gxb.service.Impl.BaseService;
import com.gxb.service.center.MyOrdersService;
import com.gxb.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> myOrdersVOList = ordersMapperCustom.queryMyOrders(map);
        return this.setterPagedGrid(myOrdersVOList, page);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.getType());
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.getType());

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    /**
     * 查看我的订单
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.getType());
        return ordersMapper.selectOne(orders);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.getType());
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.getType());

        int result = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return result == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.getType());
        updateOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(updateOrder, example);

        return result == 1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.getType());
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.getType());
        int waitDeliveryCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.getType());
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.getType());
        map.put("isComment", YesOrNo.NO.getType());
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        return new OrderStatusCountsVO(waitPayCounts,waitDeliveryCounts,waitReceiveCounts,waitCommentCounts);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<OrderStatus> myOrderTrend = ordersMapperCustom.getMyOrderTrend(map);
        return setterPagedGrid(myOrderTrend, page);
    }


}
