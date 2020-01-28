package com.gxb.controller.center;

import com.gxb.controller.BaseController;
import com.gxb.pojo.Orders;
import com.gxb.pojo.Users;
import com.gxb.pojo.bo.center.CenterUserBO;
import com.gxb.pojo.vo.OrderStatusCountsVO;
import com.gxb.resource.FileUpLoad;
import com.gxb.service.center.CenterUserService;
import com.gxb.service.center.MyOrdersService;
import com.gxb.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("myorders")
@Api(value = "用户中心我的订单",tags = {"用户中心我的订单相关的接口"})
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @PostMapping("query")
    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "GET")
    public JSONResult update(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam("userId") String userId,
                             @RequestParam(value = "orderStatus",required = false) Integer orderStatus,
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
        PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @GetMapping("deliver")
    @ApiOperation(value = "商家发货",notes = "商家发货",httpMethod = "GET")
    public JSONResult deliver(@ApiParam(name = "orderId",value = "订单id",required = true)
                                          @RequestParam("orderId") String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg( null);
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }

    @PostMapping("confirmReceive")
    @ApiOperation(value = "用户确认收货",notes = "用户确认收货",httpMethod = "POST")
    public JSONResult confirmReceive(@ApiParam(name = "orderId",value = "订单id",required = true)
                              @RequestParam("orderId") String orderId,
                                     @ApiParam(name = "userId",value = "用户id",required = true)
                                     @RequestParam("userId") String userId) {
        JSONResult jsonResult = this.checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return jsonResult.errorMsg("订单确认收货失败!");
        }
        return JSONResult.ok();
    }

    @PostMapping("delete")
    @ApiOperation(value = "用户删除订单",notes = "用户删除订单",httpMethod = "POST")
    public JSONResult delete(@ApiParam(name = "orderId",value = "订单id",required = true)
                                     @RequestParam("orderId") String orderId,
                                     @ApiParam(name = "userId",value = "用户id",required = true)
                                     @RequestParam("userId") String userId) {
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return jsonResult.errorMsg("订单删除失败!");
        }
        return JSONResult.ok();
    }

    @PostMapping("statusCounts")
    @ApiOperation(value = "获得订单状态书概况",notes = "获得订单状态书概况",httpMethod = "POST")
    public JSONResult statusCounts(@ApiParam(name = "userId",value = "用户id",required = true)
                                   @RequestParam("userId") String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }
        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);
        return JSONResult.ok(orderStatusCounts);
    }

    @PostMapping("trend")
    @ApiOperation(value = "查询订单动向",notes = "查询订单动向",httpMethod = "POST")
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
        PagedGridResult ordersTrend = myOrdersService.getOrdersTrend(userId, page, pageSize);
        return JSONResult.ok(ordersTrend);
    }

}
