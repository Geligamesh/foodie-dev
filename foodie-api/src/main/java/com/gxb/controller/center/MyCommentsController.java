package com.gxb.controller.center;

import com.gxb.controller.BaseController;
import com.gxb.enums.YesOrNo;
import com.gxb.pojo.OrderItems;
import com.gxb.pojo.Orders;
import com.gxb.service.center.MyCommentsService;
import com.gxb.service.center.MyOrdersService;
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
                             @RequestParam(value = "orderId",required = false) String orderId) {
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

}
