package com.finder.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.finder.api.dto.ErrorMessage;

import java.time.LocalDateTime;


@ControllerAdvice
public class ExceptionBadRequestHandler  extends ResponseEntityExceptionHandler {
    
// Este método captura qualquer outra exceção não tratada
    @ExceptionHandler(MyRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleMyRuntimeException(MyRuntimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
