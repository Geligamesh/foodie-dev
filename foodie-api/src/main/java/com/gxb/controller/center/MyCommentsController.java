package com.gxb.controller.center;

import com.gxb.controller.BaseController;
import com.gxb.enums.YesOrNo;
import com.gxb.pojo.OrderItems;
import com.gxb.pojo.Orders;
import com.gxb.pojo.bo.center.OrderItemsCommentBO;
import com.gxb.service.center.MyCommentsService;
import com.gxb.utils.JSONResult;
import com.gxb.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("mycomments")
@Api(value = "用户中心评价模块",tags = {"用户中心评价模块相关的接口"})
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @PostMapping("pending")
    @ApiOperation(value = "查询订单信息",notes = "查询订单信息",httpMethod = "POST")
    public JSONResult update(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam("userId") String userId,
                             @RequestParam(value = "orderId",required = false) String orderId,HttpServletRequest request
    ) {
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        Orders orders = (Orders) jsonResult.getData();
        if (orders.getIsComment().equals(YesOrNo.YES.getType())) {
            return JSONResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);
        return JSONResult.ok(orderItems);
    }

    @PostMapping("saveList")
    @ApiOperation(value = "保存评论列表",notes = "保存评论列表",httpMethod = "POST")
    public JSONResult saveList(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam("userId") String userId,
                               @RequestParam(value = "orderId") String orderId,
                               @RequestBody List<OrderItemsCommentBO> commentList) {
        System.out.println(commentList);
        //判断用户和订单是否关联
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return JSONResult.errorMsg("评论内容不能为空");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return JSONResult.ok();
    }

    @PostMapping("query")
    @ApiOperation(value = "查询我的历史评价",notes = "查询我的历史评价",httpMethod = "POST")
    public JSONResult update(@ApiParam(name = "userId",value = "用户id",required = true)
                             @RequestParam("userId") String userId,
                             Integer page,
                             Integer pageSize,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg( null);
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = myCommentsService.queryMyComments(userId, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }
}
