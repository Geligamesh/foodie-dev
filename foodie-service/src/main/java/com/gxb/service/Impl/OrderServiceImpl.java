package com.gxb.service.Impl;

import com.gxb.mapper.OrdersMapper;
import com.gxb.pojo.bo.SubmitOrderBO;
import com.gxb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(SubmitOrderBO submitOrderBO) {

    }
}
