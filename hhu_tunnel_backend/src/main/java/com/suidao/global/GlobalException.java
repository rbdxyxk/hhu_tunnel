package com.suidao.global;

import com.suidao.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public Result handlerRuntimeException(RuntimeException e){
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}
