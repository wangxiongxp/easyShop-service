package com.aliboy.exception;

import com.aliboy.common.exception.GlobalExceptionBaseHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends GlobalExceptionBaseHandler{

//    // 上传文件超过500k，捕获异常：MaxUploadSizeExceededException
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
//        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500k，请压缩图片或者降低图片质量再上传！");
//    }
}
