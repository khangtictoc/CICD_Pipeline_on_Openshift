package com.mkyong.exception;

import com.mkyong.book.BookController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookHandler {
    private static final Log log = LogFactory.getLog(BookController.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof NullPointerException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        MDC.put("StatusResponse",httpStatus.toString());
        log.error(ex.getMessage());
        return ResponseEntity.status(httpStatus).build();
    }
}