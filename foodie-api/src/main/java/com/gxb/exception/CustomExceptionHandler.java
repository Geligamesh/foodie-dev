package com.gxb.exception;

import com.gxb.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    //上传文件超过500k，捕获异常
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException exception) {
        return JSONResult.errorMsg("文件上传带下不能超过500k,请压缩图片或者降低图片质量再上传");
    }
}
