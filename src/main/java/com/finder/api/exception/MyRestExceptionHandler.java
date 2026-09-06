package com.finder.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.finder.api.dto.ErrorMessage;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyRestExceptionHandler extends ResponseEntityExceptionHandler {

    // Este método captura especificamente a sua MyRuntimeException
    @ExceptionHandler(MyRuntimeException.class)
    private ResponseEntity<ErrorMessage> handleMyException(MyRuntimeException exception) {
        
        // Criamos o corpo da resposta com o texto que você enviou na Exception
        ErrorMessage threatResponse = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                exception.getMessage(),                  // A mensagem que você definiu
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
