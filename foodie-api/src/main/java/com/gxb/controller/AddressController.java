package com.gxb.controller;

import com.gxb.pojo.UserAddress;
import com.gxb.pojo.bo.AddressBO;
import com.gxb.service.AddressService;
import com.gxb.utils.JSONResult;
import com.gxb.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("address")
@Api(value = "地址相关",tags = {"地址相关的接口"})
public class AddressController {

    @Autowired
    private AddressService addressService;
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */
    @ApiOperation(value = "根据用户id查询收货地址列表",notes = "根据用户id查询收货地址列表",tags = {"根据用户id查询收货地址列表"},httpMethod = "POST")
    @PostMapping("list")
    public JSONResult list(@RequestParam("userId")
                          @ApiParam(name = "userId",value = "用户id",required = true) String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        List<UserAddress> userAddresses = addressService.queryAll(userId);
        return JSONResult.ok(userAddresses);
    }

    @ApiOperation(value = "用户新增地址",notes = "用户新增地址",tags = {"用户新增地址"},httpMethod = "POST")
    @PostMapping("add")
    public JSONResult add(@RequestBody AddressBO addressBO) {
        JSONResult jsonResult = this.checkAddress(addressBO);
        if (jsonResult.getStatus() != 200) {
            return jsonResult;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }

        return JSONResult.ok();
    }
}