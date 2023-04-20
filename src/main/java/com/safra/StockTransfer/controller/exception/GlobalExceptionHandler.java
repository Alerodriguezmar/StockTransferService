package com.safra.StockTransfer.controller.exception;

import com.safra.StockTransfer.exceptions.SQLConnectionException;

import java.io.IOException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(RestClientException.class)
//    public ResponseEntity<ErrorDTO> handleRestClientException(RestClientException exception) {
//        log.error(exception.getMessage(), exception);
//        return new ResponseEntity<>(new ErrorDTO(ERPHttpConnectionErrorMessages.HTTP_ERP_CONECCTION_ERROR.getMessage()),
//                HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ErrorDTO> handleRestClientException(ValidationException exception) {
//        log.error(exception.getMessage(), exception);
//        return new ResponseEntity<>(new ErrorDTO(exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(SQLConnectionException.class)
//    public ResponseEntity<ErrorDTO> handleRestClientException(SQLConnectionException exception) {
//        log.error(exception.getMessage(), exception);
//        return new ResponseEntity<>(new ErrorDTO(exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<ErrorDTO> handleRestClientException(NullPointerException exception) {
//        log.error(exception.getMessage(), exception);
//        return new ResponseEntity<>(new ErrorDTO(exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(IOException.class) 
    public void handleIOException (IOException exception) {
    	exception.printStackTrace();
    }         
    
}
