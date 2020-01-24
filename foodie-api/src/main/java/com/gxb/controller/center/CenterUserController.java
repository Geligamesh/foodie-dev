package com.gxb.controller.center;

import com.gxb.controller.BaseController;
import com.gxb.pojo.Users;
import com.gxb.pojo.bo.center.CenterUserBO;
import com.gxb.resource.FileUpLoad;
import com.gxb.service.center.CenterUserService;
import com.gxb.utils.CookieUtils;
import com.gxb.utils.DateUtil;
import com.gxb.utils.JSONResult;
import com.gxb.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("userInfo")
@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpLoad fileUpLoad;

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

    @PostMapping("uploadFace")
    @ApiOperation(value = "用户头像修改",notes = "用户头像修改",httpMethod = "POST")
    public JSONResult uploadFace(@ApiParam(name = "userId",value = "用户id",required = true)
                             @RequestParam("userId") String userId,
                             MultipartFile file,
                             HttpServletRequest request,
                             HttpServletResponse response)  {
        //定义头像保存的地址
        String path = fileUpLoad.getImageUserFaceLocation();
        String fileSpace = path + "faceImages/";
        String uploadPathPrefix = userId + File.separator;
        FileOutputStream fileOutputStream = null;
        String newFileName = null;
        InputStream inputStream;
        //开始上传
        if (file != null) {
            try {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isBlank(fileName)) {
                    //文件重命名
                    String[] fileNameArr = fileName.split("\\.");
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg") ) {
                        return JSONResult.errorMsg("图片格式不正确");
                    }

                    //文件名称重组
                    newFileName = "face-" + userId +"." + suffix;
                    //上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + newFileName;
                    System.out.println(finalFacePath);
                    File outFile = new File(finalFacePath);
                    if (!outFile.getParentFile().exists()) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //获得图片服务地址
            String imageServerUrl = fileUpLoad.getImageServerUrl();
            imageServerUrl = imageServerUrl + uploadPathPrefix + newFileName + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
            //更新用户头像到数据库
            Users userResult = centerUserService.updateUserFace(userId, imageServerUrl);

            setNullProperty(userResult);
            CookieUtils.setCookie(request, response, "user",
                    JsonUtils.objectToJson(userResult), true);
            //TODO 增加token令牌，整合redis分布式会话
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("上传失败");
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
