package com.finder.api.exception;

public class MyRuntimeException extends RuntimeException {


    public MyRuntimeException() {
        super();
    }

    public MyRuntimeException(String message) {
        super(message);
    }


    public MyRuntimeException(Throwable cause) {
        super(cause);
    }

    public MyRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
