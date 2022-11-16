package com.example.running.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public Result UploadException(IOException ioException) {
        return new Result(500, "上传图片失败", ioException.getMessage());
    }
}
