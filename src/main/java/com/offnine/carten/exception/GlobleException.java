package com.offnine.carten.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobleException {
    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerException se ,WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(se.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(LocalDateTime.now());

        
            return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);

    }
   
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorDetails> ProductExceptionHandler(ProductException pe ,WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(pe.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(LocalDateTime.now());

        
            return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> CategoryNotFoundExceptionHandler(CategoryNotFoundException cnfe ,WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(cnfe.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(LocalDateTime.now());

        
            return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue ,WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(ue.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(LocalDateTime.now());

        
            return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);

    }
}
