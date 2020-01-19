package com.gxb.controller;

import com.gxb.enums.PayMethod;
import com.gxb.pojo.bo.SubmitOrderBO;
import com.gxb.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
@Api(value = "订单相关",tags = {"订单相关的api接口"})
public class OrdersController {

    @ApiOperation(value = "用户下单",notes = "用户下单",tags = {"用户下单"},httpMethod = "POST")
    @PostMapping("create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO) {
        if(!submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.getType()) && !submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.getType())) {
            return JSONResult.errorMsg("支付状态不支持");
        }
        System.out.println(submitOrderBO);
        //1.创建订单

        //2.创建订单以后，移除购物车中已结算（已提交的）商品
        //3.向支付中心发送当前订单,用于保存支付中心的订单数据
        return JSONResult.ok();
    }

}
