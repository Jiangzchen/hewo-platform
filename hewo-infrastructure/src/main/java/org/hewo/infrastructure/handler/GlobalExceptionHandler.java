package org.hewo.infrastructure.handler;

import org.hewo.infrastructure.exception.BusinessException;
import org.hewo.infrastructure.model.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException)e;
            return new BaseResponse(businessException.getCode(),  businessException.getMessage());
        } else {
            return new BaseResponse(10001, e.getMessage());
        }
    }
}
