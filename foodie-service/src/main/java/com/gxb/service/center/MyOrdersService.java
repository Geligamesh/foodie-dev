package com.gxb.service.center;

import com.gxb.pojo.Orders;
import com.gxb.pojo.vo.OrderStatusCountsVO;
import com.gxb.utils.PagedGridResult;

public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId,Integer orderStatus,Integer page,Integer pageSize);

    /**
     * 订单状态 -> 商家发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    Orders queryMyOrder(String userId,String orderId);

    /**
     * 订单状态 -> 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    boolean deleteOrder(String userId,String orderId);

    /**
     * 查询用户的订单数
     * @param userId
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    PagedGridResult getOrdersTrend(String userId,Integer page,Integer pageSize);

}
