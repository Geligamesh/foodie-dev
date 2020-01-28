package com.gxb.service.center;

import com.gxb.pojo.OrderItems;
import com.gxb.pojo.bo.center.OrderItemsCommentBO;
import com.gxb.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param orderItemsCommentBOS
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> orderItemsCommentBOS);

    PagedGridResult queryMyComments(String userId,Integer page,Integer pageSize);
}
