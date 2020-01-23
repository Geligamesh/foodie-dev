package com.gxb.controller.center;

import com.gxb.pojo.Users;
import com.gxb.pojo.bo.center.CenterUserBO;
import com.gxb.service.center.CenterUserService;
import com.gxb.utils.CookieUtils;
import com.gxb.utils.JSONResult;
import com.gxb.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("userInfo")
@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @PostMapping("update")
    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "GET")
    public JSONResult update(@ApiParam(name = "userId",value = "用户id",required = true)
                               @RequestParam("userId") String userId,
                                @RequestBody @Valid CenterUserBO centerUserBO,
                             BindingResult result,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        //判断BingingResults是否保存错误的验证信息，如果有则知己return
        if (result.hasErrors()) {
            Map<String, String> errors = this.getErrors(result);
            return JSONResult.errorMap(errors);
        }
        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        //TODO 增加token令牌，整合redis分布式会话
        return JSONResult.ok();
    }

    private Map<String,String> getErrors(BindingResult bindingResult) {
        Map<String,String> map = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            map.put(field, defaultMessage);
        });
        return map;
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
