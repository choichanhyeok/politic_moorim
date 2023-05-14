package com.politics_moorim.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends PoliticMoorimException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }
//
//    public PostNotFound(Throwable cause) {
//        super(MESSAGE, cause);
//    }

    public InvalidRequest(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
