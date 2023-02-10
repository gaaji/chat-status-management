package com.gaaji.chat.statusmanagement.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CSMException.class)
    public void handleCSMException(Throwable t){
        CSMException exception = (CSMException) t;
        log.error(exception.getMessage());
    }
}
