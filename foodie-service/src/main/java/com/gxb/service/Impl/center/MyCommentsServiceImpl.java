package com.gxb.service.Impl.center;

import com.gxb.mapper.OrderItemsMapper;
import com.gxb.pojo.OrderItems;
import com.gxb.service.center.MyCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }
}
