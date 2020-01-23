package com.gxb.controller.center;

import com.gxb.pojo.Users;
import com.gxb.service.center.CenterUserService;
import com.gxb.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("center")
@Api(value = "center - 用户中心",tags = {"用户中心展示的相关接口"})
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    public JSONResult userInfo(@ApiParam(name = "userId",value = "用户id",required = true)
                                   @RequestParam("userId") String userId) {
        Users users = centerUserService.queryUserInfo(userId);
        return JSONResult.ok(users);
    }
}
