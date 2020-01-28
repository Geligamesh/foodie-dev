package com.gxb.service.Impl.center;

import com.github.pagehelper.PageHelper;
import com.gxb.enums.YesOrNo;
import com.gxb.mapper.ItemsCommentsMapperCustom;
import com.gxb.mapper.OrderItemsMapper;
import com.gxb.mapper.OrderStatusMapper;
import com.gxb.mapper.OrdersMapper;
import com.gxb.pojo.OrderItems;
import com.gxb.pojo.OrderStatus;
import com.gxb.pojo.Orders;
import com.gxb.pojo.bo.center.OrderItemsCommentBO;
import com.gxb.pojo.vo.MyCommentVO;
import com.gxb.service.Impl.BaseService;
import com.gxb.service.center.MyCommentsService;
import com.gxb.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> orderItemsCommentBOS) {
        //1.保存评价 items_content
        for (OrderItemsCommentBO orderItemsCommentBO : orderItemsCommentBOS) {
            orderItemsCommentBO.setCommentId(sid.next());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", orderItemsCommentBOS);
        itemsCommentsMapperCustom.saveComments(map);
        //2.修改订单状态为已经评价
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.getType());
        ordersMapper.updateByPrimaryKeySelective(orders);
        //3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 我的历史评价查询
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(list, page);
    }

}
