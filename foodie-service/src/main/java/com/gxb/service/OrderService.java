package com.gxb.service;

import com.gxb.pojo.OrderStatus;
import com.gxb.pojo.bo.SubmitOrderBO;
import com.gxb.pojo.vo.OrderVO;

public interface OrderService {

    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId,Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
