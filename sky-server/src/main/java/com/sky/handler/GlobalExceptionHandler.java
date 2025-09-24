package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex 基础异常，所有自定义异常的父类
     * @return 1
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理sql 唯一索引异常
     *
     * @param ex 异常类
     * @return 返回异常的错误信息
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        //Duplicate entry 'Jerry' for key 'employee.idx_username'
        log.error("异常信息: {}", ex.getMessage());
        //注意这里我们要返回前端具体的错误信息 也就是entry后面的内容
        //geMessage获取的异常信息就是上面那个注释内容
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] sp = message.split(" ");
            String username = sp[2];
            return Result.error(username + MessageConstant.ALREADY_EXISTS);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
