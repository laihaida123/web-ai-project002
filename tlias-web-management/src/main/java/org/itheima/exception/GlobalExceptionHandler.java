package org.itheima.exception;

import lombok.extern.slf4j.Slf4j;
import org.itheima.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
/**
 * 全局异常处理器
 */
@RestControllerAdvice//表示全局异常处理器，可以将返回值直接相应会前端
public class GlobalExceptionHandler {
    @ExceptionHandler//表示异常处理的方法
    public Result exceptionHandler(Exception ex) {
        log.error("出现异常：{}", ex.getMessage());
        return Result.error("服务器异常");
    }
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        log.error("出现异常2：{}", ex.getMessage());
        String message = ex.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }
}
