package com.finder.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorMessage {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorMessage(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
