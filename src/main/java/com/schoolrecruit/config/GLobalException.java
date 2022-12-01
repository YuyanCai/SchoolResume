package com.schoolrecruit.config;

import com.schoolrecruit.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GLobalException {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = RuntimeException.class)
    public Result hander(RuntimeException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("运行异常：",e);
        //response.sendRedirect("/page/500");
        return Result.fail("运行异常，请联系管理员！");
    }
}
