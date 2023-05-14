package com.politics_moorim.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class PoliticMoorimException extends RuntimeException{
    private final Map<String, String> validation = new HashMap<>();

    public PoliticMoorimException(String message) {
        super(message);
    }

    public PoliticMoorimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        this.validation.put(fieldName, message);
    }
}
