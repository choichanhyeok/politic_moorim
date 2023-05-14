package com.politics_moorim.controller;


import com.politics_moorim.exception.InvalidRequest;
import com.politics_moorim.exception.PoliticMoorimException;
import com.politics_moorim.exception.PostNotFound;
import com.politics_moorim.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse response = ErrorResponse.builder().code("400").message("잘못된 요청입니다.").build();

        for (FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }


    @ResponseBody
    @ExceptionHandler(PoliticMoorimException.class)
    public ResponseEntity<ErrorResponse> politicMoorimException(PoliticMoorimException e){
        int statusCode = e.getStatusCode();
        ErrorResponse response = ErrorResponse.builder().code(String.valueOf(statusCode)).message(e.getMessage()).validation(e.getValidation()).build();


        return ResponseEntity.status(statusCode).body(response);
    }


}