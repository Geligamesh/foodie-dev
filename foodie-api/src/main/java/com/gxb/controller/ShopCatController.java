package com.gxb.controller;

import com.gxb.pojo.bo.ShopcartBO;
import com.gxb.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口Controller",tags = "购物车接口相关的api")
@RestController
@RequestMapping("shopcart")
public class ShopCatController {

    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",tags = {"添加商品到购物车"},httpMethod = "POST")
    @PostMapping("add")
    public JSONResult add(@RequestParam("userId") String userId,
                          @RequestBody ShopcartBO ShopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        System.out.println(ShopcartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存中
        return JSONResult.ok();
    }
}
