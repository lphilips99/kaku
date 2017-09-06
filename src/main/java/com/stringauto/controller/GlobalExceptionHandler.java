package com.stringauto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stringauto.model.ErrorResponse;


@ControllerAdvice  
@RestController  
public class GlobalExceptionHandler {  
  
  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(value = Exception.class)  
    public ResponseEntity<ErrorResponse> handleException(Exception e){
    	
    	ErrorResponse error = new ErrorResponse();
    	error.setErrorCode(10);
    	error.setMessage(e.getMessage());
    	return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
   
    	}  
  
  
}  