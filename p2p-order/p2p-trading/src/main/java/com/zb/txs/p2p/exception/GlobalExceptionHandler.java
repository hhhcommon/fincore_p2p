package com.zb.txs.p2p.exception;

import com.zb.txs.foundation.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * User: hushenbing@zillionfortune.com
 * Date: 2017/2/10
 * Time: 下午7:57
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handle(Exception ex) {
        log.error("系统异常", ex);
        return ResponseEntity.failure(null);
    }
}
